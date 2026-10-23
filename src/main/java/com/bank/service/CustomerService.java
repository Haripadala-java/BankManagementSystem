package com.bank.service;

import org.springframework.stereotype.Service;

import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import com.bank.util.AccountUtil;

@Service
public class CustomerService {
	
	private final CustomerRepository repository;
	
	private final EmailService emailService;
	
	
	
	public CustomerService(CustomerRepository repository, EmailService emailService) {
		
		this.repository = repository;
		this.emailService = emailService;
	}



	public String createCustomer(String name, String email, String mobile) {
		if(name==null || name.isBlank()) 
			return"name required";
		
//		email
		
		if(email==null || email.isBlank())
			return "email required";
		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
	            return "Invalid email format";
		
//		mobile
		
		if(mobile==null || mobile.isBlank())
			return "mobile number required";
		 if (!mobile.matches("^[0-9]{10}$"))
	            return "Mobile number must be 10 digits";
		 
//		 checking
		
		if(repository.findByEmail(email)) {
			return "email already exists try another";
		}
		
		if(repository.findByMobile(mobile)) {
			return "mobile already exists try another";
		}
		
//		create customer
		
		Customer cus = new Customer();
		cus.setName(name);
		cus.setEmail(email);
		cus.setPhone(mobile);
		cus.setAccountNumber(AccountUtil.generateAccountNumber());
		cus.setTempPassword(AccountUtil.tempPassword());
		cus.setTempPasswordActive(true);
		cus.setBalance(0.0);
		cus.setPassword("");
		
		repository.save(cus);
		
		 String subject = "Your Smart Bank Account Details";
		
		String body = "dear" + cus.getName() + 
				"your account created successfully your accountnumber :" + 
				cus.getAccountNumber()+ "and your tempPassword :" + cus.getTempPassword() +
				"please login with your tempPassword and reset your new password Regards BankManagement";
		
	     emailService.sendMail(cus.getEmail(), subject, body);
	     
	     return "Account created successfully";
		
		 
		
	}

	

}
