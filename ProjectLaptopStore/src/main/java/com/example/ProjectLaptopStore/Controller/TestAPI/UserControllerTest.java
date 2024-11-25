package com.example.ProjectLaptopStore.Controller.TestAPI;

import com.example.ProjectLaptopStore.DTO.Customer_CountNewCustomerPerMonthDTO;
import com.example.ProjectLaptopStore.DTO.ProductDetailDTO;
import com.example.ProjectLaptopStore.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/test")
public class UserControllerTest {
    @Autowired
    private ProductService productService;
    //API lấy số khách mới mỗi tháng cho biểu đồ
    @GetMapping(value = "/user/findbykey/{key}")
    public List<ProductDetailDTO> searchProductByKey(@PathVariable(name = "key") String key ){
        List<ProductDetailDTO> result = productService.listSearchProductByKey(key);
        return result;
    }
}
