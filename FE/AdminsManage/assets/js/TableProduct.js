// API URL để lấy dữ liệu sản phẩm
const productApiUrl = 'http://localhost:8080/admin/product/';

// Lấy các phần tử DOM cần thiết
const productListDiv = document.getElementById('product-list');
const top10ProductsList = document.getElementById('top-10-products-list');
const monthlyNewProductsChart = document.getElementById('monthly-new-products-chart');

// Hàm để lấy dữ liệu từ API
async function fetchProductData() {
  try {
    const response = await fetch(productApiUrl);
    
    // Kiểm tra nếu phản hồi hợp lệ
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    // Lấy dữ liệu JSON từ phản hồi
    const data = await response.json();

    // Hiển thị danh sách sản phẩm
    displayProductList(data.listProductDetail);

    // Hiển thị top 10 sản phẩm bán chạy nhất
    displayTop10Products(data.listTopProductSell);

    // Hiển thị biểu đồ doanh thu sản phẩm theo tháng
    displayMonthlyProductChart(data.quantityProductForChart);

  } catch (error) {
    console.error('Error fetching product data:', error);
  }
}

// Hàm hiển thị danh sách sản phẩm
function displayProductList(products) {
  let productHTML = '';
  products.forEach((product, index) => {
    productHTML += `
      <tr>
        <td>${index + 1}</td>
        <td>${product.productName}</td>
        <td>${product.productBrand}</td>
        <td>${product.price.toLocaleString()} USD</td>
      </tr>
    `;
  });
  productListDiv.innerHTML = productHTML;
}

// Hàm hiển thị top 10 sản phẩm bán chạy nhất
function displayTop10Products(products) {
  // Sắp xếp sản phẩm theo số lượng bán giảm dần
  const top10 = products.sort((a, b) => b.quantityOrdered - a.quantityOrdered).slice(0, 10);
  
  let top10HTML = '';
  top10.forEach(product => {
    top10HTML += `
      <li class="list-group-item">
        ${product.productName} - Số lượng bán: ${product.quantityOrdered}
      </li>
    `;
  });
  top10ProductsList.innerHTML = top10HTML;
}

// Hàm hiển thị biểu đồ doanh thu sản phẩm theo tháng
function displayMonthlyProductChart(monthlyData) {
  const months = monthlyData.map(data => `Tháng ${data.month}`);
  const salesData = monthlyData.map(data => data.totalSold);

  const chartData = {
    labels: months,
    datasets: [{
      label: 'Doanh thu sản phẩm theo tháng',
      data: salesData,
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
  new Chart(monthlyNewProductsChart, {
    type: 'line',
    data: chartData,
    options: chartOptions
  });
}

// Gọi hàm fetchProductData khi trang được tải
fetchProductData();
