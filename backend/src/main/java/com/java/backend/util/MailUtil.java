package com.java.backend.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class MailUtil {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendTokenMail(String to, String subject, String name, String token)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("token", token);
        String html = templateEngine.process("email-token.html", context);

        helper.setTo(to);
        helper.setText(html, true);
        helper.setSubject(subject);
        helper.setFrom("spring.sendmail.demo.63@gmail.com");

        mailSender.send(message);
    }
}
