
const importFormModal = document.getElementById('importFormModal');
const productSelectModal = document.getElementById('productSelectModal');
const closeImportModal = document.getElementById('closeImportModal');
const closeProductModal = document.getElementById('closeProductModal');
const productDropdownButton = document.getElementById('productDropdownButton');
const productListSelect = document.getElementById('productListSelect');
const selectedProduct = document.getElementById('selectedProduct');

const apiUrl = 'http://localhost:8080/admin/warehouse/allproduct';

async function getProducts() {
  try {
    const response = await fetch(apiUrl);
    const products = await response.json();
    productListSelect.innerHTML = '';
    products.forEach(product => {
      const li = document.createElement('li');
      li.classList.add('product-item');  

      // Tạo hình ảnh sản phẩm
      const img = document.createElement('img');
      img.src = product.productImage;
      img.alt = product.productName;
      img.style.width = '50px'; 
      img.style.height = 'auto';

      // Tạo tên sản phẩm
      const span = document.createElement('span');
      span.textContent = product.productName;

      li.appendChild(img);
      li.appendChild(span);

      
      li.addEventListener('click', function () {
        selectedProduct.value = product.productName;  
        selectedProduct.setAttribute('data-idProduct', product.productId);  
        productSelectModal.style.display = 'none';  
      });

      productListSelect.appendChild(li);
    });
  } catch (error) {
    console.error('Có lỗi xảy ra khi lấy sản phẩm:', error);
  }
}


productDropdownButton.addEventListener('click', function () {
  productSelectModal.style.display = 'block';  
  getProducts();  
});

closeImportModal.addEventListener('click', function () {
  importFormModal.style.display = 'none';
});


closeProductModal.addEventListener('click', function () {
  productSelectModal.style.display = 'none';
});

document.getElementById('btnImportProduct').addEventListener('click', function () {
  importFormModal.style.display = 'block';
});
