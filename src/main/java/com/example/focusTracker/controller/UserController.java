package com.example.focusTracker.controller;

import com.example.focusTracker.dto.request.LoginRequest;
import com.example.focusTracker.dto.request.RegistrationRequest;
import com.example.focusTracker.dto.request.UpdateUserProfile;
import com.example.focusTracker.dto.response.AuthResponse;
import com.example.focusTracker.dto.response.UserProfileResponse;
import com.example.focusTracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegistrationRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PatchMapping(value = "/edit-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserProfileResponse> editUserProfile(@ModelAttribute UpdateUserProfile request) {
        UserProfileResponse updatedUser = service.editUserProfile(request);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email){
        service.forgotPassword(email);
        return ResponseEntity.ok("Password has been changed");
    }
}
