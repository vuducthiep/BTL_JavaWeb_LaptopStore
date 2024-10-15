package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.IntrospecTokenDTO;
import com.example.ProjectLaptopStore.DTO.TokenValidDTO;
import com.example.ProjectLaptopStore.DTO.User_AuthenticationResponseDTO;
import com.example.ProjectLaptopStore.DTO.User_RegisterDTO;
import com.example.ProjectLaptopStore.Entity.UserEntity;
import com.nimbusds.jose.JOSEException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.text.ParseException;
import java.util.List;

public interface IUserService {
    List<User_RegisterDTO> getAllUsers();
    void createUser(User_RegisterDTO user) ;
    void updateUser(String phoneNumber, User_RegisterDTO user);
    void deleteUser(String phoneNumber);
    User_AuthenticationResponseDTO Authenticate(String phoneNumber, String password);
    TokenValidDTO validateToken(IntrospecTokenDTO token) throws JOSEException, ParseException;
}
