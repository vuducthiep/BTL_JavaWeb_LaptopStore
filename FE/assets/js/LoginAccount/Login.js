async function loginUser(email, password) {
    try {
      // Gửi yêu cầu GET để lấy danh sách Users từ API giả lập
      const usersResponse = await fetch("http://localhost:3000/Users");
      if (!usersResponse.ok) {
        throw new Error("Không thể kết nối tới API Users.");
      }
  
      const users = await usersResponse.json();
  
      // Tìm user dựa trên email
      const user = users.find(u => u.Email === email);
  
      if (!user) {
        return {
          success: false,
          message: "Email không tồn tại.",
        };
      }
  
      // Kiểm tra mật khẩu
      if (user.Password !== password) {
        return {
          success: false,
          message: "Mật khẩu không đúng.",
        };
      }
  
      // Đăng nhập thành công
      return {
        success: true,
        message: "Đăng nhập thành công!",
        user: {
          UserID: user.UserID,
          FullName: user.FullName,
          Email: user.Email,
          UserType: user.UserType,
        },
      };
    } catch (error) {
      return {
        success: false,
        message: `Lỗi: ${error.message}`,
      };
    }
  }