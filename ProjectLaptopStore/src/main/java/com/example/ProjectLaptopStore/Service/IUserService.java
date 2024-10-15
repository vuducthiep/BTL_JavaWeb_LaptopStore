package com.example.ProjectLaptopStore.Service;

import com.example.ProjectLaptopStore.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserService extends JpaRepository<UserEntity, Integer> {
}
