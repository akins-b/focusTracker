package com.example.focusTracker.service;

import com.example.focusTracker.dto.request.CreateFocusSession;
import com.example.focusTracker.dto.request.PaginationRequest;
import com.example.focusTracker.dto.response.FocusSessionDTO;
import com.example.focusTracker.entity.FocusSession;
import com.example.focusTracker.entity.User;
import com.example.focusTracker.repo.FocusSessionRepo;
import com.example.focusTracker.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FocusSessionService {
    private final UserRepo userRepo;
    private final FocusSessionRepo repo;

    public Page<FocusSessionDTO> getAllFocusSessionsByUser(PaginationRequest request, long userId){
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        Object principal = authentication.getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        if (!user.getEmail().equals(email)){
            throw new IllegalArgumentException("Can't access this user's history");
        }

        Pageable pageable = PageRequest.of(request.getPage()-1, request.getSize());
        Page<FocusSession> focusSessions = repo.findAllByUser(user, pageable);

        Page<FocusSessionDTO> focusSessionDTOs = focusSessions.map(focusSession -> {
            FocusSessionDTO dto = new FocusSessionDTO();
            dto.setId(focusSession.getId());
            dto.setUserId(focusSession.getUser().getId());
            dto.setFocusDuration(focusSession.getFocusDuration());
            dto.setCreatedAt(focusSession.getCreatedAt());
            return dto;
        });
        return focusSessionDTOs;
    }


    public void startFocusSession(CreateFocusSession request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.getEmail().equals(email)){
            throw new IllegalArgumentException("Can't access this user's history");
        }

        FocusSession focusSession = new FocusSession();
        focusSession.setFocusDuration(request.getFocusDuration());
        focusSession.setUser(user);
        focusSession.setCreatedAt(request.getCreatedAt());
        focusSession.setEndedAt(request.getCreatedAt().plusMinutes(request.getFocusDuration()));

        repo.save(focusSession);
    }
}
