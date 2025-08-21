package com.example.focusTracker.repo;

import com.example.focusTracker.entity.FocusSession;
import com.example.focusTracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FocusSessionRepo extends JpaRepository<FocusSession, Long> {
    Page<FocusSession> findAllByUser(User suer, Pageable pageable);
}
