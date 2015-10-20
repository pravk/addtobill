package com.mantralabsglobal.addtobill.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

    @Value("${app.mail.protocol}")
    private String protocol;
    @Value("${app.mail.host}")
    private String host;
    @Value("${app.mail.port}")
    private int port;
    @Value("${app.mail.smtp.auth}")
    private boolean auth;
    @Value("${app.mail.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${app.mail.username}")
    private String username;
    @Value("${app.mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", auth);
        mailProperties.put("mail.smtp.starttls.enable", starttls);
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }
}