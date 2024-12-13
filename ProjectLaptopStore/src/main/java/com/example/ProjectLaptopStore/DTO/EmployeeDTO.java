package com.example.ProjectLaptopStore.DTO;

import com.example.ProjectLaptopStore.Entity.Enum.Status_Enum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EmployeeDTO {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Integer employeeId;
    private String status;
}
