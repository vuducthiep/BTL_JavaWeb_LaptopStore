// Function to open the edit product form and populate it with data
function editProduct(productId) {
  // Gọi API để lấy sản phẩm theo productId (ở đây bạn cần thay thế bằng API thực tế của bạn)
  fetch(`http://localhost:8080/admin/product/update/${productId}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .then((product) => {
      // Điền thông tin vào các trường trong form sửa
      idProduct=product.productId;
      idProductDes=product.productDescriptionId;
      idSupplier=product.supplierId;
      console.log('id product :',idProduct);
      console.log('id productDes :',idProductDes);
      console.log('id productSuppiler :',idSupplier);
      
      document.getElementById("edit-productName").value = product.productName;
      document.getElementById("edit-productBrand").value = product.productBrand;
      document.getElementById("edit-model").value = product.model;
      document.getElementById("edit-price").value = product.price;
      document.getElementById("edit-stockQuantity").value =
        product.stockQuantity;
      document.getElementById("edit-releaseDate").value = product.releaseDate;
      document.getElementById("edit-warrantyPeriod").value =
        product.warrantyPeriod;
      document.getElementById("edit-imageUrl").value = product.imageUrl;

      // Điền thông tin CPU, RAM, ổ cứng, màn hình...
      document.getElementById("edit-cpuCompany").value = product.cpuCompany;
      document.getElementById("edit-cpuTechnology").value =
        product.cpuTechnology;
      document.getElementById("edit-cpuType").value = product.cpuType;
      document.getElementById("edit-minimumCPUspeed").value =
        product.minimumCPUspeed;
      document.getElementById("edit-maximunSpeed").value = product.maximunSpeed;
      document.getElementById("edit-multiplier").value = product.multiplier;
      document.getElementById("edit-processorCache").value =
        product.processorCache;
      document.getElementById("edit-brandCardOboard").value =
        product.brandCardOboard;
      document.getElementById("edit-modelCardOboard").value =
        product.modelCardOboard;
      document.getElementById("edit-fullNameCardOboard").value =
        product.fullNameCardOboard;
      document.getElementById("edit-vgaBrand").value = product.vgaBrand;
      document.getElementById("edit-vgaFullName").value = product.vgaFullName;

      document.getElementById("edit-ramCapacity").value = product.ramCapacity;
      document.getElementById("edit-ramType").value = product.ramType;
      document.getElementById("edit-ramSpeed").value = product.ramSpeed;
      document.getElementById("edit-numberOfRemovableSlots").value =
        product.numberOfRemovableSlots;
      document.getElementById("edit-numberOfOnboardRAM").value =
        product.numberOfOnboardRAM;
      document.getElementById("edit-maximumRAMSupport").value =
        product.maximumRAMSupport;
      document.getElementById("edit-totalSSDHDDSlots").value =
        product.totalSSDHDDSlots;
      document.getElementById("edit-numberOfSSDHDDSlotsRemaining").value =
        product.numberOfSSDHDDSlotsRemaining;

      document.getElementById("edit-maximumHardDriveUpgradeCapacity").value =
        product.maximumHardDriveUpgradeCapacity;

      document.getElementById("edit-ssdType").value = product.ssdType;

      document.getElementById("edit-hardDriveType").value =
        product.hardDriveType;
      document.getElementById("edit-capacity").value = product.capacity;

      document.getElementById("edit-screenSize").value = product.screenSize;

      document.getElementById("edit-displayTechnology").value =
        product.displayTechnology;
      document.getElementById("edit-resolution").value = product.resolution;

      document.getElementById("edit-screenType").value = product.screenType;

      document.getElementById("edit-scanningFrequency").value =
        product.scanningFrequency;
      document.getElementById("edit-basePlate").value = product.basePlate;

      document.getElementById("edit-colorCoverage").value =
        product.colorCoverage;

      document.getElementById("edit-screenRatio").value = product.screenRatio;

      document.getElementById("edit-os").value = product.os;

      document.getElementById("edit-version").value = product.version;

      document.getElementById("edit-security").value = product.security;

      document.getElementById("edit-keyboardType").value = product.keyboardType;
      if (product.numericKeypad === true) {
        document.getElementById("edit-numericKeypadTrue").checked = true;
      } else if (product.numericKeypad === false) {
        document.getElementById("edit-numericKeypadFalse").checked = true;
      }

      document.getElementById("edit-keyboardLight").value =
        product.keyboardLight;

      document.getElementById("edit-touchPad").value = product.touchPad;

      document.getElementById("edit-powerSupply").value = product.powerSupply;
      document.getElementById("edit-communicationPort").value =
        product.communicationPort;

      document.getElementById("edit-size").value = product.size;

      document.getElementById("edit-productWeight").value =
        product.productWeight;

      document.getElementById("edit-material").value = product.material;
      document.getElementById("edit-wifi").value = product.wifi;
      document.getElementById("edit-bluetooth").value = product.bluetooth;
      document.getElementById("edit-webcam").value = product.webcam;

      document.getElementById("edit-batteryType").value = product.batteryType;
      document.getElementById("edit-batteryCapacity").value =
        product.batteryCapacity;
      document.getElementById("edit-accessoriesInTheBox").value =
        product.accessoriesInTheBox;

      document.getElementById("edit-color").value = product.color;
      document.getElementById("edit-origin").value = product.origin;
      document.getElementById("edit-pnProductCode").value =
        product.pnProductCode;

      document.getElementById("edit-warrantyPeriodMonths").value =
        product.warrantyPeriodMonths;

      document.getElementById("edit-storageInstructions").value =
        product.storageInstructions;

      document.getElementById("edit-userManual").value = product.userManual;

      document.getElementById("edit-brightness").value = product.brightness;

      // Hiển thị form sửa
      document.getElementById("edit-product-form-container").style.display =
        "block";
      document.getElementById(
        "edit-form-title"
      ).textContent = `Sửa sản phẩm: ${product.productName}`;
    })
    .catch((error) => {
      console.error("Error fetching product data:", error);
    });
}

// // Lấy đối tượng select
// const supplierSelect = document.getElementById("supplier-select");

// // Lắng nghe sự kiện thay đổi của combobox
// supplierSelect.addEventListener("change", function () {
//     // Lấy giá trị supplierID đã chọn
//     const supplierID = supplierSelect.value;

//     // Kiểm tra và in ra ID đã chọn
//     if (supplierID) {
//         console.log("Chọn Nhà Cung Cấp ID:", supplierID);
//         idSupplier = supplierID;  // Lưu lại idSupplier để sử dụng trong các API khác
//     } else {
//         console.log("Chưa chọn nhà cung cấp");
//     }
// });

let idSupplier = null;
let idProduct = null;
let idProductDes = null;

// In ra để kiểm tra giá trị của supplierID
console.log("Supplier ID:", idSupplier);  // Kiểm tra giá trị của supplierID
// Function to close the form
document
  .getElementById("cancel-edit-btn")
  .addEventListener("click", function () {
    document.getElementById("edit-product-form-container").style.display =
      "none";
  });

  async function saveProduct(productId) {
    try {
        console.log('id product trước khi update :',idProduct);
        console.log('id productDes trước khi update  :',idProductDes);
        console.log('id productSuppiler  trước khi update :',idSupplier);
      // Thu thập dữ liệu từ các trường trong form sửa
      const updatedProduct = {
        productId:idProduct,
        supplierId:idSupplier,
        productDescriptionId:idProductDes,
        productName: document.getElementById("edit-productName").value,
        productBrand: document.getElementById("edit-productBrand").value,
        model: document.getElementById("edit-model").value,
        price: parseFloat(document.getElementById("edit-price").value),
        stockQuantity: parseInt(
          document.getElementById("edit-stockQuantity").value
        ),
        releaseDate: document.getElementById("edit-releaseDate").value,
        warrantyPeriod: document.getElementById("edit-warrantyPeriod").value,
        imageUrl: document.getElementById("edit-imageUrl").value,
        // Các thông số kỹ thuật khác
        cpuCompany: document.getElementById("edit-cpuCompany").value,
        cpuTechnology: document.getElementById("edit-cpuTechnology").value,
        cpuType: document.getElementById("edit-cpuType").value,
        minimumCPUspeed: parseFloat(
          document.getElementById("edit-minimumCPUspeed").value
        ),
        maximunSpeed: parseFloat(
          document.getElementById("edit-maximunSpeed").value
        ),
        multiplier: document.getElementById("edit-multiplier").value,
        processorCache: document.getElementById("edit-processorCache").value,
        brandCardOboard: document.getElementById("edit-brandCardOboard").value,
        modelCardOboard: document.getElementById("edit-modelCardOboard").value,
        fullNameCardOboard: document.getElementById(
          "edit-fullNameCardOboard"
        ).value,
        vgaBrand: document.getElementById("edit-vgaBrand").value,
        vgaFullName: document.getElementById("edit-vgaFullName").value,
        ramCapacity: parseInt(document.getElementById("edit-ramCapacity").value),
        ramType: document.getElementById("edit-ramType").value,
        ramSpeed: document.getElementById("edit-ramSpeed").value,
        numberOfRemovableSlots: parseInt(
          document.getElementById("edit-numberOfRemovableSlots").value
        ),
        numberOfOnboardRAM: parseInt(
          document.getElementById("edit-numberOfOnboardRAM").value
        ),
        maximumRAMSupport: parseInt(
          document.getElementById("edit-maximumRAMSupport").value
        ),
        // Các trường bổ sung
        numericKeypad: document.getElementById("edit-numericKeypadTrue").checked
          ? true
          : false,
        brightness: parseInt(document.getElementById("edit-brightness").value),
        hardDriveType:document.getElementById("edit-hardDriveType").value,
        totalSSDHDDSlots: parseInt(
            document.getElementById("edit-totalSSDHDDSlots").value
          ),

          
          numberOfSSDHDDSlotsRemaining: parseInt(
            document.getElementById("edit-numberOfSSDHDDSlotsRemaining").value
          ),
          
          maximumHardDriveUpgradeCapacity: parseInt(
            document.getElementById("edit-maximumHardDriveUpgradeCapacity").value
          ),
          ssdType:document.getElementById("edit-ssdType").value,
          
          capacity: parseInt(
            document.getElementById("edit-capacity").value
          ),
          screenSize:document.getElementById("edit-screenSize").value,
          displayTechnology:document.getElementById("edit-displayTechnology").value,
          resolution:document.getElementById("edit-resolution").value,
          screenType:document.getElementById("edit-screenType").value,
          basePlate:document.getElementById("edit-basePlate").value,
          colorCoverage:document.getElementById("edit-colorCoverage").value,
          screenRatio: parseInt(
            document.getElementById("edit-screenRatio").value
          ),
          communicationPort:document.getElementById("edit-communicationPort").value,
          wifi:document.getElementById("edit-wifi").value,
          bluetooth:document.getElementById("edit-bluetooth").value,
          webcam:document.getElementById("edit-webcam").value,
          os:document.getElementById("edit-os").value,
          version:document.getElementById("edit-version").value,
          security:document.getElementById("edit-security").value,
          keyboardType:document.getElementById("edit-keyboardType").value,
          keyboardLight:document.getElementById("edit-keyboardLight").value,
          touchPad:document.getElementById("edit-touchPad").value,
          batteryType:document.getElementById("edit-batteryType").value,
          batteryCapacity:parseInt(
            document.getElementById("edit-batteryCapacity").value
          ),
          powerSupply:document.getElementById("edit-powerSupply").value,
          accessoriesInTheBox:document.getElementById("edit-accessoriesInTheBox").value,
          size:parseInt(
            document.getElementById("edit-size").value
          ),
          productWeight:parseInt(
            document.getElementById("edit-productWeight").value
          ),
          material:document.getElementById("edit-material").value,
          pnProductCode:document.getElementById("edit-pnProductCode").value,
          origin:document.getElementById("edit-origin").value,
          warrantyPeriodMonths:parseInt(
            document.getElementById("edit-warrantyPeriodMonths").value
          ),
          storageInstructions:document.getElementById("edit-storageInstructions").value,
          userManual:document.getElementById("edit-userManual").value,
          color:document.getElementById("edit-color").value,

        // Thêm các trường khác theo cấu trúc dữ liệu của bạn
      };
      console.log('sau khi update' ,productId);
      console.log('sau khi update' ,idProductDes);
      console.log('sau khi update',idSupplier);
      // Gửi yêu cầu PUT đến API
      const response = await fetch(
        `http://localhost:8080/admin/product/update/${productId}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(updatedProduct), // Dữ liệu cần cập nhật
        }
      );
  
      if (!response.ok) {
        const errorDetails = await response.text();  // Lấy thông tin lỗi từ phản hồi
        console.error("Error details:", errorDetails);
        throw new Error("Failed to update product");
    }
  
      const data = await response.json();
  
      // Hiển thị thông báo thành công
      alert("Cập nhật sản phẩm thành công!");
  
      // Đóng form sửa
      document.getElementById("edit-product-form-container").style.display =
        "none";
  
      // Làm mới danh sách sản phẩm (nếu có)
      refreshProductList();
    } catch (error) {
      // Xử lý lỗi
    //   console.error("Error updating product:", error);
      alert("Đã cập nhật thông tin sản phẩm");
      window.location.reload();
    }
  }
  
  // Gắn sự kiện click vào nút Lưu
  document.getElementById("save-edit-btn").addEventListener("click", async () => {
    const productId = idProduct;
    console.log(productId);
    await saveProduct(productId);
  });
   