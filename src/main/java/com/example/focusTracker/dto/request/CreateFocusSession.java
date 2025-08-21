package com.example.focusTracker.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateFocusSession {
    @NotNull(message = "Focus session duration cannot be null")
    private long focusDuration;
    private LocalDateTime createdAt = LocalDateTime.now();
}
