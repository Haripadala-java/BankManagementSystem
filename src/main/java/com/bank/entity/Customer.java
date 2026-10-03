package com.bank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true)
	private String phone;
	
	@Column(nullable = false, unique = true)
	private String accountNumber;
	
	@Column(nullable = true)
	private String tempPassword;
	
	@Column(nullable = false)
    private boolean tempPasswordActive;

    @Column(nullable = false)
    private boolean active;
    
    @Column(nullable = false)
    private double balance = 0.0;
    
    
	public Customer() {
		super();
	}
	
	public Customer(int customerId, String name, String email, String password, String phone, String accountNumber,
			String tempPassword, boolean tempPasswordActive, boolean active, double balance) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.accountNumber = accountNumber;
		this.tempPassword = tempPassword;
		this.tempPasswordActive = tempPasswordActive;
		this.active = active;
		this.balance = balance;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public boolean isTempPasswordActive() {
		return tempPasswordActive;
	}

	public void setTempPasswordActive(boolean tempPasswordActive) {
		this.tempPasswordActive = tempPasswordActive;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", phone=" + phone + ", accountNumber=" + accountNumber + ", tempPassword=" + tempPassword
				+ ", tempPasswordActive=" + tempPasswordActive + ", active=" + active + ", balance=" + balance + "]";
	}

	

	

}
