package com.example.focusTracker.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserProfile {
    private long userId;
    private MultipartFile image;

}
