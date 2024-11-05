package com.example.ProjectLaptopStore.DTO;

import com.example.ProjectLaptopStore.Entity.Enum.User_Enum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class User_DTO {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    public User_DTO(String fullName, String email, String password, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    public User_DTO() {

    }
}
