fetch('http://localhost:8080/admin/dashboard/')
  .then(response => response.json())
  .then(data => {
    console.log(data);  // Debug: Kiểm tra cấu trúc dữ liệu trả về

    // Hiển thị số sản phẩm bán ra trong tháng
    const quantitySellProductCurrentMonthElement = document.getElementById('Quantity_Sell_Product_Current_Month');
    if (quantitySellProductCurrentMonthElement && data.quantitySellProductCurrentMonth !== undefined) {
      quantitySellProductCurrentMonthElement.querySelector('h4').innerText = data.quantitySellProductCurrentMonth;
    }

    // Hiển thị tổng số khách hàng trong tháng
    const totalCustomerInCurrentMonthElement = document.getElementById('Total_Customer_In_Current_Month');
    if (totalCustomerInCurrentMonthElement && data.totalCustomerInCurrentMonth !== undefined) {
      totalCustomerInCurrentMonthElement.querySelector('h4').innerText = data.totalCustomerInCurrentMonth;
    }

    // Hiển thị tổng số khách hàng mới trong tháng
    const totalNewCustomerInCurrentMonthElement = document.getElementById('Total_New_Customer_In_Current_Month');
    if (totalNewCustomerInCurrentMonthElement && data.totalNewCustomerInCurrentMonth !== undefined) {
      totalNewCustomerInCurrentMonthElement.querySelector('h4').innerText = data.totalNewCustomerInCurrentMonth;
    }

    // Hiển thị tổng doanh thu trong tháng
    const totalAmountInCurrentMonthElement = document.getElementById('Total_Amount_InCurrent_Month');
    if (totalAmountInCurrentMonthElement && data.totalAmountInCurrentMonth !== undefined) {
      totalAmountInCurrentMonthElement.querySelector('h4').innerText = `$${data.totalAmountInCurrentMonth.toFixed(2)}`;
    }

    // Hiển thị sản phẩm bán chạy nhất trong tháng
    const topProduct = data.topPurchasedProductInMonth[0];
    if (topProduct) {
      const topPurchasedProductNameElement = document.getElementById('Top_Purchased_Product_Name');
      const topPurchasedProductImageElement = document.getElementById('Top_Purchased_Product_Image');
      const topPurchasedProductQuantityElement = document.getElementById('Top_Purchased_Product_Quantity');
      const topPurchasedProductLineTotalElement = document.getElementById('Top_Purchased_Product_LineTotal');

      if (topPurchasedProductNameElement) {
        topPurchasedProductNameElement.innerText = topProduct.productName;
      }
      if (topPurchasedProductImageElement) {
        topPurchasedProductImageElement.src = topProduct.imageURL;
      }
      if (topPurchasedProductQuantityElement) {
        topPurchasedProductQuantityElement.innerText = topProduct.quantityOrdered;
      }
      if (topPurchasedProductLineTotalElement) {
        topPurchasedProductLineTotalElement.innerText = `$${topProduct.lineTotal.toFixed(2)}`;
      }
    }

    // Dữ liệu biểu đồ khách hàng mới
    const newCustomersData = {
      labels: data.newCustomerPerMonthMap.map(item => `Month ${item.month}`),
      values: data.newCustomerPerMonthMap.map(item => item.customerCount)
    };

    const ctx1 = document.getElementById('chart-bars').getContext('2d');
    new Chart(ctx1, {
      type: 'line',
      data: {
        labels: newCustomersData.labels,
        datasets: [{
          label: 'New Customers',
          data: newCustomersData.values,
          backgroundColor: 'red',  // Màu nền (light green)
            borderColor: 'red',  // Màu viền (dark green
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { display: true }
        }
      }
    });

    // Dữ liệu biểu đồ doanh thu
    const revenueData = {
      labels: data.totalAmountPerMonthMap.map(item => `Month ${item.month}`),
      values: data.totalAmountPerMonthMap.map(item => item.totalAmount)
    };

    const ctx2 = document.getElementById('chart-line').getContext('2d');
    new Chart(ctx2, {
      type: 'line',
      data: {
        labels: revenueData.labels,
        datasets: [{
          label: 'Revenue',
          data: revenueData.values,
          borderColor: 'rgba(76, 175, 80, 1)',
          backgroundColor: 'rgba(76, 175, 80, 0.2)',
          fill: true
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { display: true }
        }
      }
    });

    // Dữ liệu biểu đồ sản phẩm bán
    const productSalesData = {
      labels: data.totalQuantitySellProductPerMonthMap.map(item => `Month ${item.month}`),
      values: data.totalQuantitySellProductPerMonthMap.map(item => item.totalSold)
    };

    const ctx3 = document.getElementById('chart-line-tasks').getContext('2d');
    new Chart(ctx3, {
      type: 'line',
      data: {
        labels: productSalesData.labels,
        datasets: [{
          label: 'Products Sold',
          data: productSalesData.values,
          borderColor: 'rgba(33, 150, 243, 1)',
          backgroundColor: 'rgba(33, 150, 243, 0.2)',
          fill: true
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { display: true }
        }
      }
    });

    // Dữ liệu sản phẩm bán chạy nhất
    const topPurchasedProductsData = data.topPurchasedProductInMonth.map(product => ({
      label: product.productName,
      data: [product.quantityOrdered],
      backgroundColor: 'rgba(255, 99, 132, 0.6)',
      borderColor: 'rgba(255, 99, 132, 1)',
      borderWidth: 1
    }));

    const ctx4 = document.getElementById('top-purchased-products').getContext('2d');
    new Chart(ctx4, {
      type: 'bar',
      data: {
        labels: data.topPurchasedProductInMonth.map(product => product.productName),
        datasets: topPurchasedProductsData
      },
      options: {
        responsive: true,
        plugins: {
          legend: { display: true }
        }
      }
    });

    // Cập nhật bảng Top Sản phẩm
    const topProductsTable = document.getElementById('top-products-table');
    data.topPurchasedProductInMonth.forEach(product => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-10">
          ${product.productName}
        </td>
        <td class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-10">
          ${product.quantityOrdered}
        </td>
      `;
      topProductsTable.appendChild(row);
    });

    // Cập nhật bảng Top Khách Hàng
    const topCustomersTable = document.getElementById('top-customers-table');
    data.topCustomersInMonth.forEach(customer => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-10">
          ${customer.customerName}
        </td>
        <td class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-10">
          $${customer.totalAmount.toFixed(2)}
        </td>
      `;
      topCustomersTable.appendChild(row);
    });
  })
  .catch(error => {
    console.error('Error fetching data:', error);
    alert('Không thể tải dữ liệu từ API');
  });
