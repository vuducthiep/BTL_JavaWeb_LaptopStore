document.addEventListener('DOMContentLoaded', () => {
  const featuredList = document.getElementById('featured-products-list');
  const regularList = document.getElementById('regular-products-list');
  const loadMoreBtn = document.getElementById('load-more-btn');
  const sortOptions = document.getElementById('sort-options'); // Lấy phần tử sort

  // Biến toàn cục
  window.allProducts = [];
  window.displayedProducts = [];
  let productOffset = 0; // Chỉ số offset để lấy sản phẩm tiếp theo
  const productsPerPage = 6; // Số sản phẩm hiển thị mỗi lần
  let currentSortOption = 'default'; // Biến lưu trữ lựa chọn sắp xếp mặc định

  // Hàm khởi tạo
  const initialize = async () => {
    try {
      const response = await fetch('http://localhost:8080/user/home/');
      const data = await response.json();

      allProducts = data.getProductForHomePage || [];
      const productFeatured = data.getOutstandingProducts || [];
      const ListDemand = data.getCustomerDemandForCheckBox || {};
      console.log('Danh sách sản phẩm', allProducts);
      console.log('Danh sách sản phẩm nổi bật', productFeatured);
      console.log(ListDemand);

      // Hiển thị sản phẩm
      displayFeaturedProducts(productFeatured);
      renderCustomerDemand(ListDemand);

      // Hiển thị sản phẩm đầu tiên trong danh sách
      loadInitialProducts();
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  // Hàm hiển thị sản phẩm nổi bật
  const displayFeaturedProducts = (products) => {
    const featuredSection = document.getElementById('featured-products');
    if (products.length >= 4) {
      featuredSection.style.display = 'block'; // Hiển thị nếu có từ 4 sản phẩm trở lên
      featuredList.innerHTML = products
        .slice(0, 4) // Lấy tối đa 4 sản phẩm
        .map(product => createProductHTML(product, true)) // Tạo HTML cho mỗi sản phẩm
        .join('');
    } else {
      featuredSection.style.display = 'none'; // Ẩn nếu có ít hơn 4 sản phẩm
    }
  };

  // Hàm hiển thị sản phẩm thông thường
  const displayRegularProducts = (products) => {
    regularList.innerHTML = products
      .map(product => createProductHTML(product, false)) // Tạo HTML cho mỗi sản phẩm
      .join('');
  };

  // Hàm tạo HTML cho sản phẩm
  const createProductHTML = (product, isFeatured) => `
    <div class="${isFeatured ? 'featured-item' : 'product-item'}">
      <a class="product-link" href="product-details.html?id=${product.productId}">
        <div class="product-image">
          <img src="${product.imageUrl}" alt="${product.productName}">
        </div>
        <div class="product-info">
          <h3 class="product-title">${product.productName}</h3>
          <p class="product-price">Giá: ${(product.price).toLocaleString('vi-VN')} VND</p>
        </div>
      </a>
      <button class="compare-btn" onclick="addToCompare(${product.productId})">
        <div class="icon-circle">
          <i class="fas fa-plus"></i>
        </div>
        So sánh
      </button>
    </div>
  `;

  // Hàm tải sản phẩm ban đầu
  const loadInitialProducts = () => {
    displayedProducts = allProducts.slice(0, productsPerPage); // Lấy sản phẩm đầu tiên
    productOffset = productsPerPage; // Cập nhật offset
    sortAndDisplayProducts(); // Áp dụng sắp xếp trước khi hiển thị
    toggleLoadMoreButton();
  };

  // Hàm tải thêm sản phẩm
  const loadMoreProducts = () => {
    const newProducts = allProducts.slice(productOffset, productOffset + productsPerPage);
    displayedProducts = [...displayedProducts, ...newProducts];
    productOffset += productsPerPage;

    sortAndDisplayProducts(); // Áp dụng sắp xếp trước khi hiển thị
    toggleLoadMoreButton();
  };

  // Hàm sắp xếp và hiển thị sản phẩm
  const sortAndDisplayProducts = () => {
    let sortedProducts = [...displayedProducts]; // Sắp xếp bản sao của mảng displayedProducts
    switch (currentSortOption) {
      case 'price-asc':
        sortedProducts.sort((a, b) => a.price - b.price); // Sắp xếp theo giá tăng dần
        break;
      case 'price-desc':
        sortedProducts.sort((a, b) => b.price - a.price); // Sắp xếp theo giá giảm dần
        break;
      default:
        break; // Giữ nguyên thứ tự ban đầu nếu không có sắp xếp
    }

    displayRegularProducts(sortedProducts);
  };

  // Hiển thị hoặc ẩn nút "Load More"
  const toggleLoadMoreButton = () => {
    loadMoreBtn.style.display = productOffset >= allProducts.length ? 'none' : 'block';
  };

  // Gắn sự kiện cho nút "Load More"
  loadMoreBtn.addEventListener('click', loadMoreProducts);

  // Xử lý sự kiện thay đổi sắp xếp
  sortOptions.addEventListener('change', (event) => {
    currentSortOption = event.target.value; // Cập nhật lựa chọn sắp xếp hiện tại
    sortAndDisplayProducts(); // Áp dụng sắp xếp mới ngay lập tức
  });

  // Khởi chạy
  initialize();
});
