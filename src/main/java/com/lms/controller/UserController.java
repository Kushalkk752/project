package com.lms.controller;

import com.lms.entity.AppUser;
import com.lms.payload.AppUserDto;
import com.lms.repository.AppUserRepository;
import com.lms.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;
    private AppUserRepository appUserRepository;

    public UserController(UserService userService, AppUserRepository appUserRepository) {
        this.userService = userService;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(
            @RequestBody AppUserDto dto,
            @Valid BindingResult result
            ){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.OK);
        }
        Optional<AppUser> opUsername = appUserRepository.findByUsername(dto.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        Optional<AppUser> opEmail = appUserRepository.findByEmail(dto.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        String encodedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(4));
        dto.setPassword(encodedPassword);
        AppUserDto appUserDto = userService.createUser(dto);
        return new ResponseEntity<>(appUserDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AppUserDto>> getAllUsers(){
        List<AppUserDto> dtos = userService.getAllUsers();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(
            @RequestParam long id
    ){
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUserById(
            @PathVariable long id,
            @RequestBody AppUserDto dto){
        AppUser appUser = userService.updateUserById(id, dto);
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> findUserByTheId(
            @PathVariable long id
    ){
        AppUserDto appUserDto = userService.findUserByTheId(id);
        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

}
