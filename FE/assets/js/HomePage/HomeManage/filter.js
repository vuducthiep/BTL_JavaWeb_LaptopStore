
// Hàm để lấy giá trị từ form bộ lọc
function getFilterValues() {
    const form = document.getElementById('filter-form');
    const formData = new FormData(form);
    const filters = {};
  
    // Lấy các giá trị checkbox đã chọn
    for (const [key, value] of formData.entries()) {
      if (!filters[key]) {
        filters[key] = [];
      }
      filters[key].push(value);
    }
  
    return filters;
}
  
// Hàm để cập nhật URL với các tham số lọc
function setURLParams(filterCriteria) {
    const url = new URL(window.location);
    const params = new URLSearchParams();
  
    // Thêm từng tiêu chí vào URL
    for (const key in filterCriteria) {
      if (filterCriteria[key].length) {
        params.set(key, filterCriteria[key].join(','));
      }
    }
  
    // Cập nhật URL mà không reload trang
    url.search = params.toString();
    window.history.pushState({}, '', url);
}
  

  
// Gọi hàm để lấy và hiển thị sản phẩm theo tiêu chí lọc
document.getElementById('filter-form').addEventListener('submit', function (event) {
    event.preventDefault(); // Ngăn chặn việc submit form
    getFilteredProducts(); // Gọi hàm lọc sản phẩm
});



