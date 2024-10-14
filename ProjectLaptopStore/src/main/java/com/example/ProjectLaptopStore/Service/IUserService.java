package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.User_RegisterDTO;

import java.util.List;

public interface IUserService {
    List<User_RegisterDTO> getAllUsers();
    void createUser(User_RegisterDTO user) ;
    void updateUser(String phoneNumber, User_RegisterDTO user);
    void deleteUser(String phoneNumber);
    boolean Authenticate(String phoneNumber, String password);
}
