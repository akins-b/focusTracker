package com.example.focusTracker.service;

import com.example.focusTracker.dto.request.LoginRequest;
import com.example.focusTracker.dto.request.RegistrationRequest;
import com.example.focusTracker.dto.request.UpdateUserProfile;
import com.example.focusTracker.dto.response.AuthResponse;
import com.example.focusTracker.dto.response.UserProfileResponse;
import com.example.focusTracker.entity.User;
import com.example.focusTracker.repo.UserRepo;
import com.example.focusTracker.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final AuthenticationProvider authenticationProvider;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final JwtService jwtService;
    private final EmailService emailService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper mapper;


    public AuthResponse register(RegistrationRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCreatedAt(request.getCreatedAt());
        userRepo.save(user);

        return new AuthResponse("An email has been sent for verification.");
    }


    public AuthResponse login(LoginRequest request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (authentication.isAuthenticated()){
            String token = jwtService.generateToken(user.getEmail());

            return new AuthResponse("Login successful", token);
        }
        else{
            return new AuthResponse("login failed", null);
        }

    }

    public void forgotPassword(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));

        String newPassword = UUID.randomUUID().toString();
        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);
        
        emailService.sendEmail(email, user.getFirstName(), user.getLastName(), newPassword);
    }

    public UserProfileResponse editUserProfile(UpdateUserProfile request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(()-> new EntityNotFoundException("User not found"));

        try {
            user.setImageUrl(cloudinaryService.uploadImage(request.getImage()));
        }
        catch (IOException e){
            throw new IllegalArgumentException("Error uploading image: " + e.getMessage());
        }
        user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(user);

        UserProfileResponse response = mapper.map(user, UserProfileResponse.class);
        return response;
    }
}
