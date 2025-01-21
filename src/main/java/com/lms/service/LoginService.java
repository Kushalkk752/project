package com.lms.service;

import com.lms.entity.AppUser;
import com.lms.payload.LoginDto;
import com.lms.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private AppUserRepository appUserRepository;
    private JWTService jwtService;

    public LoginService(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    public String loginUser(LoginDto dto) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(dto.getUsername());
        if(opUsername.isPresent()){
            AppUser appUser = opUsername.get();
            if(BCrypt.checkpw(dto.getPassword(), appUser.getPassword())){
                String token = jwtService.generateToken(appUser.getUsername());
                return token;
            }
        }
        else{
            return null;
        }
        return null;
    }
}
