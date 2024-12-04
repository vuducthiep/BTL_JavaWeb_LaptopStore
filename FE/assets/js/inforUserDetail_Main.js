const token = localStorage.getItem("authToken");
console.log("Token của khách hàng : ", token)
var tokenRequest = decodeJWT(token)

function decodeJWT(token) {
  const base64Url = token.split('.')[1]; // Lấy phần payload (phần giữa)
  const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  const jsonPayload = decodeURIComponent(
    atob(base64)
      .split('')
      .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
      .join('')
  );
  return JSON.parse(jsonPayload);
}

async function fetchUserInfo() {
  // Lấy token từ localStorage
  console.log("Token decode : ", decodeJWT(token))

  var tokenRequest = decodeJWT(token)
  console.log("....")
  console.log("Token id user : ", tokenRequest['id-user'])

  if (!token) {
    console.error("Token không tồn tại. Vui lòng đăng nhập trước.");
    return;
  }

  // apirequestInfor = "http://localhost:8080/user/myInfor" 

  // try {
  //   fetch(apirequestInfor , {
  //     method: 'POST',
  //     body : 
  //   })
  //     .then(response => response.json())
  //     .then(data => {

  try {
    // Thêm tham số id-user vào URL
    const userId = tokenRequest['id-user'];
    const response = await fetch(`http://localhost:8080/user/myInfor?id=${userId}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${tokenRequest}`, // Gửi token trong header
      },
    });

    // Xử lý phản hồi
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    console.log("User information: ", data);
    // Hiển thị thông tin tài khoản trong console
    // console.log("Thông tin tài khoản:", data);
    document.getElementById("infor-name").innerHTML = data.fullName
    document.getElementById("infor-phoneNumber").innerHTML = data.phoneNumber
    document.getElementById("infor-email").innerHTML = data.email
    // document.getElementById("infor-pw").innerHTML = data.password
    document.getElementById("inner-user-name").innerHTML = data.fullName
    document.getElementById("inner-user-phoneNumber").innerHTML = data.phoneNumber
  } catch (error) {
    console.error("Error fetching user information: ", error);
  }

  // Đọc dữ liệu JSON từ phản hồi
  // const userInfo = data;




}


// hàm xử lý sự kiện nút "Lưu thông tin"
async function handleUpdateInfor() {
  try {
      // Lấy thông tin từ các input
      const id_cus = tokenRequest['id-user']; // Lấy id-user từ token
      const fullName = document.getElementById("fullName-field").value;
      const phoneNumber = document.getElementById("phoneNumber-field").value;
      const email = document.getElementById("email-field").value;
      const password_currently = document.getElementById("password-currently-field").value;
      const password_new = document.getElementById("password-new-field").value;

      // Gửi yêu cầu tới API
      const response = await fetch("http://localhost:8080/user/update-infor", {
          method: "POST",
          headers: {
              "Content-Type": "application/json"
          },
          body: JSON.stringify({
              "userID": id_cus,  // ID người dùng
              "fullName": fullName, // Tên đầy đủ
              "phoneNumber": phoneNumber, // Số điện thoại
              "email": email, // Email
              "password": password_currently, // Mật khẩu hiện tại
              "newPassword": password_new, // Mật khẩu mới
          }),
      });

      if (response.ok) {
          alert("Cập nhật thông tin thành công!"); // Thông báo cho người dùng
          location.reload(true); // Reload lại trang sau khi cập nhật
      } else {
          // Nếu xảy ra lỗi từ server
          alert(`Cập nhật thất bại: ${errorData.message || "Lỗi không xác định!"}`);
          location.reload(); // Reload trang sau khi cập nhật 
      }
  } catch (error) {
      // Xử lý lỗi không mong muốn
      console.error("Lỗi khi cập nhật thông tin:", error);
      alert("Đã xảy ra lỗi. Vui lòng thử lại!");
      location.reload(); // Reload trang sau khi cập nhật 
  }
}

// Gọi hàm để lấy thông tin tài khoản
fetchUserInfo();

