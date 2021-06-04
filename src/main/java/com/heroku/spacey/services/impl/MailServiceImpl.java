package com.heroku.spacey.services.impl;

import com.heroku.spacey.services.MailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Objects;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class MailServiceImpl implements MailService {
    private final JavaMailSender emailSender;
    private final Environment environment;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String spaceyMail;

    @Autowired
    public MailServiceImpl(JavaMailSender emailSender, Environment environment, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.environment = environment;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        var message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(environment.getProperty(spaceyMail)));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @SneakyThrows
    @ExceptionHandler(MessagingException.class)
    @Override
    public void sendSimpleMessageWithTemplate(String to, String subject, String text) {
        log.info(to);
        log.info(subject);
        log.info(text);

        var message = emailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);
        helper.setFrom(spaceyMail);
        helper.setTo(to);
        helper.setSubject(subject);

        var context = new Context();
        context.setVariable("text", text);
        String htmlContent = templateEngine.process("email", context);
        helper.setText(htmlContent, true);
        emailSender.send(message);
    }
}
