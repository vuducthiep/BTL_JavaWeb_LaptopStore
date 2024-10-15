package com.example.ProjectLaptopStore.Controller;

import com.example.ProjectLaptopStore.DTO.Product_DisplayForHomePageDTO;
import com.example.ProjectLaptopStore.Entity.Enum.ProDescription_FindByUserDemand_Enum;
import com.example.ProjectLaptopStore.Entity.Enum.Product_FindProductsByPriceRange_Enum;
import com.example.ProjectLaptopStore.Service.IProductDescriptionService;
import com.example.ProjectLaptopStore.Service.IProductService;
import com.example.ProjectLaptopStore.Service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.example.ProjectLaptopStore.Service.ISuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class UserController {
    @Autowired
    private IProductDescriptionService productDescriptionService;

    @Autowired
    private ISuppliersService suppliersService;


    @Autowired
    private IProductService productService;


//    @GetMapping(value = "/product/productdescription/")
//    public List<ProductDescriptionDTO> ProductDescription(){
//        List<ProductDescriptionDTO> result = productDescriptionService.finAllProductDescription();
//        return result;
//    }
//    @GetMapping(value = "/may-tinh-xach-tay/{productID}")
//    public ProductDescriptionDTO productDescription(@PathVariable Long productID){
//        ProductDescriptionDTO result = productDescriptionService.finProductDescriptionById(productID);
//        return result;
//    }
//

    //API người dùng tìm kiếm danh sách sản phẩm bằng 1 từ khóa bất kì
    @GetMapping(value = "/user/searchproduct/")
    public List<Product_DisplayForHomePageDTO> listProductSearchByKey(@RequestParam(value = "keyword") Object keyword) {
        List<Product_DisplayForHomePageDTO> result = productService.listSearchProductByKey(keyword);
        return result;

    }

    @GetMapping(value = "/user/home/")
    public List<Product_DisplayForHomePageDTO> listProductForHomePage() {
        List<Product_DisplayForHomePageDTO> result = productService.listProductForHomePage();
        return result;
    }

    //API lấy nhà cung cấp cho checkbox(cả checkbox và btn)
    @GetMapping(value = "/user/checkboxsuppliers/")
    public Map<Integer,String> getSuppliersCheckbox(){
        Map<Integer,String> result = suppliersService.getSupplierForCheckbox();
        return result;
    }

    //API hiện mức giá cho checkbox
    @GetMapping(value = "/user/checkboxprice/")
    public Map<List<Integer>,String> getPricesCheckbox(){
        Map<List<Integer>,String> result = Product_FindProductsByPriceRange_Enum.getPriceRanges();
        return result;
    }

    //API lấy công nghệ CPU cho checkbox
    @GetMapping(value = "/user/checkboxcpu/")
    public Map<String,String> getCpuTechnologyCheckbox(){
        Map<String,String> result = productDescriptionService.getCPUTechnologyForCheckbox();
        return result;
    }
    //API lấy dung lượng Ram cho checkbox
    @GetMapping(value = "/user/checkboxram/")
    public Map<Long,Long> getRamCapacityCheckbox(){
        Map<Long,Long> result = productDescriptionService.getRamCapacityForCheckbox();
        return result;
    }
    //API lấy loại ổ cứng cho checkbox
    @GetMapping(value = "/user/checkboxharddrive/")
    public Map<String,String> getHardDriveCheckbox(){
        Map<String,String> result = productDescriptionService.getHardDriveForCheckbox();
        return result;
    }
    //API lấy kích thước màn hình cho checkbox
    @GetMapping(value = "/user/checkboxscreensize/")
    public Map<String,String> getScreenSizeForCheckbox(){
        Map<String,String> result = productDescriptionService.getScreensizeForCheckbox();
        return result;
    }

    //API lấy nhu cầu của khách hàng(cả checkbox và btn)
    @GetMapping(value = "/user/checkboxuserdemand/")
    public Map<String,String> getUserDemandForCheckbox(){
        Map<String,String> result = ProDescription_FindByUserDemand_Enum.typeUserDemand();
        return result;
    }
}
