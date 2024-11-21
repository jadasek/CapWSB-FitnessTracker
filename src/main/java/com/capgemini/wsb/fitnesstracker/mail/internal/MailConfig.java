package com.capgemini.wsb.fitnesstracker.mail.internal;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
class MailConfig {

    @Bean(name = "trainingMailConfig")
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        // Replace these with your SMTP details
        mailSender.setHost("smtp.yourmailserver.com");
        mailSender.setPort(587);
        mailSender.setUsername("your-email@example.com");
        mailSender.setPassword("your-email-password");

        // Additional properties, if required
        mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
