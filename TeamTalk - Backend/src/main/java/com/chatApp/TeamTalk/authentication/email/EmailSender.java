package com.chatApp.TeamTalk.authentication.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String token) throws IOException, MessagingException {
    String verifyEmailHtml = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/verification.html")), StandardCharsets.UTF_8);
        var link = String.format("http://localhost:8080/api/v1/auth/verify?token=%s", token);
        verifyEmailHtml = verifyEmailHtml.replace("$link", link);
        MimeMessageHelper helper = new MimeMessageHelper(emailSender.createMimeMessage(), true, "UTF-8");
        helper.setFrom("tanmay.datey123@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(verifyEmailHtml, true);
        emailSender.send(helper.getMimeMessage());
    }


}
