package com.engineerindigest.demo_project.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class AuthRequest {

    private String username;
    private String password;
}
