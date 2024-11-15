// Khi nhấn nút "Thêm sản phẩm"
document.getElementById("add-product-btn").addEventListener("click", function () {
    loadForm('formAdd.html', '#add-product-form-container');
    document.getElementById("add-product-form-container").style.display = "block"; // Hiển thị form thêm
    document.getElementById("edit-product-form-container").style.display = "none"; // Ẩn form sửa
  });
  
  // Khi nhấn nút "Sửa sản phẩm" (giả sử bạn có hàm này trong trang sản phẩm)
  function showEditForm(productId) {
    loadForm('formEdit.html', '#edit-product-form-container');
    document.getElementById("add-product-form-container").style.display = "none"; // Ẩn form thêm
    document.getElementById("edit-product-form-container").style.display = "block"; // Hiển thị form sửa
  
    // Bạn có thể sử dụng `productId` để gửi thông tin sản phẩm vào form sửa
  }
  
  // Hàm tải nội dung form
  function loadForm(formName, containerId) {
    fetch(`FormProduct/${formName}`)
      .then(response => response.text())
      .then(data => {
        document.querySelector(containerId).innerHTML = data;
      })
      .catch(error => {
        console.error('Error loading form:', error);
      });
  }
  