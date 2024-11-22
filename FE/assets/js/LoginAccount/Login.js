async function login(event) {
  event.preventDefault(); // Ngăn form gửi yêu cầu mặc định

  // Lấy dữ liệu từ form
  const username = document.getElementById("loGin").value;
  const password = document.getElementById("passWord").value;

  try {
    // Gửi yêu cầu POST đến API
    const response = await fetch("http://localhost:8080/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ phoneNumber: username, password: password }),
    });

    if (!response.ok) {
      throw new Error("Không thể kết nối tới server. Vui lòng thử lại!");
    }

    const data = await response.json();

    // Kiểm tra trạng thái đăng nhập
    if (data.status) {
      alert("Đăng nhập thành công!"); // Thông báo đăng nhập thành công

      // In ra token
      console.log("Token nhận được từ server:", data.token);

      // Lưu token vào localStorage
      localStorage.setItem("authToken", data.token);
      localStorage.setItem("isLoggedIn", "true");

      // Giải mã token để lấy vai trò
      const payload = JSON.parse(atob(data.token.split(".")[1])); // Decode phần payload của token
      console.log('Payload của mày :',payload)
      // In ra vai trò (role)
      console.log("Vai trò của người dùng:", payload.scope);

      // Kiểm tra vai trò (role)
      if (payload.scope === "admin") {
        // Nếu là admin, chuyển đến trang admin
        window.location.href =
          "/Laptop-Store/BTL-TTCSN-20241IT6040001-NHOM7/FE/AdminsManage/Dashboard.html";
      } else if (payload.scope === "customer") {
        // Nếu là user, chuyển đến trang người dùng
        window.location.href =
          "/Laptop-Store/BTL-TTCSN-20241IT6040001-NHOM7/FE/HomePage/index.html";
      } else {
        // Vai trò không xác định
        alert("Vai trò không hợp lệ!");
      }
    } else {
      alert("Tên đăng nhập hoặc mật khẩu không đúng!");
    }
  } catch (error) {
    alert(`Lỗi: ${error.message}`);
  }
}
