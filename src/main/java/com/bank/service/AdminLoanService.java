package com.bank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.entity.Customer;
import com.bank.entity.Loan;
import com.bank.repository.CustomerRepository;
import com.bank.repository.LoanRepository;

@Service
public class AdminLoanService {
	
	public LoanRepository repository;
	public CustomerRepository cusreRepository;
	
	public List<Loan> getPendingLoans(){
		return repository.findByStatus("PENDING");
	}
	
	public String approveLoan(Long loanId) {
		
		if(loanId == null)
			return "invalid loanId";
		
		Loan loan = repository.findById(loanId).orElse(null);
		
		if(loan==null)
			return "loan not found";
		
		if(!"PENDING".equals(loan.getStatus()))
			return" loan Already Processed";
		
		Customer custoomer = cusreRepository.findByAcountnmber(loan.getAccountNumber());
		
		if(custoomer==null) {
			return"customer not found";
		}
		if(!custoomer.isActive()) {
			return"customer account is not active";
		}
		
		
		
		loan.setStatus("APPROVED");
		custoomer.setBalance(custoomer.getBalance()+loan.getAmount());
		
		cusreRepository.save(custoomer);
		repository.save(loan);
		
		return" loan amount approved succesfully";
	}
	
	public String rejectLoan(Long loanId) {
		if(loanId==null)
			return "invalid loanId";
		Loan loan = repository.findById(loanId).orElse(null);
		if(loan==null) {
			return "loan not found";
		}
		if(!"PENDING".equals(loan.getStatus())) {
			return"loan already processed";
		}
		Customer cus= cusreRepository.findByAcountnmber(loan.getAccountNumber());
		
		if(cus==null) {
			return "customer not found";
		}
		if(!cus.isActive()) {
			return"customer is not active";
		}
		
		loan.setStatus("REJECTED");
		
		repository.save(loan);
		
		return "loan Rejected";
	}

}
