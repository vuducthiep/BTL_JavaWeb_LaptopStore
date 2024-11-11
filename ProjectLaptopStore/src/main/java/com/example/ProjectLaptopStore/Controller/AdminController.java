package com.example.ProjectLaptopStore.Controller;

import com.example.ProjectLaptopStore.DTO.ProductsInWarehouse_DTO;
import com.example.ProjectLaptopStore.DTO.Promotion_getPromotionProductDTO;
import com.example.ProjectLaptopStore.DTO.Promotions_DisplayPromotionsDTO;
import com.example.ProjectLaptopStore.Response.Admin_BillingResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_DashBoardResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_ReceiptResponseDTO;
import com.example.ProjectLaptopStore.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/admin")
public class AdminController {

//    @PersistenceContext
//    private EntityManager entityManager;
    @Autowired
    private IPromotionService promotionService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductInWareHouseService productInWarehouseService;
    //API cho trang dashboard
    @Operation(summary = "Get dashboard data for admin")
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
    //API cho trang billing
    @GetMapping(value = "/billing/")
    public Admin_BillingResponseDTO adminBilling(){
        Admin_BillingResponseDTO result = adminService.adminBillingAtService();
        return result;
    }
    //API lấy thong tin trang nhà kho
    @GetMapping(value = "/warehouse/{warehouseID}")
    public Admin_ReceiptResponseDTO adminWareHouse(@PathVariable(name = "warehouseID") Integer warehouseID){
        return adminService.adminReceiptAtService(warehouseID);
    }
    //API cập nhật thông tin của sản phẩm trong kho
    @PutMapping(value = "/warehouse/update/")
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
}
