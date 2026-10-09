package com.bank.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bank.entity.Customer;

@Repository
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

	
	   public boolean findByEmail(String email) {
	        Long count = entityManager.createQuery(
	                "SELECT COUNT(c) FROM Customer c WHERE c.email = :email",
	                Long.class)
	                .setParameter("email", email)
	                .getSingleResult();

	        return count > 0;
	    }
	
	   public boolean findByMobile(String mobile) {
	        Long count = entityManager.createQuery(
	                "SELECT COUNT(c) FROM Customer c WHERE c.mobile = :mobile",
	                Long.class)
	                .setParameter("mobile", mobile)
	                .getSingleResult();

	        return count > 0;
	    }
	   
	   @Transactional
	   public void save(Customer customer) {
		   entityManager.persist(customer);
	   }
	   
	   
}
