document.addEventListener("DOMContentLoaded", () => {
    // URL của API
    const apiURL = "http://localhost:8080/admin/product";

    // Hàm lấy dữ liệu từ API
    async function fetchData() {
        try {
            const response = await fetch(apiURL);
            const data = await response.json();

            // Hiển thị dữ liệu lên giao diện
            displayChart(data.quantityProductForChart);
            displayTopProducts(data.listTopProductSell);
            displayProductList(data.listProductDetail);
        } catch (error) {
            console.error("Lỗi khi gọi API:", error);
        }
    }

    // Hàm hiển thị biểu đồ Doanh thu theo tháng
    function displayChart(quantityProductForChart) {
        const ctx = document.getElementById("monthly-new-products-chart").getContext("2d");
        const labels = quantityProductForChart.map(item => `Tháng ${item.month}`);
        const data = quantityProductForChart.map(item => item.totalSold);

        new Chart(ctx, {
            type: "line",
            data: {
                labels: labels,
                datasets: [{
                    label: "Doanh thu theo tháng",
                    data: data,
                    borderColor: "rgb(75, 192, 192)",
                    backgroundColor: "rgba(75, 192, 192, 0.2)",
                    fill: true
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    // Hàm hiển thị danh sách Top sản phẩm
    function displayTopProducts(listTopProductSell) {
        const topProductsList = document.getElementById("top-10-products-list");
        topProductsList.innerHTML = ""; // Xóa danh sách cũ

        listTopProductSell.forEach((product, index) => {
            const listItem = document.createElement("li");
            listItem.className = "list-group-item";
            listItem.textContent = `${index + 1}. ${product.productName} - Số lượng bán: ${product.quantityOrdered}`;
            topProductsList.appendChild(listItem);
        });
    }

    // Hàm hiển thị danh sách sản phẩm
    function displayProductList(listProductDetail) {
        const productList = document.getElementById("product-list");
        productList.innerHTML = ""; // Xóa danh sách cũ

        listProductDetail.forEach((product, index) => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${product.productName}</td>
                <td>${product.productBrand}</td>
                <td>${product.price.toLocaleString()} USD</td>
            `;
            productList.appendChild(row);
        });
    }

    // Gọi hàm fetchData để tải dữ liệu từ API khi trang tải
    fetchData();
});
