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

function renderOrders(order) {
    var listOrderBlock = document.querySelector("#list-orders")

    var htmls = order.map(function(order){
        return `
            <li class="order-item-">
                    <div class="Cell-Order d-flex justify-content-between align-items-center">
                      <div class="block-image-name-quantity-Order d-flex justify-content-between align-items-center">
                        <div class="order-image">
                            <img src="${order.imageURL}" alt="Ảnh order">
                        </div> 
                        <div class="order-infor">
                            <h6 class="order-product-name">${order.productName}</h6>
                            <h6 class="order-quantity">${order.quantity}</h6>
                        </div>
                      </div>

                      <div class="block-Linetotal-Order">
                        <h6 class="order-Linetotal">${order.lineTotal}<span>VNĐ</span></h6>
                      </div>
                    </div>
            </li>
        `
    })

    listOrderBlock.innerHTML = htmls.join("");
}

try {
    // Thêm tham số id-user vào URL
    const cusID = tokenRequest['id-customer'];
    fetch(`http://localhost:8080/user/my-orders?customerID=${cusID}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${tokenRequest}`, // Gửi token trong header
      },
    })
    .then(response => response.json())
    .then(function(data){
        console.log("Đơn hàng của tôi : " , data)
        var orders = data;
    })
    ;
} catch(err) {
    console.log(err)
}