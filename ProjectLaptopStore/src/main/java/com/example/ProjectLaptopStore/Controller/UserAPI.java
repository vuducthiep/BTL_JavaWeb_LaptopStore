package com.example.ProjectLaptopStore.Controller;

import com.example.ProjectLaptopStore.DTO.Product_DisplayForHomePageDTO;
import com.example.ProjectLaptopStore.DTO.User_RegisterDTO;
import com.example.ProjectLaptopStore.Service.IProductService;
import com.example.ProjectLaptopStore.Service.IUserService;
import com.example.ProjectLaptopStore.Service.Impl.ProductDescriptionServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class UserAPI {
    @Autowired
    private ProductDescriptionServiceImpl productDescriptionService;

    @Autowired
    private IProductService productService;
    IUserService  userService;

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

    @GetMapping(value = "/users/")
    public List<User_RegisterDTO> listUserLogin() {
        List<User_RegisterDTO> users = userService.getAllUsers();
        return users;
    }

    @PostMapping(value = "/register")
    public void createUser(@RequestBody User_RegisterDTO user) {
        userService.createUser(user);
    }

    @DeleteMapping(value = "/user-delete/{phoneNumber}")
    public void deleteUser(@PathVariable String phoneNumber) {
        userService.deleteUser(phoneNumber);
    }
}
