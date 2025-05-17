async function loadDashboardData() {
  try {
    const response = await fetch("http://localhost:8080/admin/dashboard/");
    const data = await response.json();

    // Cập nhật số liệu tổng quan
    document.getElementById("quantity-sell-product-current-month").innerText =
      data.quantitySellProductCurrentMonth;
    document.getElementById("total-customer-in-current-month").innerText =
      data.totalCustomerInCurrentMonth;
    document.getElementById("total-new-customer-in-current-month").innerText =
      data.totalNewCustomerInCurrentMonth;
    document.getElementById("total-amount-in-current-month").innerText =
      `${data.totalAmountInCurrentMonth.toLocaleString()} VND`;

    // === BIỂU ĐỒ KHÁCH HÀNG MỚI ===
    const newCustomerLabels = data.newCustomerPerMonthMap.map(item => `Tháng ${item.month}`);
    const newCustomerData = data.newCustomerPerMonthMap.map(item => item.customerCount);
    // Font config
    Chart.defaults.font.family = "Inter";
    Chart.defaults.font.size = 14;
    

// === BIỂU ĐỒ KHÁCH HÀNG MỚI ===
const ctx1 = document.getElementById("new-customers-chart").getContext("2d");
new Chart(ctx1, {
  type: "line",
  data: {
    labels: data.newCustomerPerMonthMap.map(item => `Tháng ${item.month}`),
    datasets: [
      {
        label: "Khách hàng mới",
        data: data.newCustomerPerMonthMap.map(item => item.customerCount),
        borderColor: "#36A2EB",
        backgroundColor: "rgba(54, 162, 235, 0.2)",
        pointBackgroundColor: "#007BFF",
        fill: true,
        tension: 0.4,
        borderWidth: 2,
      },
    ],
  },
  options: {
    responsive: true,
    animation: {
      duration: 1000,
      easing: "easeOutBounce",
    },
    plugins: {
      legend: { position: "top" },
      title: {
        display: true,
        text: "Khách hàng mới theo tháng",
        font: { size: 18, weight: "bold" },
        padding: { top: 10, bottom: 20 },
      },
      tooltip: {
        backgroundColor: "#ffffff",
        titleColor: "#333",
        bodyColor: "#333",
        borderColor: "#ccc",
        borderWidth: 1,
        callbacks: {
          label: (item) => `${item.raw} khách hàng`,
        },
      },
    },
    scales: {
      x: {
        title: { display: true, text: "Tháng", font: { size: 14 } },
        ticks: { color: "#333" },
      },
      y: {
        beginAtZero: true,
        title: { display: true, text: "Số lượng", font: { size: 14 } },
        ticks: { color: "#333" },
      },
    },
  },
});

// === BIỂU ĐỒ DOANH THU ===
const ctx2 = document.getElementById("revenue-chart").getContext("2d");
new Chart(ctx2, {
  type: "line",
  data: {
    labels: data.totalAmountPerMonthMap.map(item => `Tháng ${item.month}`),
    datasets: [
      {
        label: "Doanh thu (VND)",
        data: data.totalAmountPerMonthMap.map(item => item.totalAmount),
        borderColor: "#FF6384",
        backgroundColor: "rgba(255, 99, 132, 0.2)",
        pointBackgroundColor: "#FF4D6D",
        fill: true,
        tension: 0.4,
        borderWidth: 2,
      },
    ],
  },
  options: {
    responsive: true,
    animation: {
      duration: 1000,
      easing: "easeOutBounce",
    },
    plugins: {
      legend: { position: "top" },
      title: {
        display: true,
        text: "Doanh thu theo tháng",
        font: { size: 18, weight: "bold" },
        padding: { top: 10, bottom: 20 },
      },
      tooltip: {
        backgroundColor: "#ffffff",
        titleColor: "#333",
        bodyColor: "#333",
        borderColor: "#ccc",
        borderWidth: 1,
        callbacks: {
          label: (item) => `${item.raw.toLocaleString()} VND`,
        },
      },
    },
    scales: {
      x: {
        title: { display: true, text: "Tháng", font: { size: 14 } },
        ticks: { color: "#333" },
      },
      y: {
        beginAtZero: true,
        title: { display: true, text: "", font: { size: 14 } },
        ticks: { color: "#333" },
      },
    },
  },
});

// === BIỂU ĐỒ SẢN PHẨM BÁN ===
const ctx3 = document.getElementById("products-sold-chart").getContext("2d");
new Chart(ctx3, {
  type: "line",
  data: {
    labels: data.totalQuantitySellProductPerMonthMap.map(item => `Tháng ${item.month}`),
    datasets: [
      {
        label: "Sản phẩm bán",
        data: data.totalQuantitySellProductPerMonthMap.map(item => item.totalSold),
        borderColor: "#9966FF",
        backgroundColor: "rgba(153, 102, 255, 0.2)",
        pointBackgroundColor: "#7E57C2",
        fill: true,
        tension: 0.4,
        borderWidth: 2,
      },
    ],
  },
  options: {
    responsive: true,
    animation: {
      duration: 1000,
      easing: "easeOutBounce",
    },
    plugins: {
      legend: { position: "top" },
      title: {
        display: true,
        text: "Số lượng sản phẩm bán theo tháng",
        font: { size: 18, weight: "bold" },
        padding: { top: 10, bottom: 20 },
      },
      tooltip: {
        backgroundColor: "#ffffff",
        titleColor: "#333",
        bodyColor: "#333",
        borderColor: "#ccc",
        borderWidth: 1,
        callbacks: {
          label: (item) => `${item.raw} sản phẩm`,
        },
      },
    },
    scales: {
      x: {
        title: { display: true, text: "Tháng", font: { size: 14 } },
        ticks: { color: "#333" },
      },
      y: {
        beginAtZero: true,
        title: { display: true, text: "Số lượng sản phẩm", font: { size: 14 } },
        ticks: { color: "#333" },
      },
    },
  },
});


    // Danh sách sản phẩm bán chạy
    const TopProductsList = document.getElementById("top-products-list");
    TopProductsList.innerHTML = "";
    data.topPurchasedProductInMonth.forEach((product) => {
      const li = document.createElement("li");
      li.classList.add("list-group-item");
      li.innerHTML = `
        <div><strong>${product.productName}</strong></div>
        <div><img src="${product.imageUrl}" alt="${product.productName}" style="width: 100px; height: auto;"></div>
        <div>Giá: ${product.price.toLocaleString()} VND</div>
      `;
      TopProductsList.appendChild(li);
    });

    // Danh sách khách hàng tốt nhất
    const topCustomersList = document.getElementById("top-customers-list");
    topCustomersList.innerHTML = "";
    data.topCustomerInMonth.forEach((customer) => {
      const li = document.createElement("li");
      li.classList.add("list-group-item");
      li.innerHTML = `
        <div><strong>${customer.fullName}</strong></div>
        <div>Email: ${customer.email}</div>
        <div>Số điện thoại: ${customer.phoneNumber}</div>
        <div>Địa chỉ: ${customer.streetAddress}, ${customer.ward}, ${customer.district}, ${customer.city}, ${customer.address}</div>
        <div>Tổng chi tiêu: ${customer.totalAmount.toLocaleString()} VND</div>
      `;
      topCustomersList.appendChild(li);
    });

  } catch (error) {
    console.error("Lỗi khi tải dữ liệu từ API:", error);
  }
}

document.addEventListener("DOMContentLoaded", loadDashboardData);
