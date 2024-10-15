package com.example.ProjectLaptopStore.Service.Impl;

import com.example.ProjectLaptopStore.DTO.IntrospecTokenDTO;
import com.example.ProjectLaptopStore.DTO.TokenValidDTO;
import com.example.ProjectLaptopStore.DTO.User_AuthenticationResponseDTO;
import com.example.ProjectLaptopStore.DTO.User_RegisterDTO;
import com.example.ProjectLaptopStore.Entity.UserEntity;
import com.example.ProjectLaptopStore.Repository.IUserRepository;
import com.example.ProjectLaptopStore.Service.IUserService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
public class UserServiceImpl implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    IUserRepository userRepository;
    ModelMapper modelMapper;
//    private final Authentication authentication;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected  String SIGNER_KEY;

//    private final UserAPI userAPI;

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
// tra ve token
    @Override
    public User_AuthenticationResponseDTO Authenticate(String phoneNumber, String password) {
        UserEntity userEntity = userRepository.findAllByPhoneNumber(phoneNumber);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(userEntity != null) {
            boolean status =  passwordEncoder.matches(password, userEntity.getPassword());
        }
        else throw new RuntimeException("So dien thoai khong ton tai");
        var token = generateToken(phoneNumber);
        return User_AuthenticationResponseDTO.builder()
                .status(true)
                .token(token)
                .build();
    }


    @Override
    public TokenValidDTO validateToken(IntrospecTokenDTO token) throws JOSEException, ParseException {
        var tk = token.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(tk);
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return TokenValidDTO.builder()
                .token(tk)
                .valid(verified && expirationTime.after(new Date()))
                .build();
    }

    // tao token
    private String generateToken(String phonenumber){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(phonenumber)
                .issuer("laptopabc.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error(e.getMessage() + "Can not create token");
            throw new RuntimeException(e);
        }
    }
}

