package com.lms.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserDto {
    @Size(min = 2, message = "minimum should be having 2 letters")
    private String name;
    @Email
    private String email;
    @Size(min = 2, message = "minimum should be having 2 letters")
    private String username;
    @Size(min = 8, message = "minimum should be having 8 characters " +
            "and should contain atleast one Uppercase, lowercase and one specialCharacter")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
