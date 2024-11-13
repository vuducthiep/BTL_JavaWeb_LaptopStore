package com.example.ProjectLaptopStore.Controller;

import com.example.ProjectLaptopStore.DTO.CustomerDTO;
import com.example.ProjectLaptopStore.DTO.ProductsInWarehouse_DTO;
import com.example.ProjectLaptopStore.DTO.Promotion_getPromotionProductDTO;
import com.example.ProjectLaptopStore.DTO.Promotions_DisplayPromotionsDTO;
import com.example.ProjectLaptopStore.Response.Admin_BillResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_CustomerResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_DashBoardResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_WarehouseResponseDTO;
import com.example.ProjectLaptopStore.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Lớp kiểm soát api của role admin
@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/admin")
public class AdminController {

//    @PersistenceContext
//    private EntityManager entityManager;
    @Autowired
    private IPromotionService promotionService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductInWareHouseService productInWarehouseService;
    //API cho trang dashboard
    @Operation(summary = "Get dashboard data for admin")
    //thông báo lỗi cụ thể trên công cụ test
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved dashboard data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - token is invalid or expired"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/dashboard/")
    public Admin_DashBoardResponseDTO adminDashBoard(){
        Admin_DashBoardResponseDTO result = adminService.adminDashBoardAtService();
        return result;
    }
    //API cho trang bill
    @GetMapping(value = "/bill/")
    public Admin_BillResponseDTO adminBilling(){
        Admin_BillResponseDTO result = adminService.adminBillingAtService();
        return result;
    }

    //API lấy thong tin trang nhà kho
    @GetMapping(value = "/warehouse/{warehouseID}")
    public Admin_WarehouseResponseDTO adminWareHouse(@PathVariable(name = "warehouseID") Integer warehouseID){
        return adminService.adminReceiptAtService(warehouseID);
    }

    //API lấy thông tin của sản phẩm được chọn để sửa ở màn kho hàng
    @GetMapping(value = "/warehouse/update/{productID}")
    public ProductsInWarehouse_DTO getProductsInWarehouse(@PathVariable(name = "productID") Integer productID){
        return productInWarehouseService.getProductInWarehouse(productID);
    }
    //API cập nhật thông tin của sản phẩm trong kho
    //productID không cần dùng
    @PutMapping(value = "/warehouse/update/{productID}")
    public void warehouseUpdate(@RequestBody ProductsInWarehouse_DTO productsInWarehouseUpdate){
        productInWarehouseService.productInWareHouseUpdate(productsInWarehouseUpdate);
    }

    //API lay thong tin cac khuyen mai
    @GetMapping(value = "/promotion")
    public List<Promotions_DisplayPromotionsDTO> promotion(){
        List<Promotions_DisplayPromotionsDTO> rs = promotionService.getPromotions();
        return rs;
    }
    //API tìm kiếm khuyến mãi theo tên
    @GetMapping(value = "/promotion/{promotionName}")
    public List<Promotions_DisplayPromotionsDTO> SearchPromotion(@PathVariable(name = "promotionName") String promotionName){
        List<Promotions_DisplayPromotionsDTO> result = promotionService.searchPromotion(promotionName);
        return  result;
    }
    //API hiển thị thông tin khuyến mãi cụ thể
    @GetMapping(value = "/promotion-product/{promotionName}")
    public List<Promotion_getPromotionProductDTO> displayPromotionProduct(@PathVariable(name = "promotionName")String promotionName){
        List<Promotion_getPromotionProductDTO> rs = promotionService.displayPromotionProduct(promotionName);
        return  rs;
    }
    //API thêm mã giảm giá cho sản phẩm
    @PostMapping(value = "/promotion-product/add-promotion/{productID}/{promotionID}")
    public ResponseEntity<?> addPromotion(@PathVariable(name = "productID")int productID,
                                          @PathVariable(name = "promotionID")int promotionID){
            promotionService.addPromotionProduct(productID,promotionID);
            return  ResponseEntity.ok("success");
    }
    //API xóa mã giảm giá cho sản phẩm
    @DeleteMapping(value = "/promotion-product/remove-promotion/{productID}/{promotionID}")
    public ResponseEntity<?> removePromotion(@PathVariable(name = "productID")int productID,
                                             @PathVariable(name = "promotionID")int promotionID){
        promotionService.deletePromotionProduct(productID,promotionID);
        return  ResponseEntity.ok("success");
    }

    //API lấy thông tin cho màn quản lý khách hàng
    @GetMapping(value = "/customer/")
    public Admin_CustomerResponseDTO adminCustomer(){
        return customerService.adminCustomer();
    }

    //API thêm khách hàng
    @PostMapping(value = "/customer/create-customer/")
    public void customerCreate(@RequestBody CustomerDTO customerNew){
        customerService.createCustomerAtService(customerNew);
    }

    //API lấy thông tin chi tiết cho  

    //API sửa khách hàng
    @PutMapping(value = "/customer/update/{id}")
    public void customerUpdate(@RequestBody CustomerDTO customerUpdate){
        customerService.updateCustomerAtService(customerUpdate);
    }

}
