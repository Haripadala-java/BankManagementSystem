package com.bank.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.entity.Customer;
import com.bank.entity.Otp;
import com.bank.repository.CustomerRepository;
import com.bank.repository.OtpRepository;

@Service
public class OtpService {
	
	public OtpRepository repository;
	public CustomerRepository cRepository;
	public EmailService emailService;

	@Transactional
	public void generateOtp(String accountNumber) {
		if(accountNumber==null || accountNumber.isBlank()) {
			return;
		}
		String otp = String.valueOf((int) Math.random() * 900000 + 100000);
		
		Otp ot=new Otp();
		ot.setAccountNumber(accountNumber);
		ot.setOtp(otp);
		
		repository.save(ot);
		
		Customer customer = cRepository.findByAcountnmber(accountNumber);
		
		if(customer==null) {
			return;
		}
		
		String subject = "Bank Otp Verification";
		String body = "dear "+ customer.getName() + "your Otp for verification is"+ otp;
		emailService.sendMail(customer.getEmail(), subject, body);
		
	}	
	
	public boolean verifyOtp(String accounnumber,String otp) {
		if(accounnumber==null || otp==null) 
			return false;
		
		Otp ot= repository.findValidOtp(accounnumber, otp);
		
		if(ot==null)
			return false;
		
		if(ot.getExpiryTime().isBefore(LocalDateTime.now())) {
			repository.delete(ot);
			return false;
		}
		
		repository.delete(ot);
		return true;
	}
}
