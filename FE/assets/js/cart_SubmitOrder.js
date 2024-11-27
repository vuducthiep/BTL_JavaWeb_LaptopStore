document.addEventListener("DOMContentLoaded", () => {
    // Lấy nút "Gửi đơn hàng"
    const submitButton = document.getElementById("btn-submit");
    const token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTIyMzM0NDU1IiwiaWQtY3VzdG9tZXIiOjEsInNjb3BlIjoiY3VzdG9tZXIiLCJpc3MiOiJsYXB0b3BhYmMuY29tIiwiaWQtY2FydCI6MSwiZXhwIjoxNzMyNjgyMjcxLCJpYXQiOjE3MzI2Nzg2NzF9.yWZ7WeDgtY80Rl4hMriHboHoXJd7rR1FppDltaVNJJFZyH83EFq_ed2DEBjKJl_nmKBmseXKdydqF-9_THq6ag';


    // Thêm sự kiện click cho nút "Gửi đơn hàng"
    submitButton.addEventListener("click", async (event) => {
        event.preventDefault(); // Ngăn gửi form mặc định

        // Kiểm tra dữ liệu
        const selectedAddress = document.querySelector('input[name="address"]:checked');
        if (!selectedAddress) {
            alert("Vui lòng chọn địa chỉ giao hàng.");
            return;
        }

        const selectedPaymentMethod = document.querySelector('input[name="paymentMethod"]:checked');
        if (!selectedPaymentMethod) {
            alert("Vui lòng chọn phương thức thanh toán.");
            return;
        }

        const totalAmountElement = document.getElementById("tien-phai-thanh-toan");
        const totalAmount = parseFloat(totalAmountElement.textContent.replace(" VND", "").replace(",", ""));
        if (totalAmount <= 0) {
            alert("Vui lòng chọn ít nhất một sản phẩm để mua.");
            return;
        }

        // Thu thập thông tin sản phẩm
        const products = [];
        const productRows = document.querySelectorAll(".product-row");
        productRows.forEach(row => {
            const checkbox = row.querySelector(".select-product");
            if (checkbox && checkbox.checked) {
                const productID = row.dataset.productId;
                const price = parseFloat(row.querySelector(".product-price").textContent.replace(",", ""));
                const quantity = parseInt(row.querySelector(".quantity-input").value, 10);
                if (productID && price > 0 && quantity > 0) {
                    products.push({ productID, price, quantity });
                }
            }
        });

        if (products.length === 0) {
            alert("Vui lòng chọn sản phẩm hợp lệ.");
            return;
        }

        // Tạo JSON để gửi
        const orderData = {
            orderDate: new Date().toISOString().split("T")[0], // Ngày hiện tại
            totalAmount: totalAmount,
            estimatedDeliveryDate: new Date().toISOString().split("T")[0], // Ví dụ: cùng ngày
            actualDeliveryDate: new Date().toISOString().split("T")[0], // Chưa có
            paymentMethodID: selectedPaymentMethod.value === "card" ? 1 : 2, // 1: Online, 2: Offline
            addressID: selectedAddress.value, // ID địa chỉ
            orderDetails: products,
        };

        // Gửi API
        try {
            const response = await fetch("http://localhost:3000/orders", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(orderData),
            });

            if (response.ok) {
                alert("Đơn hàng đã được gửi thành công!");
                // Xử lý logic sau khi gửi thành công (reload, chuyển trang, v.v.)
            } else {
                alert("Có lỗi xảy ra khi gửi đơn hàng. Vui lòng thử lại!");
            }
        } catch (error) {
            console.error("Lỗi khi gửi API:", error);
            alert("Không thể gửi đơn hàng. Vui lòng kiểm tra kết nối!");
        }
    });
});
