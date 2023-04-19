package com.chatApp.TeamTalk.authentication.auth;

import ch.qos.logback.core.model.Model;
import com.chatApp.TeamTalk.authentication.email.EmailSender;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final EmailSender emailSender;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterBody request) throws MessagingException, IOException {
        var user = service.register(request);
        var token = user.getToken();
        emailSender.sendEmail(request.getEmail(), "Verification Link", token);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
    return ResponseEntity.ok(service.authenticate(request));
    }



    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token, Model model) throws IOException {
        boolean isVerified = service.verifyEmail(token);
        if (isVerified) {
            String verified = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/verified.html")
            ), StandardCharsets.UTF_8);
            return verified;
        } else {
            String notVerified = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/AlreadyExists.html")
            ), StandardCharsets.UTF_8);
            return notVerified;

        }


    }

}
