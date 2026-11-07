package com.bank.config;

import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	
	public JavaMailSender javamails() {
		JavaMailSenderImpl send = new JavaMailSenderImpl();
		send.setHost("smtp.gmail.com");
		send.setPort(587);
		send.setUsername("odjk fyqe fqhb ycmz");
		
		Properties prop = send.getJavaMailProperties();
//		to send the mails we use smtp protocal
		prop.put("mail.transport.protocol", "smtp");
//		username and password verifications are compulsory
		prop.put("mail.smtp.auth", "true");
//		for security tls layer is turned on
		prop.put("mail.smtp.starttls.enable", "true");
//		if any errors identified we can see those on console debug logs
		prop.put("mail.debug", "true");

        return send;
		
	}

}
