package com.bank.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;



@Service
public class CustomerLoginService {
	
	public CustomerRepository customerRepository;

	 @Transactional(readOnly = true)
    public String validateLogin(String accountNumber, String password) {

        if (accountNumber == null || accountNumber.isBlank())
            return "Account number required";

        if (password == null || password.isBlank())
            return "Password required";

        Customer customer =
                customerRepository.findByAcountnmber(accountNumber);

        if (customer == null)
            return "Invalid account number";

        // First-time login using temp password
        if (customer.isTempPasswordActive()) {

            if (!password.equals(customer.getTempPassword()))
                return "Invalid temporary password";

            return "RESET_REQUIRED";
        }

        // Normal login
        if (!password.equals(customer.getPassword()))
            return "Invalid password";

        return "SUCCESS";
    }
	 
	 
	 
	   
	    
	    @PersistenceContext
	    private EntityManager entiManager;

	    @Transactional
	    public String resetPassword(String accountNumber, String newPassword) {

	        if (accountNumber == null || accountNumber.isBlank())
	            return "Account number is required";

	        if (newPassword == null || newPassword.length() < 4)
	            return "Password must be at least 4 characters";

	        Customer customer =
	                customerRepository.findByAcountnmber(accountNumber);

	        if (customer == null)
	            return "Customer not found";

	        customer.setPassword(newPassword);
	        customer.setTempPasswordActive(false);
	        customer.setTempPassword(null);

	        entiManager.merge(customer);
	        return "SUCCESS";
	    }
}
