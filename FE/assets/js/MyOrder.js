// Xử lý lọc
document.querySelectorAll('#filter-nav .nav-link').forEach(navLink => {
    navLink.addEventListener('click', function (e) {
        e.preventDefault();

        // Loại bỏ lớp active khỏi các tab
        document.querySelectorAll('#filter-nav .nav-link').forEach(link => link.classList.remove('active'));

        // Thêm lớp active cho tab được chọn
        this.classList.add('active');

        // Lấy bộ lọc
        const filter = this.getAttribute('data-filter');

        // Lọc bảng
        document.querySelectorAll('table tbody tr').forEach(row => {
            if (filter === 'all' || row.getAttribute('data-category') === filter) {
                row.classList.remove('hidden');
            } else {
                row.classList.add('hidden');
            }
        });
    });
});

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


try {
    // Thêm tham số id-user vào URL
    const cusID = tokenRequest['id-customer'];
    fetch(`http://localhost:8080/user/orders/?customerID=${cusID}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${tokenRequest}`, // Gửi token trong header
      },
    })
    .then(response => response.json())
    .then(function(data){
        console.log("Đơn hàng của tôi : " , data)
    })
    ;
} catch(err) {
    console.log(err)
}