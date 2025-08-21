package com.example.focusTracker.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        String contentType = file.getContentType();

        if(contentType == null || !isImage(contentType)){
            throw new IllegalArgumentException("Only JPG, PNG, or WEPG files are allowed.");
        }
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
        return (String) uploadResult.get("secure_url");
    }

    public boolean isImage(String contentType){
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/webp");
    }
}
