package com.example.ProjectLaptopStore.Repository;

import com.example.ProjectLaptopStore.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity,Integer> {
}
