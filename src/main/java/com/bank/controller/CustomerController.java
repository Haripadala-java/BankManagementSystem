package com.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.service.CustomerLoginService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {
	
	public CustomerLoginService customerLoginService;

    @GetMapping("/customer/login")
    public String customerLoginPage() {
        return "customer-login";
    }
    
    @PostMapping("/customer/login")
    public String customerLogin(@RequestParam String username, @RequestParam String password , HttpSession session, Model model ) {
    	String result = customerLoginService.validateLogin(username, password);
    	if("temp".equals(result)) {
    		session.setAttribute("us", username);
    		return "redirect:/customer/reset-password";
    	}
    	if("success".equals(result)) {
    		
    		return "redirect:/customer/otp";
    	}
    }
}
