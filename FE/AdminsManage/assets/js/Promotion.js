// Biến toàn cục để lưu promotionID
let selectedPromotionID = null;

// Gọi API để lấy danh sách khuyến mãi
fetch('http://localhost:8080/admin/promotion')
  .then(response => response.json())
  .then(data => {
    const promotionListElement = document.getElementById('promotion-list');
    
    // Nếu không có dữ liệu trả về, thông báo cho người dùng
    if (data.length === 0) {
      promotionListElement.innerHTML = '<tr><td colspan="3">Không có khuyến mãi nào</td></tr>';
    } else {
      // Duyệt qua từng khuyến mãi và thêm vào bảng
      data.forEach(promotion => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${promotion.promotionID}</td>
          <td>${promotion.promotionName}</td>
          <td>
            <input type="radio" name="promotion" value="${promotion.promotionID}" 
                   onclick="selectPromotion(${promotion.promotionID})">
          </td>
        `;
        promotionListElement.appendChild(row);
      });
    }
  })
  .catch(error => {
    console.error('Lỗi khi gọi API:', error);
    const promotionListElement = document.getElementById('promotion-list');
    promotionListElement.innerHTML = '<tr><td colspan="3">Lỗi khi tải khuyến mãi</td></tr>';
  });

// Hàm để chọn khuyến mãi
function selectPromotion(promotionID) {
  selectedPromotionID = promotionID;  // Lưu ID của khuyến mãi đã chọn
  console.log('Khuyến mãi được chọn:', selectedPromotionID);

  // Thực hiện gọi lại API lấy danh sách sản phẩm theo khuyến mãi
  loadProductsByPromotion(selectedPromotionID);
  
  // Cập nhật URL mà không reload trang (nếu muốn lưu trạng thái trong URL)
  const url = new URL(window.location.href);
  url.searchParams.set('promotionID', promotionID);  // Thêm tham số vào URL
  window.history.pushState({}, '', url);
}

// Hàm để gọi API và lấy danh sách sản phẩm theo khuyến mãi đã chọn
function loadProductsByPromotion(promotionID) {
  const productListElement = document.getElementById('product-list');
  
  // Gọi API để lấy danh sách sản phẩm theo khuyến mãi
  fetch(`http://localhost:8080/admin/promotion-product/${promotionID}`)
    .then(response => response.json())
    .then(data => {
      // Xóa danh sách sản phẩm cũ trước khi thêm sản phẩm mới
      productListElement.innerHTML = '';

      // Nếu không có sản phẩm trả về
      if (data.length === 0) {
        productListElement.innerHTML = '<tr><td colspan="5">Không có sản phẩm nào áp dụng khuyến mãi</td></tr>';
      } else {
        // Duyệt qua từng sản phẩm và thêm vào bảng
        data.forEach(product => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${product.productID}</td>
            <td>${product.productName}</td>
            <td>${product.brand}</td>
            <td>
              <input type="checkbox" ${product.hasPromotion === 1 ? 'checked' : ''} 
                     onchange="togglePromotion(${product.productID}, this.checked)">
            </td>
          `;
          productListElement.appendChild(row);
        });
      }
    })
    .catch(error => {
      console.error('Lỗi khi gọi API:', error);
      productListElement.innerHTML = '<tr><td colspan="5">Lỗi khi tải sản phẩm</td></tr>';
    });
}

// Hàm để xử lý khi thay đổi trạng thái checkbox
function togglePromotion(productID, isChecked) {
  // Thực hiện cập nhật trạng thái khuyến mãi cho sản phẩm này nếu cần
  console.log(`Sản phẩm ${productID} ${isChecked ? 'được' : 'không được'} áp dụng khuyến mãi.`);
  // Có thể gọi API để cập nhật trạng thái này lên server
}


//Thêm ,Xóa khuyến mãi 
// Lấy PromotionID từ URL
const urlParams = new URLSearchParams(window.location.search);
const promotionID = urlParams.get('promotionID');

// Hàm gửi POST hoặc DELETE khi checkbox thay đổi
function togglePromotion(productID, isChecked) {
    if (promotionID) {
        const url = isChecked
            ? `http://localhost:8080/admin/promotion-product/add-promotion/${productID}/${promotionID}`  // POST nếu checkbox được tích
            : `http://localhost:8080/admin/promotion-product/remove-promotion/${productID}/${promotionID}`; // DELETE nếu checkbox bị bỏ tích

        // Gửi yêu cầu POST hoặc DELETE tùy theo trạng thái checkbox
        fetch(url, {
            method: isChecked ? 'POST' : 'DELETE', // Chọn phương thức POST hoặc DELETE
            headers: {
                'Content-Type': 'application/json',
            },
            body: isChecked
                ? JSON.stringify({
                      productID: productID,
                      promotionID: promotionID,
                      isApplied: true, // Trạng thái checkbox tích
                  })
                : null, // Không cần body khi là yêu cầu DELETE
        })
        .then(response => response.json())
        .then(data => {
            if (isChecked) {
                console.log('Khuyến mãi được thêm vào sản phẩm:', data);
            } else {
                console.log('Khuyến mãi đã bị xóa khỏi sản phẩm:', data);
            }
        })
        .catch(error => {
            console.error('Lỗi khi thay đổi khuyến mãi cho sản phẩm:', error);
        });
    } else {
        console.log('Không có khuyến mãi được chọn.');
    }
}

// Hàm thêm khuyến mãi cho sản phẩm (giữ nguyên)
function addPromotion() {
    const productID = document.getElementById('product-id').value;
    const promotionDetails = document.getElementById('promotion-details').value;

    if (productID && promotionDetails && promotionID) {
        const url = `http://localhost:8080/admin/promotion-product/add-promotion/${productID}/${promotionID}`;

        // Gửi yêu cầu POST khi nhấn thêm khuyến mãi
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                promotionDetails: promotionDetails
            })
        })
        .then(response => response.json())
        .then(data => {
            console.log('Khuyến mãi đã được thêm:', data);
        })
        .catch(error => {
            console.error('Lỗi khi thêm khuyến mãi:', error);
        });
    } else {
        alert('Vui lòng chọn sản phẩm và nhập chi tiết khuyến mãi.');
    }
}

// Hàm hiển thị danh sách sản phẩm và checkbox (giữ nguyên)
function loadProducts() {
    fetch(`http://localhost:8080/admin/promotion-product/${promotionID}`)
        .then(response => response.json())
        .then(data => {
            const productListElement = document.getElementById('product-id');
            productListElement.innerHTML = ''; // Clear current list

            // Nếu có sản phẩm, thêm vào dropdown
            if (data.length > 0) {
                data.forEach(product => {
                    const option = document.createElement('option');
                    option.value = product.productID;
                    option.textContent = `${product.productName} - ${product.brand}`;
                    productListElement.appendChild(option);
                });
            }
        })
        .catch(error => {
            console.error('Lỗi khi tải danh sách sản phẩm:', error);
        });
}

// Gọi hàm để load sản phẩm khi trang được tải (giữ nguyên)
loadProducts();
