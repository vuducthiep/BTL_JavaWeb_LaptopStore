// ẩn hiện box khi đăng nhập,đăng xuất
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
            loginLink.style.display = "none";   
            userInfo.style.display = "block";   
            orders.style.display = "block";    
            logout.style.display = "block";     
  
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
  // ẩn hiện box khi đăng nhập,đăng xuất