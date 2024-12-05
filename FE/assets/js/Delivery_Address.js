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

// Hiển thị thông tin cá nhân
fetch(`http://localhost:8080/user/myInfor?id=${tokenRequest['id-user']}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${tokenRequest}`, // Gửi token trong header
    },
  })
  .then(response => response.json())
  .then(function(data){
    console.log(data);
    document.getElementById("inner-user-name").innerHTML = data.fullName
    document.getElementById("inner-user-phoneNumber").innerHTML = data.phoneNumber
  });

try {
    // Thêm tham số id-user vào URL
    const cusID = tokenRequest['id-customer'];
    fetch(`http://localhost:8080/user/shipping-address?customerID=${cusID}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${tokenRequest}`, // Gửi token trong header
      },
    })
    .then(response => response.json())
    .then(function(data){
        console.log("Địa chỉ shipping : " , data)
    })
    ;
} catch(err) {
    console.log(err)
}