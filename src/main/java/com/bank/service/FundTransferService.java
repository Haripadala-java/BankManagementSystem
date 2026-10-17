package com.bank.service;

import java.time.LocalDateTime;

import com.bank.entity.Customer;
import com.bank.entity.Transaction;
import com.bank.repository.CustomerRepository;
import com.bank.repository.TransactionRepository;

public class FundTransferService {
	
	public CustomerRepository repository;
	
	public TransactionRepository trarepRepository;
	
	public String transfer(String fromacc, String toacc, double amnt) {
		
        // Validation checks
        if (fromacc == null || toacc == null)
            return "Invalid account numbers";

        if (fromacc.equals(toacc))
            return "Cannot transfer to same account";

        if (amnt <= 0)
            return "Amount must be greater than zero";
        
        // Minimum transfer amount check
        if (amnt < 1)
            return "Minimum transfer amount is ₹1";

        Customer sender= repository.findByAcountnmber(fromacc);

        if (sender == null)
            return "Sender account not found";

        Customer receiver= repository.findByAcountnmber(toacc);

        if (receiver == null)
            return "Receiver account not found";
        
        // Check if receiver account is active
        if (!receiver.isActive())
            return "Receiver account is not active";

        if (sender.getBalance() < amnt)
            return "Insufficient balance";
		
		
		sender.setBalance(sender.getBalance()-amnt);
		receiver.setBalance(receiver.getBalance()+amnt);
		
		repository.save(sender);
		repository.save(receiver);
		
	
		Transaction tnx = new Transaction();
		tnx.setFromAccount(fromacc);
		tnx.setToAccount(toacc);
		tnx.setAmount(amnt);
		tnx.setType("Online transfer");
		tnx.setTransactionDate(LocalDateTime.now());
		
		trarepRepository.save(tnx);
		
		return"SUCCESS";
	
		
		
	}

}
