package com.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.service.AdminLoanService;
import com.bank.service.CustomerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	
	private final CustomerService service;
	
	private final AdminLoanService loanService;
	
	
	
	
	
	
	public AdminController(CustomerService service, AdminLoanService loanService) {
		super();
		this.service = service;
		this.loanService = loanService;
	}

	private static final String ADMIN_USERNAME = "admim";
	private static final String ADMIN_PASSWORD = "admin123";
	
	@GetMapping("/admin/login")
	public String adminLoginPage() {
		return "admin-login";
	}
	
	@PostMapping("/admin/login")
	public String adminLogin(String username, String password, HttpSession session, Model model) {
		
		if(username == null || username.isBlank() || password==null || password.isBlank()) {
			model.addAttribute("error", "fill username and password");
			return "admin-login";
		}
		
		if(ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
			session.setAttribute("ADMIN_LOGGED_IN", true);
			return "redirect:/admin/dashboard";
		}
		
		model.addAttribute("error", "Invalid admin credentials");
        return "admin-login";
	}
	
	@GetMapping("/admin/dashboard")
	public String adminDashboard(HttpSession session) {
		Boolean loggedIn = (Boolean) session.getAttribute("ADMIN_LOGGED_IN");
		
		if(loggedIn == null || !loggedIn) {
			return "redirect:/admin/login";
		}
		return "admin-dashboard";
	}
	
	  @GetMapping("/admin/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();
	        return "redirect:/admin/login";
	    }
	
	@GetMapping("/admin/create-customer")
	public String createCustomerPage(HttpSession session , Model model) {
		
		Boolean loggedIn =  (Boolean) session.getAttribute("ADMIN_LOGGED_IN");
		
		if(loggedIn== null || !loggedIn) {
			return "redirect:/admin/login";
		}
		return "admin-create-customer";
	}
	
	@GetMapping("/admin/create-customer")
	public String createCustomer(@RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("mobile") String mobile,
            HttpSession session,
            Model model) {
	    
		Boolean loggedIn = (Boolean) session.getAttribute("ADMIN_LOGGED_IN");
		
		if(loggedIn==null || !loggedIn) {
			return"redirect:/admin/login";
		}
		
		String result = service.createCustomer(fullName, email, mobile);
		
		 model.addAttribute("message", result);
	        return "admin-create-customer";
	}
	
	@GetMapping("/admin/loans")
	public String viewloans(HttpSession session , Model model) {
		Boolean login = (Boolean) session.getAttribute("ADMIN_LOGGED_IN");
		if(login==null || !login) {
			return "redirect:/admin/login";
		}
		model.addAttribute("loans", loanService.getPendingLoans());
		
	   return "admin-loans";
	}
	
	@PostMapping("/admin/loan/approve")
	public String approveLoan(@RequestParam Long loanId , HttpSession session) {
		
		loanService.approveLoan(loanId);
		return "redirect:/admin/loans";
	}
	
	 @PostMapping("/admin/loan/reject")
	public String rejectLoan(@RequestParam Long loanId, HttpSession session) {
		
		loanService.rejectLoan(loanId);
		return "redirect:/admin/loans";
	}
	
	
	
	
	
	

}
