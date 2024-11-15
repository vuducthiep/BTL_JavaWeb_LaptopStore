// API URL để lấy dữ liệu
const apiUrl = 'http://localhost:8080/admin/customer/';

// Lấy các phần tử DOM cần thiết
const customerListDiv = document.getElementById('customer-list');
const top10CustomersList = document.getElementById('top-10-customers-list');
const monthlyNewCustomersChart = document.getElementById('monthly-new-customers-chart');

// Hàm để lấy dữ liệu từ API
async function fetchCustomerData() {
  try {
    const response = await fetch(apiUrl);
    
    // Kiểm tra nếu phản hồi hợp lệ
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    // Lấy dữ liệu JSON từ phản hồi
    const data = await response.json();

    // Hiển thị danh sách khách hàng
    displayCustomerList(data.listCustomer);

    // Hiển thị top 10 khách hàng chi tiêu nhiều nhất
    displayTop10Customers(data.listCustomer);

    // Hiển thị biểu đồ khách hàng mới theo tháng
    displayMonthlyCustomerChart(data.countNewCustomerPerMonth);

  } catch (error) {
    console.error('Error fetching data:', error);
  }
}

// Hàm hiển thị danh sách khách hàng
function displayCustomerList(customers) {
  let customerHTML = '';
  customers.forEach((customer, index) => {
    customerHTML += `
      <tr>
        <td>${index + 1}</td>
        <td>${customer.fullName}</td>
        <td>${customer.email}</td>
        <td>${customer.totalAmount}</td>
      </tr>
    `;
  });
  customerListDiv.innerHTML = customerHTML;
}

// Hàm hiển thị top 10 khách hàng chi tiêu nhiều nhất
function displayTop10Customers(customers) {
  // Sắp xếp khách hàng theo tổng chi tiêu giảm dần
  const top10 = customers.sort((a, b) => b.totalAmount - a.totalAmount).slice(0, 10);
  
  let top10HTML = '';
  top10.forEach(customer => {
    top10HTML += `
      <li class="list-group-item">
        ${customer.fullName} - ${customer.totalAmount}
      </li>
    `;
  });
  top10CustomersList.innerHTML = top10HTML;
}

// Hàm hiển thị biểu đồ khách hàng mới theo tháng
function displayMonthlyCustomerChart(monthlyCounts) {
  const months = monthlyCounts.map(count => count.month);
  const customerCounts = monthlyCounts.map(count => count.customerCount);

  const chartData = {
    labels: months,
    datasets: [{
      label: 'New Customers Per Month',
      data: customerCounts,
      borderColor: 'rgb(75, 192, 192)',
      backgroundColor: 'rgba(75, 192, 192, 0.2)',
      fill: true,
    }]
  };

  const chartOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      tooltip: {
        callbacks: {
          label: function (context) {
            return `${context.dataset.label}: ${context.raw}`;
          }
        }
      }
    }
  };

  // Vẽ biểu đồ sử dụng Chart.js
  new Chart(monthlyNewCustomersChart, {
    type: 'line',
    data: chartData,
    options: chartOptions
  });
}

// Gọi hàm fetchCustomerData khi trang được tải
fetchCustomerData();
