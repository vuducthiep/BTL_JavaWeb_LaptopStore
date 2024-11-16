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
    console.log('Fetched Product Data:', data); // Kiểm tra dữ liệu API

    // Gọi các hàm hiển thị dữ liệu
    displayProductList(data.listProductDetail);
    displayTop10Products(data.listTopProductSell);
    displayMonthlyProductChart(data.quantityProductForChart);
  } catch (error) {
    console.error('Error fetching product data:', error);
  }
}

// Hàm hiển thị danh sách sản phẩm
function displayProductList(products) {
  const productHTML = products.map((product, index) => {
    console.log('Product ID:', product.id); // Kiểm tra ID sản phẩm
    return `
      <tr>
        <td>${index + 1}</td>
        <td>${product.productName}</td>
        <td>${product.productBrand}</td>
        <td>${product.price.toLocaleString()} USD</td>
        <td>
          <button class="btn btn-primary btn-sm" onclick="editProduct('${product.productId}')">Sửa</button>
          <button class="btn btn-danger btn-sm" onclick="deleteProduct('${product.productId}')">Xóa</button>
        </td>
      </tr>
    `;
  }).join('');
  
  productListDiv.innerHTML = productHTML;
}

// Hàm để sửa sản phẩm (nơi bạn xử lý chỉnh sửa sản phẩm)
function editProduct(productId) {
  console.log('Edit Product ID:', productId); // Kiểm tra ID khi nhấn "Sửa"
  // Tiến hành xử lý chỉnh sửa sản phẩm ở đây với productId
}

// Hàm hiển thị top 10 sản phẩm bán chạy nhất
function displayTop10Products(products) {
  // Lấy danh sách top 10 sản phẩm dựa trên số lượng bán
  const top10 = products.sort((a, b) => b.quantityOrdered - a.quantityOrdered).slice(0, 10);

  // Tạo HTML cho bảng
  const top10HTML = `
    <table class="table table-striped">
      <thead>
        <tr>
          <th>#</th>
          <th>Tên sản phẩm</th>
          <th>Số lượng bán</th>
        </tr>
      </thead>
      <tbody>
        ${top10.map((product, index) => `
          <tr>
            <td>${index + 1}</td>
            <td>${product.productName}</td>
            <td>${product.quantityOrdered}</td>
          </tr>
        `).join('')}
      </tbody>
    </table>
  `;

  // Hiển thị bảng trong phần tử HTML
  document.getElementById("top-10-products-list").innerHTML = top10HTML;
}


// Hàm hiển thị biểu đồ doanh thu sản phẩm theo tháng
function displayMonthlyProductChart(monthlyData) {
  const months = monthlyData.map(data => `Tháng ${data.month}`);
  const salesData = monthlyData.map(data => data.totalSold);

  const chartData = {
    labels: months,
    datasets: [{
      label: 'Sản phẩm bán theo tháng',
      data: salesData,
      borderColor: 'rgb(75, 192, 192)',
      backgroundColor: 'rgba(75, 192, 192, 0.2)',
      fill: true,
    }]
  };

  const chartOptions = {
    responsive: true,
    plugins: {
      legend: { position: 'top' },
      tooltip: {
        callbacks: {
          label: function (context) {
            return `${context.dataset.label}: ${context.raw}`;
          }
        }
      }
    }
  };

  new Chart(monthlyNewProductsChart, {
    type: 'line',
    data: chartData,
    options: chartOptions
  });
}

// Gọi hàm fetchProductData khi trang được tải
fetchProductData();
