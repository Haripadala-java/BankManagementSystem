package com.bank.service;

import com.bank.repository.LoanRepository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.entity.Loan;

@Service
public class LoanService {
	
	private final LoanRepository loanRepository;

	public LoanService(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	public String applyloan(String accountno, String loantype, double amount, int tenure) {
		
	       if (accountno == null || accountno.isBlank())
	            return "Account number is required";

	        if (loantype == null || loantype.isBlank())
	            return "Loan type is required";

	        if (amount <= 0)
	            return "Loan amount must be greater than zero";
	        
	        // Minimum loan amount
	        if (amount < 10000)
	            return "Minimum loan amount is ₹10,000";
	        
	        if (tenure <= 0)
	            return "Tenure must be greater than zero";
	        
	        // Tenure validation (months)
	        if (tenure < 6)
	            return "Minimum tenure is 6 months";
	        
	        if (tenure > 360)
	            return "Maximum tenure is 360 months (30 years)";
	        
	        double maxamount = switch (loantype) {
			case "HOMELOAN" -> 500000;
			case  "EDUCATION" -> 1000000;
			case  "PERSONAL" -> 500000;
			default -> 0;
			
	        };
	        if(maxamount==0) 
	        	return "Invalid LoanType : choose Homeloan , Education , Personal";
	        if (amount > maxamount)
	            return "Maximum loan amount for " + loantype + " is ₹" + maxamount;
		
		
		
		Loan lo= new Loan();
		lo.setAmount(amount);
		lo.setLoanType(loantype);
		lo.setTenureMonths(tenure);
		lo.setAccountNumber(accountno);
		lo.setAppliedDate(LocalDate.now());
		lo.setStatus("PENDING");
		lo.setInterestRate(getInterestRate(loantype));
		
		loanRepository.save(lo);
		
		return "Loan applied successfully your application under Review";
	}
	
	public List<Loan> getLoans(String accNo) {
        return loanRepository.findByAccountNumber(accNo);
    }
	
    private double getInterestRate(String loanType) {
        return switch (loanType) {
            case "HOME" -> 8.5;
            case "EDUCATION" -> 6.5;
            default -> 12.0;
        };
    }
	

}
