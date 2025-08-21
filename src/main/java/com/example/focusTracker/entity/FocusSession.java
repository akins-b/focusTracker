package com.example.focusTracker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class FocusSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    private User user;

    private long focusDuration;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;

}
