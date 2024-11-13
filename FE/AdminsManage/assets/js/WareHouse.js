let currentProductID = null;

// Hàm gọi API để lấy dữ liệu chi tiết sản phẩm
function loadProductDetails(productID) {
    if (!productID) {
        console.log('Không có ID sản phẩm');
        return;
    }

    const productApiUrl = `http://localhost:8080/admin/warehouse/update/${productID}`;

    fetch(productApiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Lỗi khi lấy dữ liệu sản phẩm');
            }
            return response.json();
        })
        .then(data => {
            console.log('Dữ liệu sản phẩm:', data);

            // Cập nhật các trường trong form chỉnh sửa
            updateFormFields(data);

            // Hiển thị form chỉnh sửa
            const editProductForm = document.getElementById('editProductForm');
            if (editProductForm) {
                editProductForm.style.display = 'block';
            }
        })
        .catch(error => {
            console.error('Error fetching product details:', error);
            alert('Đã xảy ra lỗi khi lấy dữ liệu sản phẩm: ' + error.message);
        });
}

// Hàm cập nhật các trường trong form chỉnh sửa
function updateFormFields(data) {
    const fields = [
        { id: 'editProductName', value: data.productName },
        { id: 'editProductBrand', value: data.brand },
        { id: 'editProductModel', value: data.model },
        { id: 'editProductPrice', value: data.price },
        { id: 'editProductReleaseDate', value: data.releaseDate.split("T")[0] },
        { id: 'editProductWarranty', value: data.warrantyPeriod },
        { id: 'editProductBatchCode', value: data.productBatchCode },
        { id: 'editProductDimension', value: data.dimension },
        { id: 'editProductVolume', value: data.volume },
        { id: 'editProductMinStockLevel', value: data.minStockLevel },
        { id: 'editProductMaxStockLevel', value: data.maxStockLevel },
        { id: 'editProductQuantity', value: data.quantity }
    ];

    fields.forEach(field => {
        const element = document.getElementById(field.id);
        if (element) {
            element.value = field.value;
        }
    });
}

// Hàm lưu thay đổi sản phẩm
function saveProductChanges() {
    if (!currentProductID) {
        console.log('Không có ID sản phẩm để lưu');
        return;
    }

    const warehouseID = parseInt(getWarehouseIDFromUrl());
    if (!warehouseID) {
        console.log('Không có warehouseID trong URL');
        return;
    }

    // Lấy dữ liệu từ form
    const updatedProduct = {
        productId: currentProductID, // Sử dụng currentProductID cho productId
        productName: document.getElementById('editProductName').value,
        brand: document.getElementById('editProductBrand').value,
        model: document.getElementById('editProductModel').value,
        price: parseFloat(document.getElementById('editProductPrice').value),
        releaseDate: document.getElementById('editProductReleaseDate').value + "T00:00:00.000+00:00", // Đảm bảo định dạng
        warrantyPeriod: parseInt(document.getElementById('editProductWarranty').value),
        productBatchCode: document.getElementById('editProductBatchCode').value,
        dimension: document.getElementById('editProductDimension').value,
        volume: parseFloat(document.getElementById('editProductVolume').value),
        minStockLevel: parseInt(document.getElementById('editProductMinStockLevel').value),
        maxStockLevel: parseInt(document.getElementById('editProductMaxStockLevel').value),
        quantity: parseInt(document.getElementById('editProductQuantity').value),
        productInWareHouseId: warehouseID // Sử dụng warehouseID lấy từ URL
    };

    // Gửi yêu cầu PUT
    fetch(`http://localhost:8080/admin/warehouse/update/${currentProductID}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedProduct)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(text || `Lỗi ${response.status}`);
            });
        }
        return response.json();
    })
    .then(data => {
        console.log('Sản phẩm đã được cập nhật:', data);
        alert('Sản phẩm đã được cập nhật thành công!');
        // Đóng form sau khi lưu thành công
        const editProductForm = document.getElementById('editProductForm');
        if (editProductForm) {
            editProductForm.style.display = 'none';
        }
    })
    .catch(error => {
        console.error('Lỗi khi gửi yêu cầu PUT:', error.message);
        alert('Đã xảy ra lỗi khi cập nhật sản phẩm: ' + error.message);
    });
}

// Đóng form chỉnh sửa
document.getElementById('closeEditForm').addEventListener('click', () => {
    const editProductForm = document.getElementById('editProductForm');
    if (editProductForm) {
        editProductForm.style.display = 'none';
    }
});

// Lắng nghe sự kiện nhấn vào nút "Sửa" trong danh sách sản phẩm
document.getElementById('productList').addEventListener('click', (event) => {
    if (event.target && event.target.classList.contains('btn-warning')) {
        currentProductID = parseInt(event.target.getAttribute('data-product-id'));
        if (currentProductID) {
            loadProductDetails(currentProductID);
        } else {
            console.log('Không có ID sản phẩm');
        }
    }
});

// Lắng nghe sự kiện nhấn vào nút "Lưu"
document.getElementById('saveEditForm').addEventListener('click', () => {
    saveProductChanges();
});

// Hàm lấy warehouseID từ URL
function getWarehouseIDFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('warehouseID');
}






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
                        <button class="btn btn-warning btn-sm" data-product-id="${product.productId}">Sửa</button>
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

            // Cập nhật tham số warehouseID trong URL mà không tải lại trang
            const currentUrl = new URL(window.location.href);
            currentUrl.searchParams.set('warehouseID', warehouseID); // Cập nhật tham số trong URL
            window.history.pushState({}, '', currentUrl); // Cập nhật URL mà không tải lại trang
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
    // Lấy warehouseID từ URL nếu có, nếu không thì mặc định là kho 1
    const urlParams = new URLSearchParams(window.location.search);
    const warehouseID = urlParams.get('warehouseID') || 1;
    loadWarehouseData(warehouseID);
});

