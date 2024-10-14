package com.example.ProjectLaptopStore.Controller;

import com.example.ProjectLaptopStore.DTO.User_LoginDTO;
import com.example.ProjectLaptopStore.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    IUserService userService;

    @PostMapping(value = "/login-user")
    public boolean loginUser(@RequestBody User_LoginDTO user) {
        Boolean result = userService.Authenticate(user.getPhoneNumber(), user.getPassword());
        return result;
    }
}
