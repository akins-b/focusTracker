package com.example.focusTracker.dto.response;

import lombok.Data;

@Data
public class UserProfileResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
}
