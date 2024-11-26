package com.example.ProjectLaptopStore.Controller;

import com.example.ProjectLaptopStore.Response.User_HomeResponseDTO;
import com.example.ProjectLaptopStore.Service.ProductDescriptionService;
import com.example.ProjectLaptopStore.Service.ProductService;
import com.example.ProjectLaptopStore.Service.UserService;
import lombok.RequiredArgsConstructor;
import com.example.ProjectLaptopStore.Service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class UserController {
    @Autowired
    private ProductDescriptionService productDescriptionService;

    @Autowired
    private SuppliersService suppliersService;


    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/home/")
    public User_HomeResponseDTO getHomePage(@RequestParam(value = "keyword",required = false) String keyword){
        User_HomeResponseDTO result = userService.userHomePage(keyword);
        return result;

    }


}
