package com.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.service.CustomerLoginService;
import com.bank.service.OtpService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {
	
	public CustomerLoginService customerLoginService;
	public OtpService otpService;

//	1) get customer login details 
	
    @GetMapping("/customer/login")
    public String customerLoginPage() {
        return "customer-login";
    }
    
    @PostMapping("/customer/login")
    public String customerLogin(
            @RequestParam("accountNumber") String accountNumber,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        String result =
                customerLoginService.validateLogin(accountNumber, password);

        if ("RESET_REQUIRED".equals(result)) {
            session.setAttribute("RESET_ACC", accountNumber);
            return "redirect:/customer/reset-password";
        }

        if ("SUCCESS".equals(result)) {
            session.setAttribute("OTP_ACC", accountNumber);
            otpService.generateOtp(accountNumber);
            return "redirect:/customer/otp";
        }

        model.addAttribute("error", result);
        return "customer-login";
    }
    
//    2) reset the password
    
    @GetMapping("customer/reset-password")
    public String resetPage() {
    	return "reset-password";
    }
    
    @PostMapping("customer/reset-password")
    public String resetPassword(@RequestParam("newPassword") String password, HttpSession session, Model model) {
    	
    	String accountNumber = (String) session.getAttribute("RESET_ACC");
    	
    	String result = customerLoginService.resetPassword(accountNumber, password);
    	
    	if("SUCCESS".equals(result)) {
    		session.removeAttribute("RESET_ACC");
    		return "redirect:/customer/login";
    	}
    	model.addAttribute("error", result);
        return "reset-password";
    	
    }
    
//    3) customer otp verify
    
    @GetMapping("/customer/otp")
    public String otpPage() {
    	return "customer-otp";
    }
    
    @PostMapping("/customer/otp")
    public String verifyOtp(@RequestParam("otp") String otp, HttpSession session, Model model) {
    	
    	  String accountNumber =
                  (String) session.getAttribute("OTP_ACC");

          if (accountNumber == null) {
              return "redirect:/customer/login";
          }
          
          boolean valid =
                  otpService.verifyOtp(accountNumber, otp);

          if (!valid) {
              model.addAttribute("error", "Invalid or expired OTP");
              return "customer-otp";
          }
          
          session.removeAttribute("OTP_ACC");
          session.setAttribute("CUSTOMER_LOGGED_IN", true);
          session.setAttribute("LOGGED_IN_ACC", accountNumber);
          return "redirect:/customer/dashboard";
    }
    
    
    
}
