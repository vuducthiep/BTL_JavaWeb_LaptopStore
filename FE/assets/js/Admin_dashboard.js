document.addEventListener("DOMContentLoaded", function () {
  // Lấy các phần tử HTML theo ID
  const Products_sold_in_the_month = document.getElementById(
    "Products_sold_in_the_month"
  );
  const Total_Customer_In_Current_Month = document.getElementById(
    "Total_Customer_In_Current_Month"
  );
  const Total_New_Customer_In_Current_Month = document.getElementById(
    "Total_New_Customer_In_Current_Month"
  );
  const Total_Amount_InCurrent_Month = document.getElementById(
    "Total_Amount_InCurrent_Month"
  );

  // Giả sử các giá trị sau sẽ được lấy từ API hoặc dữ liệu backend
  let productsSoldInTheMonth = 1500; // Ví dụ giá trị thay thế
  let totalCustomerInCurrentMonth = 2000;
  let totalNewCustomerInCurrentMonth = 250;
  let totalAmountInCurrentMonth = 20000000; // Đơn vị là VND

  // Gọi API để load sản phẩm khi trang load
  fetch("http://localhost:3000/dashboard")
    .then((response) => response.json())
    .then(data => {
        // Dữ liệu được trả về từ API (ví dụ: data = List<Customer_CountNewCustomerPerMonthDTO>)
        const labelsDA = data.map(item => item.month);  // Lấy các tháng (M, T, W, ...)
        const newCustomerData = data.map(item => item.newCustomers);  // Lấy số lượng khách hàng mới trong tháng ?????
        //const labels2 = data.map(item => item.month);  // Lấy các tháng (M, T, W, ...)
        const totalAmountData = data.map(item => item.newCustomers);  // Lấy danh thu trong tháng ????
        //const labels3 = data.map(item => item.month);  // Lấy các tháng (M, T, W, ...)
        const totalQuantitySellProductData = data.map(item => item.newCustomers);  // Lấy số lượng san pham trong tháng ????
        // Tạo biểu đồ
        var ctx = document.getElementById("chart-bars").getContext("2d");

        new Chart(ctx, {
            type: "bar",
            data: {
                labels: labelsDA,  // Sử dụng labels lấy từ dữ liệu
                datasets: [{
                    label: "New Customers",
                    tension: 0.4,
                    borderWidth: 0,
                    borderRadius: 4,
                    borderSkipped: false,
                    backgroundColor: "rgba(255, 255, 255, .8)",
                    data: newCustomerData,  // Dữ liệu số lượng khách hàng mới trong tháng
                    maxBarThickness: 6,
                }],
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false,
                    },
                },
                interaction: {
                    intersect: false,
                    mode: "index",
                },
                scales: {
                    y: {
                        grid: {
                            drawBorder: false,
                            display: true,
                            drawOnChartArea: true,
                            drawTicks: false,
                            borderDash: [5, 5],
                            color: "rgba(255, 255, 255, .2)",
                        },
                        ticks: {
                            suggestedMin: 0,
                            suggestedMax: 500,  // Tùy chỉnh giá trị tối đa
                            beginAtZero: true,
                            padding: 10,
                            font: {
                                size: 14,
                                weight: 300,
                                family: "Roboto",
                                style: "normal",
                                lineHeight: 2,
                            },
                            color: "#fff",
                        },
                    },
                    x: {
                        grid: {
                            drawBorder: false,
                            display: true,
                            drawOnChartArea: true,
                            drawTicks: false,
                            borderDash: [5, 5],
                            color: "rgba(255, 255, 255, .2)",
                        },
                        ticks: {
                            display: true,
                            color: "#f8f9fa",
                            padding: 10,
                            font: {
                                size: 14,
                                weight: 300,
                                family: "Roboto",
                                style: "normal",
                                lineHeight: 2,
                            },
                        },
                    },
                },
            },
        });
        
        var ctx2 = document.getElementById("chart-line").getContext("2d");

        new Chart(ctx2, {
            type: "line",
            data: {
                labels: labelsDA,
                datasets: [{
                    label: "Mobile apps",
                    tension: 0,
                    borderWidth: 0,
                    pointRadius: 5,
                    pointBackgroundColor: "rgba(255, 255, 255, .8)",
                    pointBorderColor: "transparent",
                    borderColor: "rgba(255, 255, 255, .8)",
                    borderColor: "rgba(255, 255, 255, .8)",
                    borderWidth: 4,
                    backgroundColor: "transparent",
                    fill: true,
                    data: totalAmountData,
                    maxBarThickness: 6,
                }, ],
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false,
                    },
                },
                interaction: {
                    intersect: false,
                    mode: "index",
                },
                scales: {
                    y: {
                        grid: {
                            drawBorder: false,
                            display: true,
                            drawOnChartArea: true,
                            drawTicks: false,
                            borderDash: [5, 5],
                            color: "rgba(255, 255, 255, .2)",
                        },
                        ticks: {
                            display: true,
                            color: "#f8f9fa",
                            padding: 10,
                            font: {
                                size: 14,
                                weight: 300,
                                family: "Roboto",
                                style: "normal",
                                lineHeight: 2,
                            },
                        },
                    },
                    x: {
                        grid: {
                            drawBorder: false,
                            display: false,
                            drawOnChartArea: false,
                            drawTicks: false,
                            borderDash: [5, 5],
                        },
                        ticks: {
                            display: true,
                            color: "#f8f9fa",
                            padding: 10,
                            font: {
                                size: 14,
                                weight: 300,
                                family: "Roboto",
                                style: "normal",
                                lineHeight: 2,
                            },
                        },
                    },
                },
            },
        });

        var ctx3 = document.getElementById("chart-line-tasks").getContext("2d");

        new Chart(ctx3, {
            type: "line",
            data: {
                labels: labelsDA,
                datasets: [{
                    label: "Mobile apps",
                    tension: 0,
                    borderWidth: 0,
                    pointRadius: 5,
                    pointBackgroundColor: "rgba(255, 255, 255, .8)",
                    pointBorderColor: "transparent",
                    borderColor: "rgba(255, 255, 255, .8)",
                    borderWidth: 4,
                    backgroundColor: "transparent",
                    fill: true,
                    data: totalQuantitySellProductData,
                    maxBarThickness: 6,
                }, ],
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false,
                    },
                },
                interaction: {
                    intersect: false,
                    mode: "index",
                },
                scales: {
                    y: {
                        grid: {
                            drawBorder: false,
                            display: true,
                            drawOnChartArea: true,
                            drawTicks: false,
                            borderDash: [5, 5],
                            color: "rgba(255, 255, 255, .2)",
                        },
                        ticks: {
                            display: true,
                            padding: 10,
                            color: "#f8f9fa",
                            font: {
                                size: 14,
                                weight: 300,
                                family: "Roboto",
                                style: "normal",
                                lineHeight: 2,
                            },
                        },
                    },
                    x: {
                        grid: {
                            drawBorder: false,
                            display: false,
                            drawOnChartArea: false,
                            drawTicks: false,
                            borderDash: [5, 5],
                        },
                        ticks: {
                            display: true,
                            color: "#f8f9fa",
                            padding: 10,
                            font: {
                                size: 14,
                                weight: 300,
                                family: "Roboto",
                                style: "normal",
                                lineHeight: 2,
                            },
                        },
                    },
                },
            },
        });
    })
    .catch(error => console.error('Error fetching customer data:', error));

  // Cập nhật nội dung của các phần tử
  Products_sold_in_the_month.querySelector("h4").textContent =
    productsSoldInTheMonth.toLocaleString("vi-VN");
  Total_Customer_In_Current_Month.querySelector("h4").textContent =
    totalCustomerInCurrentMonth.toLocaleString("vi-VN");
  Total_New_Customer_In_Current_Month.querySelector("h4").textContent =
    totalNewCustomerInCurrentMonth.toLocaleString("vi-VN");
  Total_Amount_InCurrent_Month.querySelector("h4").textContent =
    totalAmountInCurrentMonth.toLocaleString("vi-VN");


});
