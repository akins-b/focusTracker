package com.example.focusTracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String token;

    public AuthResponse(String message){
        this.message = message;
    }
}
