let displayedProducts = []; // Mảng lưu trữ các sản phẩm đang hiển thị
let allProducts = [];
document.addEventListener('DOMContentLoaded', function () {
  const featuredList = document.getElementById('featured-products-list');
  window.regularList = document.getElementById('regular-products-list'); // Khai báo global để sử dụng ở các file khác
  const loadMoreBtn = document.getElementById('load-more-btn');

 

  let productOffset = 10;

  // Lấy dữ liệu sản phẩm từ API
  fetch('http://localhost:3000/products')
    .then(response => response.json())
    .then(data => {
      allProducts = data;
      displayFeaturedProducts(allProducts);

      // Cập nhật mảng displayedProducts khi tải dữ liệu
      displayedProducts = allProducts.slice(4, 10); // Hiển thị 6 sản phẩm đầu tiên
      displayRegularProducts(displayedProducts); // Hiển thị sản phẩm đầu tiên
    })
    .catch(error => console.error('Error fetching products:', error));

  // Hàm hiển thị sản phẩm nổi bật
  function displayFeaturedProducts(products) {
    const featuredProducts = products.slice(0, 4); // Lấy 4 sản phẩm nổi bật
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
            <p class="product-price">Giá: ${(product.Price * 1000000).toLocaleString('vi-VN')} VND</p>
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

  // Hàm hiển thị các sản phẩm thông thường
 function displayRegularProducts(products) {
    window.regularList.innerHTML = ''; // Xóa danh sách sản phẩm cũ
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
            <p class="product-price">Giá: ${(product.Price * 1000000).toLocaleString('vi-VN')} VND</p>
          </div>
        </a>
        <button class="compare-btn" onclick="addToCompare(${product.ProductID})">
          <div class="icon-circle">
            <i class="fas fa-plus"></i>
          </div>
          So sánh
        </button>
      `;
      window.regularList.appendChild(productDiv);
    });
   
  }

  // Hàm xử lý nút "Load More"
  loadMoreBtn.addEventListener('click', function () {
    const newProducts = allProducts.slice(productOffset, productOffset + 6); // Tải thêm 6 sản phẩm
    if (newProducts.length > 0) {
     
      displayedProducts = displayedProducts.concat(newProducts); // Cập nhật mảng displayedProducts
      console.log(displayedProducts)
      displayRegularProducts(displayedProducts); // Hiển thị sản phẩm đã cập nhật
      productOffset += 6;
    } else {
      loadMoreBtn.style.display = 'none'; // Ẩn nút "Load More" khi không còn sản phẩm để tải
    }
  });
});
