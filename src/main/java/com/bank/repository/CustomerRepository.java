package com.bank.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bank.entity.Customer;

public class CustomerRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Customer findByAcountnmber(String accounmber) {
		 try {
	            return entityManager.createQuery(
	                    "SELECT c FROM Customer c WHERE c.accountNumber = :acc",
	                    Customer.class)
	                    .setParameter("acc", accounmber)
	                    .getSingleResult();
	        } catch (Exception e) {
	            return null;
	        }
	}

}
