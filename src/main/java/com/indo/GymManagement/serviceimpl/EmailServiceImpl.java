package com.indo.GymManagement.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.indo.GymManagement.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private  JavaMailSender mailSender = null;

    public void EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("You have requested to reset your password. Click the link below to reset your password:\n" + resetLink + "\n\nIf you did not request a password reset, please ignore this email.");
        mailSender.send(message);
    }

}
