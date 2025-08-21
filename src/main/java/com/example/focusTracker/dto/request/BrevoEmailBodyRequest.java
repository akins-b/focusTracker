package com.example.focusTracker.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class BrevoEmailBodyRequest {
    private Sender sender;
    private String subject;
    private String htmlContent;
    private List<Recipient> recipient;


    @Data
    @AllArgsConstructor
    public static class Sender {
        String email;
        String name;
    }

    @Data
    @AllArgsConstructor
    public static class Recipient{
        String email;
        String name;
    }
}
