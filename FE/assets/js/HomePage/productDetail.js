document.addEventListener('DOMContentLoaded', function () {
    // Lấy ProductID từ URL
    const params = new URLSearchParams(window.location.search);
    const productId = params.get('id'); // Lấy giá trị của tham số id từ URL
    const apiUrl = `http://localhost:8080/user/product?id=${productId}`;
    if (productId) {
      // Gọi API để lấy tất cả sản phẩm
      fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
          // Tìm sản phẩm có ProductID khớp với giá trị từ URL
          const product = data[0];
          console.log('thông tin sản phẩm ', product);
          displayProductDetails(product);
          displayProductDescriptions(product)
        })
        .catch(error => console.error('Error fetching product details:', error));
    }
  }); // Đóng hàm addEventListener
  

function displayProductDetails(product) {
    document.getElementById('product-name').textContent = product.productName;
    document.getElementById('product-name-breadcrumb').textContent = product.productName; // Hiển thị tên trong breadcrumb
    document.getElementById('product-image').src = product.imageUrl;
    document.getElementById('product-status').textContent = "Tình Trạng: Còn hàng"; // Sửa lại id ở đây
    document.getElementById('product-price').textContent = `Giá: ${(product.price *1000000).toLocaleString('vi-VN')} VND`;
  }

function displayProductDescriptions(productDescription) {
    const tableBody = document.querySelector('#highlight-specs tbody');
    tableBody.innerHTML = '';

  // Thêm các thông số vào bảng
  tableBody.innerHTML += `
    <tr>
      <td>Công nghệ CPU</td>
      <td>${productDescription.cpuCompany}</td>
    </tr>
    <tr>
      <td>Dung lượng RAM</td>
      <td>${productDescription.ramCapacity} GB</td>
    </tr>
    <tr>
      <td>Card đồ họa</td>
      <td>${productDescription.vgaFullName}</td>
    </tr>
    <tr>
      <td>Kích thước màn hình</td>
      <td>${productDescription.screenSize} inch</td>
    </tr>
  `;
    const specsTable = document.getElementById('all-specs');
            specsTable.innerHTML = ''; // Xóa nội dung cũ

            // Tạo các hàng trong bảng với thông tin sản phẩm
            specsTable.innerHTML += `
                <tr class="section-title" data-toggle="cpu">
                    <td colspan="2" style="font-weight: bold; cursor: pointer;">
                        Bộ xử lý <span class="arrow" data-direction="down">▼</span>
                    </td>
                </tr>
                <tr class="cpu hidden">
                    <td>Hãng CPU</td>
                    <td>${productDescription.cpuCompany}</td>
                </tr>
                <tr class="cpu hidden">
                    <td>Công nghệ CPU</td>
                    <td>${productDescription.cpuTechnology}</td>
                </tr>
                <tr class="cpu hidden">
                    <td>Loại CPU</td>
                    <td>${productDescription.cpuType}</td>
                </tr>
                <tr class="cpu hidden">
                    <td>Tốc độ CPU (tối thiểu)</td>
                    <td>${productDescription.minimumCPUspeed} GHz</td>
                </tr>
                <tr class="cpu hidden">
                    <td>Tốc độ CPU (tối đa)</td>
                    <td>${productDescription.maximunSpeed} GHz</td>
                </tr>
                <tr class="cpu hidden">
                    <td>Nhân CPU</td>
                    <td>${productDescription.multiplier} GHz</td>
                </tr>
                 <tr class="cpu hidden">
                    <td>Bộ nhớ đệm</td>
                    <td>${productDescription.processorCache} GHz</td>
                </tr>

                 <!-- Mục Đồ họa -->
                <tr class="section-title" data-toggle="graphics">
                    <td colspan="2" style="font-weight: bold; cursor: pointer;">
                        Đồ họa <span class="arrow" data-direction="down">▼</span>
                    </td>
                </tr> 
                <tr class="graphics hidden">
                    <td>Loại RAM</td>
                    <td>${productDescription.RAMType}</td>
                </tr>
                <tr class="graphics hidden">
                    <td>Tốc độ RAM</td>
                    <td>${productDescription.RAMspeed}</td>
                </tr>
                <tr class="graphics hidden">
                    <td>Dung lượng RAM</td>
                    <td>${productDescription.RAMcapacity} GB</td>
                </tr>

                <!-- Mục RAM -->
                <tr class="section-title" data-toggle="ram">
                    <td colspan="2" style="font-weight: bold; cursor: pointer;">
                        RAM <span class="arrow" data-direction="down">▼</span>
                    </td>
                </tr>
                <tr class="ram hidden">
                    <td>Loại RAM</td>
                    <td>${productDescription.RAMType}</td>
                </tr>
                <tr class="ram hidden">
                    <td>Tốc độ RAM</td>
                    <td>${productDescription.RAMspeed}</td>
                </tr>
                <tr class="ram hidden">
                    <td>Dung lượng RAM</td>
                    <td>${productDescription.RAMcapacity} GB</td>
                </tr>

                <!-- Mục Lưu trữ -->
                <tr class="section-title" data-toggle="storage">
                    <td colspan="2" style="font-weight: bold; cursor: pointer;">
                        Lưu trữ <span class="arrow" data-direction="down">▼</span>
                    </td>
                </tr>
                <tr class="storage hidden">
                    <td>Loại ổ cứng</td>
                    <td>SSD</td>
                </tr>
                <tr class="storage hidden">
                    <td>Dung lượng ổ cứng</td>
                    <td>${productDescription.Capacity} GB</td>
                </tr>
                <tr class="storage hidden">
                    <td>Số khe ổ cứng SSD/HDD</td>
                    <td>${productDescription.TotalSSDHDDSlots}</td>
                </tr>
                <tr class="storage hidden">
                    <td>Số khe còn lại</td>
                    <td>${productDescription.NumberOfSSDHDDSlotsRemaining}</td>
                </tr>

                <!-- Mục Màn hình -->
                <tr class="section-title" data-toggle="screen">
                    <td colspan="2" style="font-weight: bold; cursor: pointer;">
                        Màn hình <span class="arrow" data-direction="down">▼</span>
                    </td>
                </tr>
                <tr class="screen hidden">
                    <td>Kích thước màn hình</td>
                    <td>${productDescription.ScreenSize} inch</td>
                </tr>
                <tr class="screen hidden">
                    <td>Công nghệ hiển thị</td>
                    <td>${productDescription.DisplayTechnology}</td>
                </tr>
                <tr class="screen hidden">
                    <td>Độ phân giải</td>
                    <td>${productDescription.Resolution}</td>
                </tr>

                <!-- Mục Giao tiếp và kết nối -->
                <tr class="section-title" data-toggle="connectivity">
                    <td colspan="2" style="font-weight: bold; cursor: pointer;">
                        Giao tiếp và kết nối <span class="arrow" data-direction="down">▼</span>
                    </td>
                </tr>
                <tr class="connectivity hidden">
                    <td>Cổng giao tiếp</td>
                    <td>${productDescription.CommunicationPort}</td>
                </tr>
                <tr class="connectivity hidden">
                    <td>Wi-Fi</td>
                    <td>${productDescription.Wifi}</td>
                </tr>
                <tr class="connectivity hidden">
                    <td>Bluetooth</td>
                    <td>${productDescription.Bluetooth}</td>
                </tr>

                <!-- Mục Hệ điều hành -->
                <tr class="section-title" data-toggle="os">
                    <td colspan="2" style="font-weight: bold; cursor: pointer;">
                        Hệ điều hành <span class="arrow" data-direction="down">▼</span>
                    </td>
                </tr>
                <tr class="os hidden">
                    <td>Tên hệ điều hành</td>
                    <td>${productDescription.OS}</td>
                </tr>

                <!-- Mục Bảo mật -->
                <tr class="section-title" data-toggle="security">
                    <td colspan="2" style="font-weight: bold; cursor: pointer;">
                        Bảo mật <span class="arrow" data-direction="down">▼</span>
                    </td>
                </tr>
                <tr class="security hidden">
                    <td>Bảo mật hệ thống</td>
                    <td>Windows Hello</td>
                </tr>

                <!-- Mục Thông tin sản phẩm -->
                <tr class="section-title" data-toggle="product-info">
                    <td colspan="2" style="font-weight: bold; cursor: pointer;">
                        Thông tin sản phẩm <span class="arrow" data-direction="down">▼</span>
                    </td>
                </tr>
                <tr class="product-info hidden">
                    <td>Mã sản phẩm</td>
                    <td>${productDescription.ProductID}</td>
                </tr>
                <tr class="product-info hidden">
                    <td>Thời gian bảo hành</td>
                    <td>${productDescription.WarrantyPeriodMonths} tháng</td>
                </tr>
                <tr class="product-info hidden">
                    <td>Màu sắc</td>
                    <td>${productDescription.Color}</td>
                </tr>
            `;
  }

 // Hàm để hiển thị thông số nổi bật
        function showHighlightSpecs() {
            console.log("showHighlightSpecs called"); // Ghi log khi gọi hàm
            document.getElementById('highlight-specs').classList.remove('hidden');
            document.getElementById('all-specs').classList.add('hidden');
            document.getElementById('highlight-btn').classList.add('active');
            document.getElementById('all-spec-btn').classList.remove('active');
        }

        // Hàm để hiển thị tất cả thông số
        function showAllSpecs() {
            console.log("showAllSpecs called"); // Ghi log khi gọi hàm
            document.getElementById('all-specs').classList.remove('hidden');
            document.getElementById('highlight-specs').classList.add('hidden');
            document.getElementById('all-spec-btn').classList.add('active');
            document.getElementById('highlight-btn').classList.remove('active');
        }

        // Thêm sự kiện click cho các nút hiển thị
        document.addEventListener('DOMContentLoaded', () => {
            document.getElementById('highlight-btn').addEventListener('click', showHighlightSpecs);
            document.getElementById('all-spec-btn').addEventListener('click', showAllSpecs);

          // Mặc định hiển thị thông số nổi bật khi tải trang
          showHighlightSpecs(); // Gọi hàm để hiển thị thông số nổi bật
        });

        document.addEventListener('click', function (event) {
          if (event.target.closest('.section-title')) {
              const section = event.target.closest('.section-title');
              const sectionClass = section.getAttribute('data-toggle');
              const rows = document.querySelectorAll(`.${sectionClass}`);
              const arrow = section.querySelector('.arrow');

              // Ẩn/hiện các hàng có liên quan
              rows.forEach(row => row.classList.toggle('hidden'));

              // Đổi mũi tên
              if (arrow.getAttribute('data-direction') === 'down') {
                  arrow.textContent = '▲';
                  arrow.setAttribute('data-direction', 'up');
              } else {
                  arrow.textContent = '▼';
                  arrow.setAttribute('data-direction', 'down');
              }
          }
      });
        


