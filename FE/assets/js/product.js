
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

document.getElementById('highlight-btn').addEventListener('click', function () {
  document.getElementById('highlight-specs').classList.remove('hidden');
  document.getElementById('all-specs').classList.add('hidden');
});

document.getElementById('all-spec-btn').addEventListener('click', function () {
  document.getElementById('all-specs').classList.remove('hidden');
  document.getElementById('highlight-specs').classList.add('hidden');
});

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

// Mặc định hiển thị thông số nổi bật
document.addEventListener('DOMContentLoaded', () => {
  const highlightSpecs = document.getElementById('highlight-specs');
  highlightSpecs.classList.remove('hidden');
});



