document.addEventListener("DOMContentLoaded", () => {
  // Lấy nút "Gửi đơn hàng"
  const submitButton = document.getElementById('btn-submit');

  // Thêm sự kiện click cho nút "Gửi đơn hàng"
  submitButton.addEventListener('click', (event) => {
      event.preventDefault(); // Ngăn gửi form mặc định

      // Kiểm tra đã chọn địa chỉ hay chưa
      const selectedAddress = document.querySelector('input[name="address"]:checked');
      if (!selectedAddress) {
          alert('Vui lòng chọn địa chỉ giao hàng.');
          return;
      }

      // Kiểm tra đã chọn phương thức thanh toán hay chưa
      const selectedPaymentMethod = document.querySelector('input[name="pay_method"]:checked');
      if (!selectedPaymentMethod) {
          alert('Vui lòng chọn phương thức thanh toán.');
          return;
      }

      // Kiểm tra đã chọn ít nhất một sản phẩm hay chưa
      const tienElement = document.getElementById('tien-phai-thanh-toan');
      const tien= parseFloat(tienElement.textContent);
      if (tien <= 0) {
          alert('Vui lòng chọn ít nhất một sản phẩm để mua.');
          return;
      }

      // Nếu mọi kiểm tra đều hợp lệ
      alert('Đơn hàng đã được gửi thành công!');
      // Sau đó, bạn có thể thực hiện hành động gửi form hoặc API
  });
});
