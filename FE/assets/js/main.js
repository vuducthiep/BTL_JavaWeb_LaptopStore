// document.addEventListener('DOMContentLoaded', function () {
//   const productList = document.getElementById('featured-products');

//   // Gọi API để hiển thị tất cả sản phẩm khi load trang
//   fetch('http://localhost:3000/products')
//     .then(response => response.json())
//     .then(data => {
//       console.log(data); // Kiểm tra cấu trúc dữ liệu trả về từ API

//       // Giả sử API trả về một mảng sản phẩm
//       displayProducts(data); // Hiển thị tất cả sản phẩm
//     })
//     .catch(error => console.error('Error fetching products:', error));

//   // Chức năng lọc theo danh mục
//   const categoryLinks = document.querySelectorAll('.item');

//   // Gắn sự kiện click vào mỗi link trong danh mục
//   categoryLinks.forEach(link => {
//     link.addEventListener('click', function (event) {
//       event.preventDefault(); // Ngăn việc trang tải lại khi nhấp vào link

//       const category = link.querySelector('a').getAttribute('data-category');
//       fetchProductsByCategory(category); // Gọi hàm fetch API để lọc sản phẩm theo danh mục
//     });
//   });

//   // Hàm gọi API và hiển thị sản phẩm theo danh mục
//   function fetchProductsByCategory(category) {
//     fetch(`http://localhost:3000/products?category=${category}`) // Thay bằng API của bạn
//       .then(response => response.json())
//       .then(data => {
//         displayProducts(data); // Hiển thị sản phẩm theo danh mục
//       })
//       .catch(error => {
//         console.error('Error fetching products:', error);
//       });
//   }

//   // Hàm hiển thị sản phẩm lên giao diện
//   function displayProducts(products) {
//     productList.innerHTML = ''; // Xóa sản phẩm cũ

//     if (products.length === 0) {
//       productList.innerHTML = '<p>Không có sản phẩm nào.</p>';
//       return;
//     }

//     products.forEach(product => {
//       const productDiv = document.createElement('div');
//       productDiv.classList.add('product-item');
//       productDiv.innerHTML = `
//         <a class="product-link" href="product-details.html?id=${product.id}">
//           <div class="product-image">
//             <img src="${product.thumbnail}" alt="${product.name}">
//           </div>
//           <div class="product-info">
//             <h3 class="product-title">${product.name}</h3>
//             <p class="product-price">Giá: ${product.price} VND</p>
//             <p>${product.description}</p>
//           </div>
//         </a>
//       `;
//       productList.appendChild(productDiv);
//     });
//   }

//   // Hàm hiển thị 4 sản phẩm nổi bật lên giao diện
//   function displayFeaturedProducts(products) {
//     const featuredProducts = products.slice(0, 4); // Lấy 4 sản phẩm đầu tiên (có thể thay đổi logic chọn sản phẩm)

//     featuredList.innerHTML = ''; // Xóa sản phẩm nổi bật cũ

//     featuredProducts.forEach(product => {
//       const productDiv = document.createElement('div');
//       productDiv.classList.add('featured-item');
//       productDiv.innerHTML = `
//         <a class="product-link" href="product-details.html?id=${product.id}">
//           <div class="product-image">
//             <img src="${product.thumbnail}" alt="${product.name}">
//           </div>
//           <div class="product-info">
//             <h3 class="product-title">${product.name}</h3>
//             <p class="product-price">Giá: ${product.price} VND</p>
//             <p>${product.description}</p>
//           </div>
//         </a>
//       `;
//       featuredList.appendChild(productDiv);
//     });
//   }
// });

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
      featuredList.appendChild(productDiv);
    });
  }

  // Hàm hiển thị 6 sản phẩm thường
  function displayRegularProducts(products) {
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

