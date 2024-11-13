// Hàm gọi API để lấy dữ liệu chi tiết sản phẩm
function loadProductDetails(productID) {
    if (!productID) {
        console.log('Không có ID sản phẩm');
        return;
    }

    const productApiUrl = `http://localhost:8080/admin/warehouse/update/${productID}`;

    fetch(productApiUrl)
        .then(response => {
            // Kiểm tra xem phản hồi có hợp lệ không
            if (!response.ok) {
                throw new Error('Lỗi khi lấy dữ liệu sản phẩm');
            }
            return response.json();
        })
        .then(data => {
            console.log('Dữ liệu sản phẩm:', data);  // Log dữ liệu sản phẩm để kiểm tra

            // Cập nhật giá trị các trường trong form chỉnh sửa
            updateFormFields(data);

            // Hiển thị form chỉnh sửa
            const editProductForm = document.getElementById('editProductForm');
            if (editProductForm) {
                editProductForm.style.display = 'block'; // Hiển thị form chỉnh sửa
            }
        })
        .catch(error => {
            console.error('Error fetching product details:', error);
            alert('Đã xảy ra lỗi khi lấy dữ liệu sản phẩm: ' + error.message);
        });
}
function getWarehouseIDFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('warehouseID');  // Trả về giá trị của 'warehouseID' từ URL
}


// Hàm cập nhật các trường trong form chỉnh sửa
function updateFormFields(data) {
    const fields = [
        { id: 'editProductName', value: data.productName },
        { id: 'editProductBrand', value: data.brand },
        { id: 'editProductModel', value: data.model },
        { id: 'editProductPrice', value: data.price },
        { id: 'editProductReleaseDate', value: data.releaseDate.slice(0, 10) },  // Chuyển đổi định dạng ngày
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



function saveProductChanges() {
    if (!currentProductID) {
        console.log('Không có ID sản phẩm để lưu');
        return;
    }

    const warehouseID = getWarehouseIDFromUrl();  // Lấy giá trị warehouseID từ URL
    if (!warehouseID) {
        console.log('Không có warehouseID trong URL');
        return;
    }

    const updatedProduct = {
        productId: currentProductID,  // ID sản phẩm cần cập nhật
        productName: document.getElementById('editProductName').value,
        brand: document.getElementById('editProductBrand').value,
        model: document.getElementById('editProductModel').value,
        price: parseFloat(document.getElementById('editProductPrice').value),
        releaseDate: new Date(document.getElementById('editProductReleaseDate').value).toISOString(),
        warrantyPeriod: parseInt(document.getElementById('editProductWarranty').value, 10),
        productBatchCode: document.getElementById('editProductBatchCode').value,
        dimension: document.getElementById('editProductDimension').value,
        volume: parseFloat(document.getElementById('editProductVolume').value),
        minStockLevel: parseInt(document.getElementById('editProductMinStockLevel').value, 10),
        maxStockLevel: parseInt(document.getElementById('editProductMaxStockLevel').value, 10),
        quantity: parseInt(document.getElementById('editProductQuantity').value, 10),
        productInWareHouseId: parseInt(warehouseID, 10)  // Thêm warehouseID vào dữ liệu
    };

    const productApiUrl = `http://localhost:8080/admin/warehouse/update/${currentProductID}`;
    console.log('Dữ liệu gửi đi:', updatedProduct);

    fetch(productApiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedProduct)
    })
    .then(response => {
        if (!response.ok) {
            // Lấy chi tiết lỗi nếu có từ phản hồi của server
            return response.text().then(text => {
                throw new Error(text || `Lỗi ${response.status}`);
            });
        }
        return response.json();
    })
    .then(data => {
        console.log('Sản phẩm đã được cập nhật:', data);
        alert('Sản phẩm đã được lưu thành công!');

        // Ẩn form sau khi lưu thành công
        const editProductForm = document.getElementById('editProductForm');
        if (editProductForm) {
            editProductForm.style.display = 'none';
        }
    })
    .catch(error => {
        // Hiển thị lỗi chi tiết từ server trong console và thông báo cho người dùng
        console.error('Lỗi khi lưu sản phẩm:', error.message);
        alert('Đã xảy ra lỗi khi lưu sản phẩm: ' + error.message);
    });
}


// Đóng form chỉnh sửa
document.getElementById('closeEditForm').addEventListener('click', () => {
    const editProductForm = document.getElementById('editProductForm');
    if (editProductForm) {
        editProductForm.style.display = 'none';  // Ẩn form khi nhấn Đóng
    }
});

let currentProductID = null; // Biến toàn cục lưu productID

// Lắng nghe sự kiện nhấn vào nút "Sửa" trong danh sách sản phẩm
document.getElementById('productList').addEventListener('click', (event) => {
    if (event.target && event.target.classList.contains('btn-warning')) {
        currentProductID = event.target.getAttribute('data-product-id');
        if (currentProductID) {
            console.log('Product ID:', currentProductID);  // Kiểm tra console log
            loadProductDetails(currentProductID); // Gọi hàm nếu có productID
        } else {
            console.log('Không có ID sản phẩm');
        }
    }
});

// Lắng nghe sự kiện nhấn vào nút "Lưu"
document.getElementById('saveEditForm').addEventListener('click', () => {
    saveProductChanges();  // Gọi hàm lưu sản phẩm
});





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

