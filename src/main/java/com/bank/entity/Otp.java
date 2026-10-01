package com.bank.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Otp {
	
	@Id
	private int otpId;
	private String email;
	private String otp;
	
	public Otp() {
		super();
	}

	public Otp(int otpId, String email, String otp) {
		super();
		this.otpId = otpId;
		this.email = email;
		this.otp = otp;
	}

	public int getOtpId() {
		return otpId;
	}

	public void setOtpId(int otpId) {
		this.otpId = otpId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "Otp [otpId=" + otpId + ", email=" + email + ", otp=" + otp + "]";
	}
	
	
	

}
