package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.DTO.User_RegisterDTO;
import com.example.ProjectLaptopStore.Entity.UserEntity;
import com.example.ProjectLaptopStore.Repository.IUserRepository;
import com.example.ProjectLaptopStore.Service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
public class UserServiceImpl implements IUserService {

    IUserRepository userRepository;
    ModelMapper modelMapper;


    @Override
    public List<User_RegisterDTO> getAllUsers() {
        List<UserEntity> entities = userRepository.findAll();
        List<User_RegisterDTO> user_loginDTO = new ArrayList<>();
        for (UserEntity entity : entities) {
            User_RegisterDTO loginDTO = new User_RegisterDTO();
            loginDTO = modelMapper.map(entity, User_RegisterDTO.class);
            user_loginDTO.add(loginDTO);
        }
        return  user_loginDTO;
    }

    @Override
    public void createUser(User_RegisterDTO user) {
        if(userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            try {
                throw new Exception("Phone number already exists");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Date date = Date.from(user.getRegisterDate().atZone(ZoneId.systemDefault()).toInstant());
        UserEntity userEntity = new UserEntity();
        userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRegistrationDate(date);
        userRepository.save(userEntity);
    }

    @Override
    public void updateUser(String phoneNumber, User_RegisterDTO user) {
        UserEntity userEntity = userRepository.findAllByPhoneNumber(phoneNumber);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(userEntity != null) {
            userEntity = modelMapper.map(user,UserEntity.class);
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        else throw new RuntimeException("So dien thoai khong ton tai");
    }

    @Transactional
    @Override
    public void deleteUser(String phoneNumber) {
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            userRepository.deleteByPhoneNumber(phoneNumber);
        }
        else throw new RuntimeException("So dien thoai khong ton tai");
    }

    @Override
    public boolean Authenticate(String phoneNumber, String password) {
        UserEntity userEntity = userRepository.findAllByPhoneNumber(phoneNumber);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(userEntity != null) {
            return passwordEncoder.matches(password, userEntity.getPassword());
        }
        return false;
    }


}

