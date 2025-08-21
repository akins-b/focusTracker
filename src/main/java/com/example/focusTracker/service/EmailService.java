package com.example.focusTracker.service;

import com.example.focusTracker.dto.request.BrevoEmailBodyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final WebClient brevoWebClient;

    public void sendEmail(String toEmail, String firstName, String lastName, String data){
//        BrevoEmailBodyRequest request = new BrevoEmailBodyRequest();
//        request.setSender(new BrevoEmailBodyRequest.Sender("akinsboms@gmail.com", "Boma"));
//        request.setRecipient(new BrevoEmailBodyRequest.Recipient(toEmail, firstName + " " + lastName));
//        request.setSubject("Account Verification");
//        request.setHtmlContent("<p>Hi " + firstName + " " + lastName + ",<br>This is your new password:<br>" + data  + "</p>");

        BrevoEmailBodyRequest emailRequest = new BrevoEmailBodyRequest();
        emailRequest.setSender(new BrevoEmailBodyRequest.Sender("Luto", "akinsboms@gmail.com"));
        emailRequest.setRecipient(List.of(new BrevoEmailBodyRequest.Recipient(toEmail, firstName + " " + lastName)));
        emailRequest.setSubject("Verification Code");
        emailRequest.setHtmlContent("<p>Hi " + firstName + " " + lastName + ",<br>This is your new password:<br>" + data  + "</p>");

        brevoWebClient.post()
                .uri("/smtp/email")
                .bodyValue(emailRequest)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error ->
                        System.err.println("Error sending email: " +error.getMessage()))
                .subscribe();

    }
}
