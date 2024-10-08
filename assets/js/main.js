document.addEventListener('DOMContentLoaded', function () {
  const productList = document.getElementById('featured-products');

  // Gọi API để hiển thị tất cả sản phẩm khi load trang
  fetch('http://localhost:3000/products')
    .then(response => response.json())
    .then(data => {
      console.log(data); // Kiểm tra cấu trúc dữ liệu trả về từ API

      // Giả sử API trả về một mảng sản phẩm
      displayProducts(data); // Hiển thị tất cả sản phẩm
    })
    .catch(error => console.error('Error fetching products:', error));

  // Chức năng lọc theo danh mục
  const categoryLinks = document.querySelectorAll('.category-item a');

  // Gắn sự kiện click vào mỗi link trong danh mục
  categoryLinks.forEach(link => {
    link.addEventListener('click', function (event) {
      event.preventDefault(); // Ngăn việc trang tải lại khi nhấp vào link

      const category = this.getAttribute('data-category');
      fetchProductsByCategory(category); // Gọi hàm fetch API để lọc sản phẩm theo danh mục
    });
  });

  // Hàm gọi API và hiển thị sản phẩm theo danh mục
  function fetchProductsByCategory(category) {
    fetch(`http://localhost:3000/products?category=${category}`) // Thay bằng API của bạn
      .then(response => response.json())
      .then(data => {
        displayProducts(data); // Hiển thị sản phẩm theo danh mục
      })
      .catch(error => {
        console.error('Error fetching products:', error);
      });
  }

  // Hàm hiển thị sản phẩm lên giao diện
  function displayProducts(products) {
    productList.innerHTML = ''; // Xóa sản phẩm cũ

    if (products.length === 0) {
      productList.innerHTML = '<p>Không có sản phẩm nào.</p>';
      return;
    }

    products.forEach(product => {
      const productDiv = document.createElement('div');
      productDiv.classList.add('product-item');
      productDiv.innerHTML = `
        <a class="product-link" href="product-details.html?id=${product.id}">
          <div class="product-image">
            <img src="${product.thumbnail}" alt="${product.name}">
          </div>
          <div class="product-info">
            <h3 class="product-title">${product.name}</h3>
            <p class="product-price">Giá: ${product.price} VND</p>
            <p>${product.description}</p>
          </div>
        </a>
      `;
      productList.appendChild(productDiv);
    });
  }
});

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


document.addEventListener("DOMContentLoaded", function () {
  var userAccount = document.getElementById("userAccount");
  var userMenu = document.getElementById("userMenu");
  var loginLink = document.getElementById("loginLink");
  var userInfo = document.getElementById("userInfo");
  var orders = document.getElementById("orders");
  var logout = document.getElementById("logout");

  // Kiểm tra trạng thái đăng nhập (giả sử ban đầu chưa đăng nhập)
  var isLoggedIn = false; // Thay đổi giá trị này dựa vào trạng thái thực tế

  // Cập nhật giao diện dựa trên trạng thái đăng nhập
  function updateMenu() {
    if (isLoggedIn) {
      loginLink.style.display = "none";   // Ẩn liên kết đăng nhập
      userInfo.style.display = "block";   // Hiển thị thông tin cá nhân
      orders.style.display = "block";     // Hiển thị đơn hàng
      logout.style.display = "block";     // Hiển thị đăng xuất
    } else {
      loginLink.style.display = "block";  // Hiển thị liên kết đăng nhập
      userInfo.style.display = "none";    // Ẩn thông tin cá nhân
      orders.style.display = "none";      // Ẩn đơn hàng
      logout.style.display = "none";      // Ẩn đăng xuất
    }
  }

  // Khi nhấp vào biểu tượng người dùng
  userAccount.addEventListener("click", function () {
    // Hiển thị hoặc ẩn menu
    if (userMenu.style.display === "none" || userMenu.style.display === "") {
      userMenu.style.display = "block";
    } else {
      userMenu.style.display = "none";
    }
  });

  // Khi người dùng nhấp vào "Đăng nhập"
  loginLink.addEventListener("click", function (event) {
    event.preventDefault();
    // Điều hướng người dùng đến trang đăng nhập (giả sử "/login")
    window.location.href = "login.html";
  });

  // Khi người dùng nhấp vào "Đăng xuất"
  logout.addEventListener("click", function (event) {
    event.preventDefault();
    // Xử lý việc đăng xuất (ví dụ: xóa session, token)
    alert("Đăng xuất thành công!");
    isLoggedIn = false;
    updateMenu(); // Cập nhật lại giao diện
  });

  // Gọi hàm để cập nhật giao diện ngay khi trang load
  updateMenu();
});

document.querySelectorAll('.item js-hover-menu a').forEach(item => {
  item.addEventListener('click', function(e) {
    e.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết

    // Ẩn bộ lọc hiện tại (nếu có)
    document.querySelector('.filter').classList.add('d-none');

    // Hiển thị bộ lọc
    document.querySelector('.filter').classList.remove('d-none');
    document.querySelector('.filter').classList.add('d-block');
  });
});

