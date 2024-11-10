package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.Response.Admin_BillingResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_DashBoardResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_ReceiptResponseDTO;

public interface AdminService {
    Admin_DashBoardResponseDTO adminDashBoardAtService();
    Admin_BillingResponseDTO adminBillingAtService();
    Admin_ReceiptResponseDTO adminReceiptAtService(Integer warehouseID);


}
