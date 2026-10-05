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
}
