package com.bank.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Transaction {
	
	@Id
	private int transactionId;
	private int customerId;
	private String transactionType;
	private double amount;
	private String recipientAccount;
	private String transactionDate;
	private String status;
	
	public Transaction() {
		super();
	}

	public Transaction(int transactionId, int customerId, String transactionType, double amount,
			String recipientAccount, String transactionDate, String status) {
		super();
		this.transactionId = transactionId;
		this.customerId = customerId;
		this.transactionType = transactionType;
		this.amount = amount;
		this.recipientAccount = recipientAccount;
		this.transactionDate = transactionDate;
		this.status = status;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRecipientAccount() {
		return recipientAccount;
	}

	public void setRecipientAccount(String recipientAccount) {
		this.recipientAccount = recipientAccount;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", customerId=" + customerId + ", transactionType="
				+ transactionType + ", amount=" + amount + ", recipientAccount=" + recipientAccount
				+ ", transactionDate=" + transactionDate + ", status=" + status + "]";
	}
	
	
	

}
