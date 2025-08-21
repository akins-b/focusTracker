package com.example.focusTracker.dto.response;

import com.example.focusTracker.entity.User;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FocusSessionDTO {
    private long id;
    private long userId;
    private long focusDuration;
    private LocalDateTime createdAt;
}
