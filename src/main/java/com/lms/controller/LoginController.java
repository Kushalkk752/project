package com.lms.controller;

import com.lms.payload.LoginDto;
import com.lms.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    public ResponseEntity<LoginDto> LoginUser(
            @RequestBody LoginDto dto
    ){
        String token = loginService.loginUser(dto);
        if()
    }
}