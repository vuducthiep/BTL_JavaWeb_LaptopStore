
const params = new URLSearchParams(window.location.search);
const product1ID = params.get('product1');
const product2ID = params.get('product2');


fetch('http://localhost:3000/products')
  .then(response => response.json())
  .then(products => {
    const product1 = products.find(product => product.ProductID === parseInt(product1ID));
    const product2 = products.find(product => product.ProductID === parseInt(product2ID));

  
    if (product1) {
      document.querySelector('.col-4:nth-child(2) .product__image').src = product1.ImageURL;
      document.querySelector('.col-4:nth-child(2) .product__name').textContent = product1.ProductName;
      document.querySelector('.col-4:nth-child(2) .product__price').textContent = product1.Price;
    }

    
    if (product2) {
      document.querySelector('.col-4:nth-child(3) .product__image').src = product2.ImageURL;
      document.querySelector('.col-4:nth-child(3) .product__name').textContent = product2.ProductName;
      document.querySelector('.col-4:nth-child(3) .product__price').textContent = product2.Price;
    }
  })
  .catch(error => console.error('Error fetching products:', error));
