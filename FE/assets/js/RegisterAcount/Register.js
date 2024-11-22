async function registerUser(fullName, email, password, phoneNumber) {
    const usersApiUrl = "http://localhost:3000/Users";
    const customersApiUrl = "http://localhost:3000/Customers";
  
    try {
      // Kiểm tra xem email đã tồn tại chưa
      const existingUsersResponse = await fetch(usersApiUrl);
      const existingUsers = await existingUsersResponse.json();
      const emailExists = existingUsers.some(user => user.Email === email);
  
      if (emailExists) {
        return { success: false, message: "Email đã tồn tại, vui lòng sử dụng email khác." };
      }
  
      // Tạo mới người dùng
      const registrationDate = new Date().toISOString().split("T")[0]; // YYYY-MM-DD
      const newUser = {
        FullName: fullName,
        Email: email,
        Password: password, // Mã hóa trong thực tế
        PhoneNumber: phoneNumber || null,
        UserType: "customer",
        RegistrationDate: registrationDate
      };
  
      const userResponse = await fetch(usersApiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newUser)
      });
  
      if (!userResponse.ok) {
        throw new Error("Không thể thêm người dùng mới.");
      }
  
      const createdUser = await userResponse.json();
  
      // Đồng bộ ID giữa Users và Customers
      const newCustomer = {
        CustomerID: createdUser.UserID, // Đảm bảo ID khớp
        UserID: createdUser.UserID,
        RegistrationDate: registrationDate,
        Status: "active"
      };
  
      const customerResponse = await fetch(customersApiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newCustomer)
      });
  
      if (!customerResponse.ok) {
        throw new Error("Không thể thêm thông tin khách hàng.");
      }
  
      const createdCustomer = await customerResponse.json();
  
      return {
        success: true,
        message: "Đăng ký thành công!",
        user: createdUser,
        customer: createdCustomer
      };
    } catch (error) {
      console.error("Lỗi khi đăng ký:", error.message);
      return { success: false, message: error.message };
    }
  }