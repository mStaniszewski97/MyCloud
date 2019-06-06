package com.stanikov.demo.mail;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class FeedbackMailSender implements FeedbackSender {
    private JavaMailSenderImpl mailSender;

    public FeedbackMailSender(Environment environment) {
        mailSender = new JavaMailSenderImpl();

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.starttls.enable", true);
        mailProperties.put("mail.smtp.starttls.required", true);
        mailProperties.put("mail.smtp.debug", true);
        mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProperties.put("mail.smtp.socketFactory.fallback", false);

        mailSender.setHost(environment.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.parseInt(environment.getProperty("spring.mail.port")));
        mailSender.setUsername(environment.getProperty("spring.mail.username"));
        mailSender.setPassword(environment.getProperty("spring.mail.password"));
        mailSender.setJavaMailProperties(mailProperties);
    }

    @Override
    public void sendFeedback(String from, String name, String feedback) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("M.Staniszewski16@gmail.com");
        message.setSubject("New question from " + name);
        message.setText(feedback);
        message.setFrom(from);

        mailSender.send(message);
    }
}
