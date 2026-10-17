package com.bank.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.entity.Customer;
import com.bank.entity.Transaction;
import com.bank.repository.CustomerRepository;
import com.bank.repository.TransactionRepository;

@Service
public class CustomerDashboard {
	
	public CustomerRepository cusreposRepository;
	
	public TransactionRepository trasrepository;
	
	public Customer getCustomerByAccount(String accountNumber) {
		
		return cusreposRepository.findByAcountnmber(accountNumber);
		
	}
	
	public List<Transaction> ministatement(String accno){
		
		List<Transaction> list= trasrepository.findRecentTransactions(accno);
		return list.size() > 10 ? list.subList(0, 10) : list ;
		
	}
	
	public List<Transaction> getFilteredStatement(String accno, Integer month , Integer year){
		LocalDateTime start;
		LocalDateTime end;
		
		if(month!=null) {
			start = LocalDate.of(year, month, 1).atStartOfDay();
			end   = start.plusMonths(1).minusSeconds(1);
		}
		else {
			start = LocalDate.of(year, 1, 1).atStartOfDay();
			end   = LocalDate.of(year, 12, 31).atTime(23, 59, 59);
		}
		
		List<Transaction> list= trasrepository.findByDateRange(accno, start, end);
		
		for(Transaction tx : list) {
			if(accno.equals(tx.getFromAccount())) {
				tx.setType("DEBIT");
			}
			else {
				tx.setType("CREDIT");
			}
		}
		
		
	
		
		return list;
	}

}
