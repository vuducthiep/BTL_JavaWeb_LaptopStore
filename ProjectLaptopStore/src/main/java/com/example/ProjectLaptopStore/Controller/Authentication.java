package com.example.ProjectLaptopStore.Controller;

import com.example.ProjectLaptopStore.DTO.*;
import com.example.ProjectLaptopStore.Service.IUserService;
import com.example.ProjectLaptopStore.Service.Impl.UserServiceImpl;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
//phân quyền
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
    public User_AuthenticationResponseDTO login(@RequestBody User_LoginDTO user) {
        return userService.Authenticate(user.getPhoneNumber(), user.getPassword());
    }
//    @PostMapping(value = "/token-valid")
//    public boolean login(@RequestBody IntrospecTokenDTO token) throws ParseException, JOSEException {
//
//    }
//    @PostMapping(value = "/token-valid")
//    public boolean login(@RequestBody IntrospecTokenDTO token) {
//        return userService.Authenticate(user.getPhoneNumber(), user.getPassword());
//    }

}
