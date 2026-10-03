package com.bank.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "loans")
public class Loan {
	   
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String accountNumber;

	    private String loanType;     // HOME / PERSONAL / EDUCATION

	    private double amount;

	    private int tenureMonths;

	    private double interestRate;

	    private String status;       // PENDING / APPROVED / REJECTED

	    private LocalDate appliedDate;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}

		public String getLoanType() {
			return loanType;
		}

		public void setLoanType(String loanType) {
			this.loanType = loanType;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public int getTenureMonths() {
			return tenureMonths;
		}

		public void setTenureMonths(int tenureMonths) {
			this.tenureMonths = tenureMonths;
		}

		public double getInterestRate() {
			return interestRate;
		}

		public void setInterestRate(double interestRate) {
			this.interestRate = interestRate;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public LocalDate getAppliedDate() {
			return appliedDate;
		}

		public void setAppliedDate(LocalDate appliedDate) {
			this.appliedDate = appliedDate;
		}

}
