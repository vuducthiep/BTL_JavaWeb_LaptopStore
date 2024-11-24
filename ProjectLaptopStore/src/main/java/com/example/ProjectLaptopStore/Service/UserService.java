package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.DTO.*;
import com.nimbusds.jose.JOSEException;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface UserService {
    // lay user
    List<User_RegisterDTO> getAllUsers();

    //dang ki user
    void createUser(User_RegisterDTO user) ;

    //update user
    void updateUser(User_UpdateUserDTO dto);

    //xoa user
    void deleteUser(String phoneNumber);

    //dang nhap
    User_AuthenticationResponseDTO Authenticate(String phoneNumber, String password);

    // kiem tra hop le token
    TokenValidDTO validateToken(IntrospecTokenDTO token) throws JOSEException, ParseException;

    // phan trang user
    Page<User_DTO> searchUser(int page, int size);

    //thong tin user
    User_DTO UserInfor();
}
