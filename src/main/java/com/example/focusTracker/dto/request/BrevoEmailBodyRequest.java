package com.example.focusTracker.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class BrevoEmailBodyRequest {
    private Sender sender;
    private List<Recipient> to;
    private String subject;
    private String htmlContent;

    @Data
    @AllArgsConstructor
    public static class Sender {
        private String email;
        private String name;
    }

    @Data
    @AllArgsConstructor
    public static class Recipient {
        private String email;
        private String name;
    }
}
