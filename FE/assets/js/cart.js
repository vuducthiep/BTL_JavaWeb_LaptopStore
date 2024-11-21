document.addEventListener('DOMContentLoaded', () => {
    const tableBody = document.getElementById('product-list');
    const totalPriceElement = document.querySelector('.total-price');
    const totalElement = document.querySelector('.total-amount');
    const bankTransferInfo = document.getElementById('bank-transfer-info');
    const traGopInfor = document.getElementById('tra-gop-infor');
    const submitButton = document.getElementById('btn-submit');
    const selectAllCB = document.getElementById('selectAll');
    const addressSelect = document.getElementById('address-select');

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

    // Payment method listeners
    // function initializePaymentMethods() {
    //     const paymentMethods = document.querySelectorAll('input[name="pay_method"]');
    //     paymentMethods.forEach(method => {
    //         method.addEventListener('change', function () {
    //             bankTransferInfo.style.display = this.value === '2' ? 'block' : 'none';
    //             traGopInfor.style.display = this.value === '3' ? 'block' : 'none';
    //         });
    //     });
    // }

    // Submit button listener
    function handleSubmit() {
        submitButton.addEventListener('click', event => {
            event.preventDefault();

            const buyerName = document.getElementById('buyer_name').value.trim();
            const buyerTel = document.getElementById('buyer_tel').value.trim();
            const buyerAddress = document.getElementById('buyer_address').value.trim();
            const buyerEmail = document.getElementById('buyer_email').value.trim();

            if (!buyerName || !buyerTel || !buyerAddress || !buyerEmail) {
                alert('Vui lòng điền đầy đủ thông tin.');
                return;
            }

            if (!document.querySelector('input[name="pay_method"]:checked')) {
                alert('Vui lòng chọn phương thức thanh toán.');
                return;
            }

            alert('Đơn hàng đã được gửi thành công!');
        });
    }

    // Initialize everything
    function initialize() {
        fetchProducts();
        initializePaymentMethods();
        handleSubmit();
    }

    initialize();
});
