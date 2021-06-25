package com.heroku.spacey.services.impl;

import com.heroku.spacey.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final Environment environment;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String spaceyMail;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(environment.getProperty(spaceyMail)));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    @SneakyThrows
    @ExceptionHandler(MessagingException.class)
    public void sendSimpleMessageWithTemplate(String to, String subject, String text, String template) {
        log.info(to);
        log.info(subject);
        log.info(text);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(spaceyMail);
        helper.setTo(to);
        helper.setSubject(subject);

        Context context = new Context();
        context.setVariable("text", text);
        String htmlContent = templateEngine.process(template, context);
        helper.setText(htmlContent, true);
        emailSender.send(message);
    }
}
