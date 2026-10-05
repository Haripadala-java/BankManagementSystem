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
}
