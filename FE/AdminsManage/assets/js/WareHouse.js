// API URL
const apiUrl = 'http://localhost:8080/admin/warehouse/1'; // Thay URL này nếu cần

// Hàm load dữ liệu khi chọn kho
function loadWarehouseData(warehouseID) {
    // Thay URL kho theo warehouseID
    const warehouseApiUrl = `http://localhost:8080/admin/warehouse/${warehouseID}`;

    fetch(warehouseApiUrl)
        .then(response => response.json())
        .then(data => {
            // Lấy combobox
            const combobox = document.getElementById('combobox');
            combobox.innerHTML = ''; // Xóa các option cũ nếu có

            // Thêm từng kho vào combobox
            data.warehouseList.forEach(warehouse => {
                const option = document.createElement('option');
                option.value = warehouse.warehouseID;
                option.textContent = warehouse.warehouseName;
                combobox.appendChild(option);
            });

            // Đặt giá trị mặc định cho combobox là kho hiện tại
            combobox.value = warehouseID;

            // Cập nhật thông tin kho
            document.getElementById('warehouseName').innerText = data.warehouseInfo.warehouseName;
            document.getElementById('warehouseID').innerText = `ID: ${data.warehouseInfo.warehouseID}`;
            document.getElementById('warehouseAddress').innerText = `Địa chỉ: ${data.warehouseInfo.address}`;
            document.getElementById('warehouseType').innerText = `Loại nhà kho: ${data.warehouseInfo.warehouseType}`;
            document.getElementById('warehouseStatus').innerText = `Trạng thái: ${data.warehouseInfo.status}`;

            // Cập nhật số lượng sản phẩm trong kho
            document.getElementById('productQuantity').innerText = data.totalQuantity;
            document.getElementById('productExportQuantity').innerText = data.listExportReceipt.reduce((total, item) => total + item.quantity, 0);
            document.getElementById('productImportQuantity').innerText = data.listImportReceipt.reduce((total, item) => total + item.quantity, 0);

            // Hiển thị danh sách sản phẩm trong kho
            let productListHtml = '';
            data.listProductsInWarehouse.forEach(product => {
                productListHtml += `
                     <li>
            <img src="path/to/product-image.jpg" alt="${product.productName}" />
            <span>${product.productName}</span> - 
            <span>Số lượng: ${product.quantity}</span>
            <button class="btn btn-warning btn-sm" onclick="editProduct(${product.productID})">Sửa</button>
          </li>
        `;
            });
            document.getElementById('productList').innerHTML = productListHtml;

            // Hiển thị phiếu xuất kho
            let exportListHtml = '';
            data.listExportReceipt.forEach(receipt => {
                exportListHtml += `
                    <li>
                        <img src="path/to/product-image.jpg" alt="${receipt.productName}" />
                        <span>${receipt.productName}</span> - 
                        <span>Ngày xuất: ${receipt.date}</span> - 
                        <span>Số lượng: ${receipt.quantity}</span>
                    </li>
                `;
            });
            document.getElementById('exportList').innerHTML = exportListHtml;

            // Hiển thị phiếu nhập kho
            let importListHtml = '';
            data.listImportReceipt.forEach(receipt => {
                importListHtml += `
                    <li>
                        <img src="path/to/product-image.jpg" alt="${receipt.productName}" />
                        <span>${receipt.productName}</span> - 
                        <span>Ngày nhập: ${receipt.date}</span> - 
                        <span>Số lượng: ${receipt.quantity}</span>
                    </li>
                `;
            });
            document.getElementById('importList').innerHTML = importListHtml;
        })
        .catch(error => console.error('Error fetching data:', error));
}

// Sự kiện lắng nghe thay đổi combobox
document.getElementById('combobox').addEventListener('change', (event) => {
    const selectedWarehouseID = event.target.value;
    loadWarehouseData(selectedWarehouseID);
});

// Gọi hàm để load kho mặc định khi trang được tải (Kho 1)
document.addEventListener('DOMContentLoaded', () => {
    loadWarehouseData(1);
});
