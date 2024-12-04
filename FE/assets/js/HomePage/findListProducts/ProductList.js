// Đường dẫn API
const apiUrl = "http://localhost:8080/user/search?keyword=SINHVIEN_VANPHONG";

// Hàm lấy dữ liệu từ API và hiển thị
async function fetchProducts() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error("Lỗi khi gọi API");
        }

        const products = await response.json();

        // Hiển thị sản phẩm
        const productList = document.getElementById("Find-products-list");
        productList.innerHTML = ""; // Xóa nội dung cũ nếu có

        products.forEach(product => {
            const productItem = document.createElement("div");
            productItem.className = "product-item";
            productItem.innerHTML = `
                <img src="${product.imageUrl}" alt="${product.productName}" style="width:150px;height:100px;">
                <h3>${product.productName}</h3>
                <p>Thương hiệu: ${product.productBrand}</p>
                <p>Model: ${product.model}</p>
                <p>Giá: $${product.price}</p>
                <p>Kho: ${product.stockQuantity} sản phẩm</p>
                <p>Bảo hành: ${product.warrantyPeriod} tháng</p>
                <p>Ngày phát hành: ${product.releaseDate}</p>
            `;
            productList.appendChild(productItem);
        });
    } catch (error) {
        console.error("Có lỗi xảy ra:", error);
    }
}

// Gọi hàm
fetchProducts();
