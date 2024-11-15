// API URL để lấy dữ liệu
const apiUrl = 'http://localhost:8080/admin/customer/';

// Lấy các phần tử DOM cần thiết
const customerListDiv = document.getElementById('customer-list');
const top10CustomersList = document.getElementById('top-10-customers-list');
const monthlyNewCustomersChart = document.getElementById('monthly-new-customers-chart');

document.getElementById("add-customer-btn").addEventListener("click", function () {
  var addCustomerModal = new bootstrap.Modal(document.getElementById("addCustomerModal"));
  addCustomerModal.show();
});

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
    displayTop10Customers(data.findTopCustomerInMonth);

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
        <td>
          <!-- Nút Sửa -->
          <button class="btn btn-primary btn-sm" onclick="editCustomer(${customer.customerID})">Sửa</button>
          <!-- Nút Xóa -->
          <button class="btn btn-danger btn-sm" onclick="deleteCustomer(${customer.customerID})">Xóa</button>
        </td>
      </tr>
    `;
  });
  customerListDiv.innerHTML = customerHTML;
}

// Hàm xóa khách hàng
async function deleteCustomer(customerId) {
  if (confirm('Bạn có chắc chắn muốn xóa khách hàng này?')) {
    try {
      const response = await fetch(`http://localhost:8080/admin/customer/${customerId}`, {
        method: 'DELETE', // Phương thức DELETE để xóa khách hàng
      });

      // Kiểm tra nếu phản hồi hợp lệ
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      // Sau khi xóa thành công, gọi lại fetchCustomerData để làm mới danh sách
      fetchCustomerData();
      alert('Khách hàng đã được xóa thành công!');
    } catch (error) {
      console.error('Error deleting customer:', error);
      alert('Có lỗi xảy ra khi xóa khách hàng.');
    }
  }
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

// Lấy phần tử DOM của form và nút Lưu
const customerForm = document.getElementById('customerForm');
const saveCustomerBtn = document.getElementById('save-customer-btn');

// Thêm sự kiện submit cho form
customerForm.addEventListener('submit', async function (event) {
  event.preventDefault(); // Ngăn chặn hành vi mặc định (reload trang)

  // Lấy dữ liệu từ form
  const newCustomer = {
    totalAmount: parseFloat(document.getElementById('totalAmount').value),
    userID: parseInt(document.getElementById('userID').value),
    customerID: parseInt(document.getElementById('customerID').value),
    addressID: parseInt(document.getElementById('addressID').value),
    fullName: document.getElementById('fullName').value,
    email: document.getElementById('email').value,
    passWord: document.getElementById('passWord').value,
    phoneNumber: document.getElementById('phoneNumber').value,
    registrationDate: document.getElementById('registrationDate').value,
    address: document.getElementById('address').value,
    city: document.getElementById('city').value,
    district: document.getElementById('district').value,
    ward: document.getElementById('ward').value,
    streetAddress: document.getElementById('streetAddress').value
  };

  try {
    // Gửi yêu cầu POST tới API
    const response = await fetch('http://localhost:8080/admin/customer/create-customer/', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newCustomer)
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    // Nếu tạo khách hàng thành công, làm mới danh sách và ẩn modal
    alert('Khách hàng đã được thêm thành công!');
    fetchCustomerData(); // Làm mới danh sách khách hàng
    const addCustomerModal = bootstrap.Modal.getInstance(document.getElementById('addCustomerModal'));
    addCustomerModal.hide(); // Đóng modal

    // Reset form sau khi thêm thành công
    customerForm.reset();
  } catch (error) {
    console.error('Error creating customer:', error);
    alert('Có lỗi xảy ra khi thêm khách hàng.');
  }
});
