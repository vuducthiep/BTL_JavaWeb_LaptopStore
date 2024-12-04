
const urlParams = new URLSearchParams(window.location.search);
const queryParam = urlParams.get("q"); 

if (queryParam) {
    
    const apiUrl = `http://localhost:8080/user/search?keyword=${queryParam}`;

    async function fetchProducts() {
        try {
            const response = await fetch(apiUrl);
            if (!response.ok) {
                throw new Error("Lỗi khi gọi API");
            }

            const products = await response.json();
            console.log(products)
            
            const productList = document.getElementById("Find-products-list");
            productList.innerHTML = ""; 

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

    fetchProducts();
} else {
    console.error("Tham số 'q' không tồn tại trong URL");
}
