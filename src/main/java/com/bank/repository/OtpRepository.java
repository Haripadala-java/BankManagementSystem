package com.bank.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bank.entity.Otp;

@Repository
public class OtpRepository {
	
	@PersistenceContext
	public EntityManager entityManager;

	@Transactional
	public void save(Otp otp) {
		entityManager.persist(otp);
	}
	
	public Otp findValidOtp(String accountNumber , String otp) {
		try {
			return entityManager.createQuery("SELECT c FROM OTP c WHERE c.accountNumber=:acc and c.otp=:ot", Otp.class)
			.setParameter("acc" , accountNumber)
			.setParameter("ot", otp)
			.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@Transactional
	public void delete(Otp otp) {
		entityManager.remove(entityManager.contains(otp) ? otp : entityManager.merge(otp));
	}
}
