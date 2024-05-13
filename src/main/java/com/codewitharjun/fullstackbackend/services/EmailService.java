package com.codewitharjun.fullstackbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendEmail(String subject, String message, String to) {
        boolean f = false;
        try {
            MimeMessage m = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(m, true);
            
            helper.setSubject(subject);
            helper.setText(message, true);
            helper.setTo(to);
            
            javaMailSender.send(m);
            f = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
}

