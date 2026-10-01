package com.bank.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Loan {
	
	@Id
	private int loanId;
	private int customerId;
	private double amount;
	private String loanType;
	private String status;
	public Loan() {
		super();
	}
	public Loan(int loanId, int customerId, double amount, String loanType, String status) {
		super();
		this.loanId = loanId;
		this.customerId = customerId;
		this.amount = amount;
		this.loanType = loanType;
		this.status = status;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", customerId=" + customerId + ", amount=" + amount + ", loanType=" + loanType
				+ ", status=" + status + "]";
	}
	
	

}
