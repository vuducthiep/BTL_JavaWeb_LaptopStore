document.addEventListener("DOMContentLoaded", function () {
    const submitButton = document.getElementById("btn-submit");
  
    submitButton.addEventListener("click", function (event) {
      event.preventDefault(); // Ngăn không cho form reload trang
  
      // Lấy thông tin từ các trường trong form
      const city = document.getElementById("buyer_city").value;
      const district = document.getElementById("buyer_district").value;
      const ward = document.getElementById("buyer_ward").value;
      const specificAddress = document.getElementById("buyer_specific_address").value;
      const paymentMethod = document.querySelector('input[name="pay_method"]:checked');
  
      // Kiểm tra tính hợp lệ
      if (!city || !district || !ward || !specificAddress || !paymentMethod) {
        alert("Vui lòng điền đầy đủ thông tin bắt buộc và chọn phương thức thanh toán!");
        return;
      }
  
     // Tạo dữ liệu đơn hàng
      const orderData = {
        address: {
          city: city,
          district: district,
          ward: ward,
          specificAddress: specificAddress,
        },
        paymentMethod: paymentMethod.value,
        items: [],
      };
  
      const productRows = document.querySelectorAll("tbody tr");
      productRows.forEach(row => {
        const checkbox = row.querySelector(".product-checkbox");
        if (checkbox && checkbox.checked) {
          const productName = row.cells[2].innerText.trim();
          const price = row.querySelector(".product-price").innerText.trim();
          const quantity = row.querySelector(".quantity-input").value;
  
          orderData.items.push({
            name: productName,
            price: parseFloat(price.replace(/\./g, "")), // Chuyển giá sang số
            quantity: parseInt(quantity),
          });
        }
      });
  
      if (orderData.items.length === 0) {
        alert("Vui lòng chọn ít nhất một sản phẩm!");
        return;
      }
  
      // Gửi dữ liệu lên server qua API
    //   fetch("https://your-api-endpoint/orders", {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify(orderData),
    //   })
    //     .then(response => response.json())
    //     .then(data => {
    //       if (data.success) {
    //         alert("Đơn hàng của bạn đã được gửi thành công!");
    //       } else {
    //         alert("Có lỗi xảy ra khi gửi đơn hàng. Vui lòng thử lại!");
    //       }
    //     })
    //     .catch(error => {
    //       console.error("Error:", error);
    //       alert("Không thể kết nối với server. Vui lòng kiểm tra kết nối mạng!");
    //     });
    });
  });
  