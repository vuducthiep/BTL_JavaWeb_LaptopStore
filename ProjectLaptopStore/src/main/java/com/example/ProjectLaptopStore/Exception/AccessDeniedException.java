package com.example.ProjectLaptopStore.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class AccessDeniedException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }

}
