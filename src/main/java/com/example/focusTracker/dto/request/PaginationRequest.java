package com.example.focusTracker.dto.request;

import lombok.Data;

@Data
public class PaginationRequest {
    private int page = 1;
    private int size = 10;
}
