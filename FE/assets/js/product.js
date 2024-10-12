
document.addEventListener('DOMContentLoaded', function () {
  // Lấy ID sản phẩm từ URL
  const params = new URLSearchParams(window.location.search);
  const productId = params.get('id'); // Lấy giá trị của tham số id

  if (productId) {
    // Gọi API để lấy chi tiết sản phẩm
    fetch(`http://localhost:3000/products/${productId}`)
      .then(response => response.json())
      .then(product => {
        // Hiển thị thông tin chi tiết sản phẩm
        displayProductDetails(product);
      })
      .catch(error => console.error('Error fetching product details:', error));
  } else {
    console.error('Product ID not found in URL');
  }

  function displayProductDetails(product) {
    document.getElementById('product-name').textContent = product.name;
    document.getElementById('product-name-breadcrumb').textContent = product.name; // Thêm đoạn này để hiển thị tên trong breadcrumb
    document.getElementById('product-image').src = product.thumbnail;
    document.getElementById('product-status').textContent = "Tình Trạng: Còn hàng"; // Sửa lại id ở đây
    document.getElementById('product-description').textContent = product.description;
    document.getElementById('product-price').textContent = `Giá: ${product.price} VND`;
}
});

// Hàm để hiển thị thông số nổi bật
function showHighlightSpecs() {
  document.getElementById('highlight-specs').classList.remove('hidden');
  document.getElementById('all-specs').classList.add('hidden');
  
  // Thêm class active cho nút "Thông số nổi bật"
  document.getElementById('highlight-btn').classList.add('active');
  document.getElementById('all-spec-btn').classList.remove('active');
}

// Hàm để hiển thị tất cả thông số
function showAllSpecs() {
  document.getElementById('all-specs').classList.remove('hidden');
  document.getElementById('highlight-specs').classList.add('hidden');
  
  // Thêm class active cho nút "Tất cả thông số"
  document.getElementById('all-spec-btn').classList.add('active');
  document.getElementById('highlight-btn').classList.remove('active');
}

// Thêm sự kiện click cho các nút hiển thị
document.getElementById('highlight-btn').addEventListener('click', showHighlightSpecs);
document.getElementById('all-spec-btn').addEventListener('click', showAllSpecs);

// Lắng nghe sự kiện click trên các mục tiêu lớn
document.querySelectorAll('.section-title').forEach(item => {
  item.addEventListener('click', () => {
      const toggleClass = item.getAttribute('data-toggle');
      
      // Tìm các hàng liên quan và chuyển đổi hiển thị
      const items = document.querySelectorAll(`.${toggleClass}`);
      items.forEach(row => {
          row.classList.toggle('hidden'); // Ẩn/hiện hàng thông số
      });

      // Thay đổi mũi tên
      const arrow = item.querySelector('.arrow');
      if (items[0].classList.contains('hidden')) {
          arrow.classList.remove('up'); // Hiện mũi tên xuống
          arrow.innerHTML = '▼';
      } else {
          arrow.classList.add('up'); // Hiện mũi tên lên
          arrow.innerHTML = '▲';
      }
  });
});

// Mặc định hiển thị thông số nổi bật khi tải trang
document.addEventListener('DOMContentLoaded', () => {
  showHighlightSpecs(); // Gọi hàm để hiển thị thông số nổi bật
});




