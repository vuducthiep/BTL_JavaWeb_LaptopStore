package com.example.ProjectLaptopStore.Controller;

import com.example.ProjectLaptopStore.DTO.Promotion_getPromotionProduct;
import com.example.ProjectLaptopStore.DTO.Promotions_DisplayPromotionsDTO;
import com.example.ProjectLaptopStore.Response.Admin_BillingResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_DashBoardResponseDTO;
import com.example.ProjectLaptopStore.Response.Admin_ReceiptResponseDTO;
import com.example.ProjectLaptopStore.Service.*;
import org.apache.coyote.Response;
import org.modelmapper.internal.bytebuddy.asm.Advice;
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
    private AdminService adminService;

    //API cho trang dashboard
    //chưa tối ưu
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
    //chưa sửa
    @GetMapping(value = "/warehouse/")
    public Admin_ReceiptResponseDTO adminWareHouse(){
        Admin_ReceiptResponseDTO result = adminService.adminReceiptAtService();
        return result;
    }



    //API lay thong tin cac khuyen mai
    @GetMapping(value = "/promotion")
    public List<Promotions_DisplayPromotionsDTO> promotion(){
        List<Promotions_DisplayPromotionsDTO> rs = promotionService.getPromotions();
        return rs;
    }

    //API tim khuyen mai theo ten
    @GetMapping(value = "/promotion/{promotionName}")
    public List<Promotions_DisplayPromotionsDTO> SearchPromotion(@PathVariable(name = "promotionName") String promotionName){
        List<Promotions_DisplayPromotionsDTO> result = promotionService.searchPromotion(promotionName);
        return  result;
    }

    //API hien thi danh sach product theo promotionName
    @GetMapping(value = "/promotion-product/{promotionName}")
    public List<Promotion_getPromotionProduct> displayPromotionProduct(@PathVariable(name = "promotionName")String promotionName){
        List<Promotion_getPromotionProduct> rs = promotionService.displayPromotionProduct(promotionName);
        return  rs;
    }

    //API them promotionproduct khi tich
    @PostMapping(value = "/promotion-product/add-promotion/{productID}/{promotionID}")
    public ResponseEntity<?> addPromotion(@PathVariable(name = "productID")int productID,
                                          @PathVariable(name = "promotionID")int promotionID){
            promotionService.addPromotionProduct(productID,promotionID);
            return  ResponseEntity.ok("success");
    }

    //API remove promotionpoduct khi bo tich
    @DeleteMapping(value = "/promotion-product/remove-promotion/{productID}/{promotionID}")
    public ResponseEntity<?> removePromotion(@PathVariable(name = "productID")int productID,
                                             @PathVariable(name = "promotionID")int promotionID){
        promotionService.deletePromotionProduct(productID,promotionID);
        return  ResponseEntity.ok("success");
    }
}
