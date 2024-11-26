document.addEventListener('DOMContentLoaded', () => {
    const tableBody = document.getElementById('product-list');
    const totalPriceElement = document.querySelector('.total-price');
    const totalElement = document.querySelector('.total-amount');
    const bankTransferInfo = document.getElementById('bank-transfer-info');
    const submitButton = document.getElementById('btn-submit');
    const selectAllCB = document.getElementById('selectAll');

    // Fetch product data from API
    async function fetchProducts() {
        try {
            const response = await fetch('https://9e093a2f-9308-4497-881c-6b5250aec5c8.mock.pstmn.io/cart');
            if (!response.ok) throw new Error('Failed to fetch products');
            const products = await response.json();
            populateTable(products);
            updateTotal();
        } catch (error) {
            console.error('Error fetching products:', error);
        }
    }
    fetchProducts();

    fetch('https://9e093a2f-9308-4497-881c-6b5250aec5c8.mock.pstmn.io/address')  // URL API của bạn
    .then(response => {
        if (!response.ok) {
            throw new Error('Lỗi kết nối đến API');
        }
        return response.json();  // Chuyển dữ liệu trả về thành JSON
    })
    .then(data => {
        console.log('Danh sách địa chỉ:', data.address);  // Hiển thị dữ liệu trong console
        if (data && data.address) {
            renderAddresses(data.address);  // Gọi hàm renderAddresses để hiển thị dữ liệu
        } else {
            throw new Error('Dữ liệu không hợp lệ');
        }
    })
    .catch(error => {
        console.error('Lỗi khi tải danh sách địa chỉ:', error);
        alert('Không thể tải danh sách địa chỉ. Vui lòng thử lại sau.');
    });

    function renderAddresses(addresses) {
        const addressTable = document.getElementById('address-table');  // Lấy bảng để hiển thị dữ liệu
        const tbody = addressTable.querySelector('tbody');  // Lấy phần thân của bảng
    
        // Xóa dữ liệu cũ nếu có
        tbody.innerHTML = '';
    
        addresses.forEach(address => {
            // Tạo một dòng mới cho mỗi địa chỉ
            const row = document.createElement('tr');
    
            // Tạo các cột dữ liệu (td) cho mỗi địa chỉ
            const streetCell = document.createElement('td');
            streetCell.textContent = address.street;
    
            const wardCell = document.createElement('td');
            wardCell.textContent = address.ward;
    
            const districtCell = document.createElement('td');
            districtCell.textContent = address.district;
    
            const cityCell = document.createElement('td');
            cityCell.textContent = address.city;
    
            // Tạo radio button để chọn
            const selectCell = document.createElement('td');
            const radioButton = document.createElement('input');
            radioButton.type = 'radio';
            radioButton.name = 'address';  // Đảm bảo mỗi nhóm radio button có tên giống nhau
            radioButton.value = address.id;  // Dùng ID địa chỉ làm giá trị
    
            // Thêm radio button vào cột
            selectCell.appendChild(radioButton);
    
            // Thêm các cột vào dòng
            row.appendChild(streetCell);
            row.appendChild(wardCell);
            row.appendChild(districtCell);
            row.appendChild(cityCell);
            row.appendChild(selectCell);
    
            // Thêm dòng vào bảng
            tbody.appendChild(row);
        });
    }
    
    
    
    async function loadPaymentMethods() {
        try {
          const response = await fetch("https://9e093a2f-9308-4497-881c-6b5250aec5c8.mock.pstmn.io/payment-method"); // Gọi API
          if (!response.ok) {
            throw new Error("Không thể lấy dữ liệu từ API");
          }
          const data = await response.json(); // Chuyển đổi JSON
          const paymentMethods = data.paymentMethods; // Lấy danh sách phương thức thanh toán
    
          // Lấy phần tử tbody
          const tableBody = document.getElementById("paymentMethodsTable");
          tableBody.innerHTML = ""; // Xóa nội dung cũ (nếu có)
    
          // Duyệt qua danh sách phương thức thanh toán và thêm vào bảng
          paymentMethods.forEach(method => {
            // Tạo một dòng mới
            const row = document.createElement("tr");
    
            // Cột radio button
            const radioCell = document.createElement("td");
            radioCell.innerHTML = `<input id="pay_${method.id}_input" name="pay_method" value="${method.id}" type="radio">`;
    
            // Cột tên phương thức
            const labelCell = document.createElement("td");
            labelCell.innerHTML = `<label for="pay_${method.id}_input">${method.name}</label>`;
    
            // Gắn cột vào hàng
            row.appendChild(radioCell);
            row.appendChild(labelCell);
    
            // Gắn hàng vào bảng
            tableBody.appendChild(row);
          });
        } catch (error) {
          console.error("Lỗi khi tải phương thức thanh toán:", error);
        }
      }
    
      // Gọi hàm để tải dữ liệu
      loadPaymentMethods();
      

    // Populate table with product data
    function populateTable(products) {
        tableBody.innerHTML = ''; // Clear table
        products.forEach((product, index) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td><input type="checkbox" class="product-checkbox"></td>
                <td>${index + 1}</td>
                <td>
                    <img src="${product.image}" alt="${product.name}" class="product-image">
                    ${product.name}
                </td>
                <td class="product-price" data-price="${product.price}">${product.price.toLocaleString()} VNĐ</td>
                <td>
                    <button class="quantity-btn decrease">-</button>
                    <input type="number" value="1" min="1" class="quantity-input">
                    <button class="quantity-btn increase">+</button>
                </td>
                <td class="item-total">${product.price.toLocaleString()} VNĐ</td>
                <td><button class="remove-btn">Xóa</button></td>
                <td><button class="view-details-btn">Xem Chi Tiết</button></td>
            `;
            tableBody.appendChild(row);
        });

        initializeRowEvents();
    }
    

    // Initialize row events
    function initializeRowEvents() {
        // Quantity input events
        document.querySelectorAll('.quantity-input').forEach(input => {
            input.addEventListener('input', function () {
                // Nếu giá trị nhỏ hơn 1 hoặc không phải số, đặt lại thành 1
                if (parseInt(this.value) < 1 || isNaN(this.value) || this.value==="") {
                    this.value = 1;
                }
                updateRowTotal(this);
                updateTotal();
            });
            
        });

        // Quantity button events
        document.querySelectorAll('.quantity-btn').forEach(button => {
            button.addEventListener('click', function () {
                const input = this.parentElement.querySelector('.quantity-input');
                const currentValue = parseInt(input.value) || 0;
                input.value = this.classList.contains('increase') ? currentValue + 1 : Math.max(1, currentValue - 1);
                updateRowTotal(input);
                updateTotal();
            });
        });

        // Remove button events
        document.querySelectorAll('.remove-btn').forEach(button => {
            button.addEventListener('click', function () {
                if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
                    this.closest('tr').remove();
                    updateTotal();
                }
            });
        });

        // Product checkbox events
        document.querySelectorAll('.product-checkbox').forEach(checkbox => {
            checkbox.addEventListener('change', updateTotal);
        });
    }
    
    
    // Update the total for a single row
    function updateRowTotal(input) {
        const row = input.closest('tr');
        const quantity = parseInt(input.value) || 0;
        const price = parseFloat(row.querySelector('.product-price').dataset.price);
        const rowTotalElement = row.querySelector('.item-total');

        const rowTotal = price * quantity;
        rowTotalElement.textContent = rowTotal.toLocaleString() + ' VNĐ';
    }


    // Update total price and amount
    function updateTotal() {
        let totalPrice = 0;
        document.querySelectorAll('#product-list tr').forEach(row => {
            const checkbox = row.querySelector('.product-checkbox');
            const quantityInput = row.querySelector('.quantity-input');
            const priceElement = row.querySelector('.product-price');

            if (checkbox.checked) {
                const price = parseFloat(priceElement.dataset.price);
                const quantity = parseInt(quantityInput.value) || 0;
                totalPrice += price * quantity;
            }
        });

        totalPriceElement.textContent = totalPrice.toLocaleString() + ' VNĐ';
        totalElement.textContent = totalPrice.toLocaleString() + ' VNĐ';
    }

    // Select all checkboxes
    selectAllCB.addEventListener('change', function () {
        const checkboxes = document.querySelectorAll('.product-checkbox');
        checkboxes.forEach(checkbox => (checkbox.checked = this.checked));
        updateTotal();
    });

});
