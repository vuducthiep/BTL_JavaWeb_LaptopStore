package com.example.ProjectLaptopStore.Controller;

import com.example.ProjectLaptopStore.DTO.User_RegisterDTO;
import com.example.ProjectLaptopStore.Service.IUserService;
import com.example.ProjectLaptopStore.Service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Authentication {
    @Autowired
    IUserService userService;

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

    @PostMapping(value = "/login")
    public boolean login(@RequestBody User_RegisterDTO user) {
        return userService.Authenticate(user.getPhoneNumber(), user.getPassword());
    }
//    @PostMapping(value = "/token-valid")
//    public boolean login(@RequestBody IntrospecTokenDTO token) {
//        return userService.Authenticate(user.getPhoneNumber(), user.getPassword());
//    }

}
