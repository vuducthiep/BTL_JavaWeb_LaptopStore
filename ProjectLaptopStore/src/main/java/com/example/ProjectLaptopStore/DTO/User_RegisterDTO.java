package com.example.ProjectLaptopStore.DTO;

import com.example.ProjectLaptopStore.Entity.Enum.User_Enum;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class User_RegisterDTO {
    private int UserID;

    @NotNull
    private String fullName;

    private String email;
    @NotNull
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull
    @Size(max = 10,min = 10)
    private String phoneNumber;

    private User_Enum userType;
    private LocalDateTime registrationDate ;
    public User_RegisterDTO(){
    }

    public LocalDateTime getRegistrationDate() {
        return   LocalDateTime.now();
    }
}
