package com.lms.service;

import com.lms.entity.AppUser;
import com.lms.exception.ResourceNotFoundException;
import com.lms.payload.AppUserDto;
import com.lms.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private AppUserRepository appUserRepository;
    private ModelMapper modelMapper;

    public UserService(AppUserRepository appUserRepository, ModelMapper modelMapper) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
    }

    public AppUserDto createUser(AppUserDto dto) {
        AppUser user = mapToEntity(dto);
        AppUser savedUser = appUserRepository.save(user);
        AppUserDto appUserDto = mapToDto(savedUser);
        return appUserDto;
    }

    private AppUserDto mapToDto(AppUser savedUser) {
        AppUserDto dto = modelMapper.map(savedUser, AppUserDto.class);
        return dto;
    }

    private AppUser mapToEntity(AppUserDto dto) {
        AppUser user = modelMapper.map(dto, AppUser.class);
        return user;
    }

    public List<AppUserDto> getAllUsers() {
        List<AppUser> users = appUserRepository.findAll();
        List<AppUserDto> dtos = users.stream().map(r -> mapToDto(r)).collect(Collectors.toList());
        return dtos;
    }

    public void deleteUserById(long id) {
        appUserRepository.deleteById(id);
    }

    public AppUser updateUserById(long id, AppUserDto dto) {
        AppUser appUser = appUserRepository.findById(id).get();
        appUser.setName(dto.getName());
        appUser.setEmail(dto.getEmail());
        appUser.setUsername(dto.getUsername());
        String encodedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(4));
        appUser.setPassword(encodedPassword);
        AppUser save = appUserRepository.save(appUser);
        return save;
    }

    public AppUserDto findUserByTheId(long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Record not found")
        );
        AppUserDto dto = mapToDto(appUser);
        return dto;
    }
}
