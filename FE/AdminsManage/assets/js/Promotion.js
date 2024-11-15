// Biến toàn cục để lưu promotionID
let selectedPromotionID = null;

// Gọi API để lấy danh sách khuyến mãi
fetch('http://localhost:8080/admin/promotion')
  .then(response => response.json())
  .then(data => {
    const promotionListElement = document.getElementById('promotion-list');
    
    if (data.length === 0) {
      promotionListElement.innerHTML = '<tr><td colspan="3">Không có khuyến mãi nào</td></tr>';
    } else {
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
  selectedPromotionID = promotionID;
  console.log('Khuyến mãi được chọn:', selectedPromotionID);
  loadProductsByPromotion(selectedPromotionID);

  const url = new URL(window.location.href);
  url.searchParams.set('promotionID', promotionID);
  window.history.pushState({}, '', url);
}

// Hàm để gọi API lấy sản phẩm theo khuyến mãi đã chọn
function loadProductsByPromotion(promotionID) {
  const productListElement = document.getElementById('product-list');
  
  fetch(`http://localhost:8080/admin/promotion-product/${promotionID}`)
    .then(response => response.json())
    .then(data => {
      productListElement.innerHTML = '';
      if (data.length === 0) {
        productListElement.innerHTML = '<tr><td colspan="5">Không có sản phẩm nào áp dụng khuyến mãi</td></tr>';
      } else {
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
      productListElement.innerHTML = '<tr><td colspan="5">Danh sách trống</td></tr>';
    });
}

// Hàm xử lý khi thay đổi checkbox
function togglePromotion(productID, isChecked) {
  if (selectedPromotionID) {
    const url = isChecked
      ? `http://localhost:8080/admin/promotion-product/add-promotion/${productID}/${selectedPromotionID}`
      : `http://localhost:8080/admin/promotion-product/remove-promotion/${productID}/${selectedPromotionID}`;

    fetch(url, {
      method: isChecked ? 'POST' : 'DELETE',
      headers: { 'Content-Type': 'application/json' },
      body: isChecked ? JSON.stringify({ productID, selectedPromotionID, isApplied: true }) : null,
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
      loadProductsByPromotion(selectedPromotionID);
    });
  } else {
    console.log('Không có khuyến mãi được chọn.');
  }
}

// Gọi hàm load sản phẩm khi trang được tải
loadProductsByPromotion(selectedPromotionID);

