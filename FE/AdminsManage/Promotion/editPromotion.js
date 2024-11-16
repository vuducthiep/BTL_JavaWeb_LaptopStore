
function addPromotion() {
 
    const promotionID = document.getElementById('promotion-id').value;
    const promotionName = document.getElementById('promotion-name').value;
    const discountPercentage = document.getElementById('discount-percentage').value;
    const promotionDetails = document.getElementById('promotion-details').value;
  
    // Kiểm tra dữ liệu hợp lệ
    if (!promotionID || !promotionName || !discountPercentage || !promotionDetails) {
      alert('Vui lòng điền đầy đủ thông tin!');
      return;
    }
  
    // Tạo đối tượng dữ liệu để gửi
    const promotionData = {
      promotionID: promotionID,
      promotionName: promotionName,
      discountPercentage: discountPercentage,
      promotionDetails: promotionDetails
    };
  
 
    fetch('http://localhost:8080/admin/promotion/update-promotion', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(promotionData) 
    })
    .then(response => response.json()) 
    .then(data => {
      if (data.success) {
        alert('Khuyến mãi được cập nhật thành công!');
        location.reload();
      } else {
        alert('Có lỗi xảy ra khi cập nhật khuyến mãi!');
      }
    })
    .catch(error => {
      console.error('Lỗi khi gửi yêu cầu:', error);
      alert('Có lỗi xảy ra. Vui lòng thử lại!');
    });
  }
  