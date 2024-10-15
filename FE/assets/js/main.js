document.addEventListener('DOMContentLoaded', function () {
  const featuredList = document.getElementById('featured-products-list');
  const regularList = document.getElementById('regular-products-list');
  const loadMoreBtn = document.getElementById('load-more-btn');

  let productOffset = 10; // Vị trí bắt đầu để load thêm sản phẩm (bắt đầu từ sản phẩm thứ 11)

  // Gọi API để load sản phẩm khi trang load
  fetch('http://localhost:3000/products')
    .then(response => response.json())
    .then(data => {
      // Hiển thị 4 sản phẩm nổi bật
      displayFeaturedProducts(data);

      // Hiển thị 6 sản phẩm đầu tiên
      displayRegularProducts(data.slice(4, 10));
    })
    .catch(error => console.error('Error fetching products:', error));

  // Hàm hiển thị 4 sản phẩm nổi bật
  function displayFeaturedProducts(products) {
    const featuredProducts = products.slice(0, 4); // Lấy 4 sản phẩm đầu tiên

    featuredProducts.forEach(product => {
      const productDiv = document.createElement('div');
      productDiv.classList.add('featured-item'); 
      productDiv.innerHTML = `
        <a class="product-link" href="product-details.html?id=${product.ProductID}">
          <div class="product-image">
            <img src="${product.ImageURL}" alt="${product.ProductName}">
          </div>
          <div class="product-info">
            <h3 class="product-title">${product.ProductName}</h3>
            <p class="product-price">Giá: ${product.Price} VND</p>
          </div>
        </a>
        <button class="compare-btn" onclick="addToCompare(${product.ProductID})">
          <div class="icon-circle">
            <i class="fas fa-plus"></i>
          </div>
          So sánh
        </button>
      `;
      featuredList.appendChild(productDiv);
    });
  }

  // Hàm hiển thị 6 sản phẩm thường
  function displayRegularProducts(products) {
    products.forEach(product => {
      const productDiv = document.createElement('div');
      productDiv.classList.add('product-item'); 
      productDiv.innerHTML = `
        <a class="product-link" href="product-details.html?id=${product.ProductID}">
          <div class="product-image">
            <img src="${product.ImageURL}" alt="${product.ProductName}">
          </div>
          <div class="product-info">
            <h3 class="product-title">${product.ProductName}</h3>
            <p class="product-price">Giá: ${product.Price} VND</p>
          </div>
        </a>
        <button class="compare-btn" onclick="addToCompare(${product.ProductID})">
          <div class="icon-circle">
            <i class="fas fa-plus"></i>
          </div>
          So sánh
        </button>
      `;
      regularList.appendChild(productDiv);
    });
  }

  // Hàm load thêm 6 sản phẩm nữa khi ấn nút "Xem thêm"
  loadMoreBtn.addEventListener('click', function () {
    fetch(`http://localhost:3000/products?_start=${productOffset}&_limit=6`) // Chỉnh sửa ở đây để lấy 6 sản phẩm
      .then(response => response.json())
      .then(data => {
        if (data.length > 0) {
          displayRegularProducts(data);
          productOffset += 6; // Tăng vị trí bắt đầu để load thêm lần sau
        } else {
          loadMoreBtn.style.display = 'none'; // Ẩn nút nếu không còn sản phẩm
        }
      })
      .catch(error => console.error('Error fetching products:', error));
  });
});





// slider banner
let currentSlide = 0;

// Hiện slide đầu tiên khi tải trang
showSlides();

function showSlides() {
    const slides1 = document.querySelectorAll('.img-fluid-item1 img');
    slides1.forEach((slide) => {
        slide.classList.remove('show'); // Ẩn tất cả
    });
    
    slides1[currentSlide].classList.add('show'); // Hiện slide trong item1
}

function changeSlide(n) {
    const totalSlides = document.querySelectorAll('.img-fluid-item1 img').length;

    currentSlide += n;

    // Đảm bảo currentSlide không vượt quá số lượng ảnh
    if (currentSlide >= totalSlides) {
        currentSlide = 0; // Trở về đầu
    } else if (currentSlide < 0) {
        currentSlide = totalSlides - 1; // Quay lại cuối
    }

    showSlides();
}

// Tự động chuyển slide sau 5 giây
setInterval(() => changeSlide(1), 5000);
// slider banner


// ẩn hiện box khi đăng nhập,đăng xuất
document.addEventListener("DOMContentLoaded", function () {
  var userAccount = document.getElementById("userAccount");
  var userMenu = document.getElementById("userMenu");
  var loginLink = document.getElementById("loginLink");
  var userInfo = document.getElementById("userInfo");
  var orders = document.getElementById("orders");
  var logout = document.getElementById("logout");

  // Kiểm tra trạng thái đăng nhập
  var isLoggedIn = localStorage.getItem("isLoggedIn") === "true";

  // Cập nhật giao diện
  function updateMenu() {
      if (isLoggedIn) {
          loginLink.style.display = "none";   // Ẩn liên kết đăng nhập
          userInfo.style.display = "block";   // Hiển thị thông tin cá nhân
          orders.style.display = "block";     // Hiển thị đơn hàng
          logout.style.display = "block";     // Hiển thị đăng xuất

          // Nếu là admin, bạn có thể hiển thị thêm thông tin hoặc điều hướng khác
          if (localStorage.getItem("userRole") === 'admin') {
              // Thực hiện hành động nếu cần cho admin
          }
      } else {
          loginLink.style.display = "block";  // Hiển thị liên kết đăng nhập
          userInfo.style.display = "none";    // Ẩn thông tin cá nhân
          orders.style.display = "none";      // Ẩn đơn hàng
          logout.style.display = "none";      // Ẩn đăng xuất
      }
  }

  // Khi nhấp vào biểu tượng người dùng
  userAccount.addEventListener("click", function () {
      if (userMenu.style.display === "none" || userMenu.style.display === "") {
          userMenu.style.display = "block";
      } else {
          userMenu.style.display = "none";
      }
  });

  // Khi người dùng nhấp vào "Đăng nhập"
  loginLink.addEventListener("click", function (event) {
      event.preventDefault();
      // Điều hướng người dùng đến trang đăng nhập
      window.location.href = "login.html";
  });

  // Khi người dùng nhấp vào "Đăng xuất"
  logout.addEventListener("click", function (event) {
      event.preventDefault();
      // Xử lý việc đăng xuất
      alert("Đăng xuất thành công!");
      localStorage.removeItem("isLoggedIn"); // Xóa trạng thái đăng nhập
      localStorage.removeItem("userRole"); // Xóa thông tin vai trò
      isLoggedIn = false;
      updateMenu(); // Cập nhật lại giao diện
  });

  // Gọi hàm để cập nhật giao diện ngay khi trang load
  updateMenu();
});
// ẩn hiện box khi đăng nhập,đăng xuất


document.querySelectorAll('.item').forEach(item => {
  item.addEventListener('click', function(e) {
    e.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết

    // Ẩn bộ lọc hiện tại (nếu có)
    document.querySelector('.filter').classList.add('d-none');

    // Hiển thị bộ lọc
    document.querySelector('.filter').classList.remove('d-none');
    document.querySelector('.filter').classList.add('d-block');
  });
});

function toggleFilter(filterId) {
  const filterItems = document.getElementById(filterId);
  if (filterItems.style.display === "none") {
    filterItems.style.display = "block"; // Hiện các mục lọc
  } else {
    filterItems.style.display = "none"; // Ẩn các mục lọc
  }
}

//Lọc sản phẩm theo hãng khi chọn từ nav slider
const boxes = document.querySelectorAll('.custom-grid-item');
    
    // Duyệt qua từng box
    boxes.forEach(box => {
        box.addEventListener('click', function() {
            // Bỏ class 'selected' khỏi tất cả các box khác
            boxes.forEach(item => item.classList.remove('selected'));
            
            // Thêm class 'selected' vào box được click
            this.classList.add('selected');
            
            // Lấy hãng từ span (giả sử tên hãng nằm trong thẻ span)
            const brand = this.querySelector('span').textContent;
            
            // Gọi hàm lọc sản phẩm theo hãng
            filterProductsByBrand(brand);
        });
    });
    
    // Hàm lọc sản phẩm theo hãng
    function filterProductsByBrand(brand) {
        // Giả sử bạn đã có danh sách sản phẩm và lọc chúng ở đây
        console.log(`Lọc các sản phẩm của hãng: ${brand}`);
        // Thực hiện việc lọc sản phẩm và hiển thị chúng trên giao diện
    }
//Lọc sản phẩm theo hãng khi chọn từ nav slider


// So sánh
// Biến để lưu trữ sản phẩm được so sánh
function compareProducts() {
  // Lấy danh sách các sản phẩm đang được thêm vào thanh so sánh (nav)
  if (comparedProducts.length === 2) {
    const product1ID = comparedProducts[0]; 
    const product2ID = comparedProducts[1];

    // Tạo URL để chuyển đến trang product-compare.html và truyền các ID sản phẩm
    const compareURL = `product-compare.html?product1=${product1ID}&product2=${product2ID}`;

    // Chuyển hướng đến trang so sánh
    window.location.href = compareURL;
  } else {
    alert("Bạn cần chọn 2 sản phẩm để so sánh!");
  }
}


let comparedProducts = [];

// Hàm thêm sản phẩm vào danh sách so sánh
function addToCompare(productId) {
  if (comparedProducts.length >= 2) {
    alert('Bạn chỉ có thể so sánh tối đa 2 sản phẩm!');
    return;
  }
  
  // Kiểm tra xem sản phẩm đã được thêm chưa
  if (!comparedProducts.includes(productId)) {
    comparedProducts.push(productId);
    updateCompareProducts();

    // Hiện thanh điều hướng nếu có sản phẩm
    document.querySelector('.navbar').style.display = 'flex';
  } else {
    alert('Sản phẩm đã được thêm vào danh sách so sánh!');
  }
}

// Hàm cập nhật danh sách sản phẩm so sánh trên navbar
function updateCompareProducts() {
  const compareProductsDiv = document.querySelector('.compare-products');
  const navbar = document.querySelector('.navbar');
  compareProductsDiv.innerHTML = ''; // Xóa nội dung cũ

  // Kiểm tra nếu có sản phẩm trong danh sách so sánh
  if (comparedProducts.length > 0) {
    navbar.style.display = 'flex'; // Hiện thanh navbar
    document.getElementById('toggle-arrow').style.display = 'inline'; // Hiện mũi tên

    // Lấy toàn bộ sản phẩm từ API
    fetch('http://localhost:3000/products')
      .then(response => response.json())
      .then(products => {
        comparedProducts.forEach(productId => {
          const product = products.find(item => item.ProductID === productId);
          if (product) {
            const productItem = document.createElement('div');
            productItem.classList.add('compare-item');
            productItem.innerHTML = `
              <div class="compare-product-image">
                <img src="${product.ImageURL}" alt="${product.ProductName}">
                 <span>${product.ProductName}</span>
              </div>
              <div class="compare-product-info">
               
                <button class="remove-btn" onclick="removeFromCompare(${productId})">X</button>
              </div>
            `;
            compareProductsDiv.appendChild(productItem);
          }
        });
      })
      .catch(error => console.error('Error fetching products:', error));
  } else {
    navbar.style.display = 'none'; // Ẩn thanh navbar nếu không có sản phẩm
    document.getElementById('toggle-arrow').style.display = 'none'; // Ẩn mũi tên
  }
}

// Hàm xóa sản phẩm khỏi danh sách so sánh
function removeFromCompare(productId) {
  comparedProducts = comparedProducts.filter(id => id !== productId);
  updateCompareProducts();
}

// Hàm xóa tất cả sản phẩm trong danh sách so sánh
function clearAllCompare() {
  comparedProducts = [];
  updateCompareProducts();
}

// Hàm để toggle navbar
function toggleNavbar() {
  const navbar = document.querySelector('.navbar');
  const arrow = document.getElementById('toggle-arrow');

  // Kiểm tra nếu navbar đang hiển thị
  if (navbar.style.display === 'flex') {
    navbar.style.display = 'none'; // Ẩn navbar
    arrow.textContent = '▲'; // Mũi tên lên
  } else {
    navbar.style.display = 'flex'; // Hiện navbar
    arrow.textContent = '▼'; // Mũi tên xuống
  }
}


// So sánh
