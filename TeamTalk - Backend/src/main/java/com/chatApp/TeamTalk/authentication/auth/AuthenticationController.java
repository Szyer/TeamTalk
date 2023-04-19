package com.chatApp.TeamTalk.authentication.auth;

import com.chatApp.TeamTalk.authentication.email.EmailSender;
import com.chatApp.TeamTalk.authentication.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final EmailSender emailSender;
    private final UserRepository repository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterBody request) {

        var user = service.register(request);
        var token = user.getToken();
        var link = String.format("%s/api/v1/auth/verify?token=%s", getBaseUrl(), token);
        emailSender.sendEmail(request.getEmail(), "Verification Link", "Please click the link to verify your account: " + link);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
    return ResponseEntity.ok(service.authenticate(request));
    }

    private String getBaseUrl() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String scheme = request.getScheme();
        String host = request.getHeader("host");
        return String.format("%s://%s", scheme, host);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        boolean isVerified = service.verifyEmail(token);
        if (isVerified) {
            return ResponseEntity.ok("Your email address has been verified.");
        } else {
            return ResponseEntity.badRequest().body("User already verified or token expired.");
        }
    }

}
