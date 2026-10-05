package com.bank.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final JavaMailSender mailsender;
	
	
	public EmailService(JavaMailSender mailsender) {
		super();
		this.mailsender = mailsender;
	}


	public void sendMail(String email, String subject, String body) {
		try {
			SimpleMailMessage mess = new SimpleMailMessage();
			mess.setTo(email);
			mess.setSubject(subject);
			mess.setText(body);
			
		mailsender.send(mess);
		} catch (Exception e) {
			System.out.println("failed to send mail"+ e.getMessage());
		}
	}
}
