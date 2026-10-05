package com.bank.service;

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
}
