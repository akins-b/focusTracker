package com.example.focusTracker.controller;

import com.example.focusTracker.dto.request.CreateFocusSession;
import com.example.focusTracker.dto.request.PaginationRequest;
import com.example.focusTracker.dto.request.UpdateUserProfile;
import com.example.focusTracker.dto.response.FocusSessionDTO;
import com.example.focusTracker.entity.FocusSession;
import com.example.focusTracker.service.FocusSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/focus")
@RequiredArgsConstructor
public class FocusSessionController {
    private final FocusSessionService service;

    @GetMapping("/all/{userId}")
    public ResponseEntity<Page<FocusSessionDTO>> getAllFocusSessions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable long userId) {
        PaginationRequest request = new PaginationRequest();
        request.setPage(page);
        request.setSize(size);
        return ResponseEntity.ok(service.getAllFocusSessionsByUser(request, userId));
    }

    @PostMapping("/start")
    public ResponseEntity<?> startFocusSession(@Valid @RequestBody CreateFocusSession request) {
        service.startFocusSession(request);
        return ResponseEntity.ok("Focus session started successfully");
    }
}
