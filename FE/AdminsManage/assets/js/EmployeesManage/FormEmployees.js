
const API_URL = "http://localhost:8080/admin/employee/";

async function fetchEmployees() {
  try {
    const response = await fetch(API_URL);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const employees = await response.json();
    renderEmployeeTable(employees);
  } catch (error) {
    console.error("Error fetching employees:", error);
  }
}

function renderEmployeeTable(employees) {
  const tableBody = document.getElementById("employeeTableBody");
  tableBody.innerHTML = ""; 

  employees.forEach((employee, index) => {
    const row = document.createElement("tr");

    row.innerHTML = `
      <td>${index + 1}</td>
      <td>${employee.name}</td>
      <td>${employee.email}</td>
      <td>${employee.phoneNumber}</td>
      <td>${employee.status === "active" ? "Active" : "Inactive"}</td>
      <td>
        <button class="btn btn-warning btn-sm" onclick="editEmployee(${employee.employeeId})">Chỉnh sửa</button>
        <button class="btn btn-danger btn-sm" onclick="deleteEmployee(${employee.employeeId})">Xóa</button>
      </td>
    `;

    tableBody.appendChild(row);
  });
}
window.onload = fetchEmployees;