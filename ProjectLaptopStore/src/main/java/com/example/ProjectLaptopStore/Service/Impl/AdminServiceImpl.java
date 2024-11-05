package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.Convert.Order_TotalAmountInMonthDTOConverter;
import com.example.ProjectLaptopStore.DTO.*;
import com.example.ProjectLaptopStore.Entity.WareHouseEntity;
import com.example.ProjectLaptopStore.Repository.*;
import com.example.ProjectLaptopStore.Response.Admin_BillingResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_DashBoardResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_ReceiptResponseDTO;
import com.example.ProjectLaptopStore.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ISuppliersService suppliersService;

    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private ExportReceiptDetailsRepository exportReceiptDetailsRepository;
    @Autowired
    private IWareHouseRepository wareHouseRepository;
    @Autowired
    private Order_TotalAmountInMonthDTOConverter order_TotalAmountInMonthDTOConverter;

    @Autowired
    private ProductsInWarehouseRepository productsInWarehouseRepository;
    @Autowired
    private ImportReceiptDetailsRepository importReceiptDetailsRepository;
    //hàm gộp thông tin cho trang dashboard
    @Override
    public Admin_DashBoardResponseDTO adminDashBoardAtService() {
        Admin_DashBoardResponseDTO adminInfo = new Admin_DashBoardResponseDTO();
        try {
            Integer productSellInMonth = orderDetailService.getQuantityProductCurrentMonthAtService();
            Integer totalCustomerInMonth = orderService.TotalCustomerInMonthAtService();
            Integer totalNewCustomerInMonth = customerService.getNewCustomerCurrentMonth();
            BigDecimal totalAmountInMonth = orderService.getTotalAmountInMountAtService();
            List<Customer_CountNewCustomerPerMonthDTO> newCustomerForChart = customerService.listCountNewCustomerPerMonth();
            List<Order_CountTotalAmountDTO> totalAmountForChart = orderService.listCountTotalAmountAtService();
            List<OrderDetail_CountQuantityProductPerMonthDTO> quantityProductForChart = orderDetailService.listCountQuantityProductPerMonth();
            List<Product_FindTopPurchasedProductsDTO> listTopProductSell = productService.findTopPurchasedProductAtService();
            List<Customer_FindTopCustomerInMonthDTO> listTopCustomer = customerService.listTopCustomerInMonth();
            adminInfo.setQuantitySellProductCurrentMonth(productSellInMonth);
            adminInfo.setTotalCustomerInCurrentMonth(totalCustomerInMonth);
            adminInfo.setTotalNewCustomerInCurrentMonth(totalNewCustomerInMonth);
            adminInfo.setTotalAmountInCurrentMonth(totalAmountInMonth);
            adminInfo.setNewCustomerPerMonthMap(newCustomerForChart);
            adminInfo.setTotalAmountPerMonthMap(totalAmountForChart);
            adminInfo.setTotalQuantitySellProductPerMonthMap(quantityProductForChart);
            adminInfo.setTopPurchasedProductInMonth(listTopProductSell);
            adminInfo.setTopCustomerInMonth(listTopCustomer);
        }catch (Exception e){
            e.printStackTrace();
        }
        return adminInfo;
    }

    @Override
    public Admin_BillingResponseDTO adminBillingAtService() {
            Admin_BillingResponseDTO billingResponseDTO = new Admin_BillingResponseDTO();
            try {
                BigDecimal totalAmountOnline = orderRepository.getTotalAmountPayOnline();
                BigDecimal totalAmountOffline = orderRepository.getTotalAmountPayOffline();
                List<Order_InvoiceDetailDTO> listInvoice = orderRepository.listInvoiceDetail();
                billingResponseDTO.setTotalAmountPayOnline(totalAmountOnline);
                billingResponseDTO.setTotalAmountPayOffline(totalAmountOffline);
                billingResponseDTO.setListInvoiceDetail(listInvoice);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return billingResponseDTO;
    }

    @Override
    public Admin_ReceiptResponseDTO adminReceiptAtService() {
        Admin_ReceiptResponseDTO adminReceiptResponseDTO = new Admin_ReceiptResponseDTO();
        try {
            List<WareHouseEntity> listWareHouse = wareHouseRepository.findAll();
            Integer totalQuantity = productsInWarehouseRepository.getTotalQuantity();
            Integer minStock = productsInWarehouseRepository.countProductsMinStockLevel();
            Integer maxStock = productsInWarehouseRepository.countProductsMaxStockLevel();
            List<ImportExport_ReceiptDTO> listExportReceipt = exportReceiptDetailsRepository.listExportReceipt();
            List<ImportExport_ReceiptDTO> listImportReceipt = importReceiptDetailsRepository.listImportReceipt();
            List<ProductsInWarehouse_DTO> listProductsInWarehouse = productsInWarehouseRepository.listProductsInWarehouse();
            adminReceiptResponseDTO.setListWareHouse(listWareHouse);
            adminReceiptResponseDTO.setTotalQuantity(totalQuantity);
            adminReceiptResponseDTO.setCountProductsMinStockLevel(minStock);
            adminReceiptResponseDTO.setCountProductsMaxStockLevel(maxStock);
            adminReceiptResponseDTO.setListExportReceipt(listExportReceipt);
            adminReceiptResponseDTO.setListImportReceipt(listImportReceipt);
            adminReceiptResponseDTO.setListProductsInWarehouse(listProductsInWarehouse);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return adminReceiptResponseDTO;
    }

}
