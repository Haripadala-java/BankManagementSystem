package com.bank.controller;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.entity.Customer;
import com.bank.entity.Transaction;
import com.bank.service.CustomerDashboard;
import com.bank.service.CustomerLoginService;
import com.bank.service.FundTransferService;
import com.bank.service.LoanService;
import com.bank.service.OtpService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {
	
	public CustomerLoginService customerLoginService;
	public OtpService otpService;
	
	public CustomerDashboard cusDashboard;

	public FundTransferService traservice;
	
	public LoanService loanService;
	
    public CustomerController(CustomerLoginService customerLoginService, OtpService otpService,
			CustomerDashboard cusDashboard, FundTransferService traservice, LoanService loanService) {
		super();
		this.customerLoginService = customerLoginService;
		this.otpService = otpService;
		this.cusDashboard = cusDashboard;
		this.traservice = traservice;
		this.loanService = loanService;
	}
	
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
    
    @GetMapping("/customer/dashboard")
    public String customerDashboard(HttpSession session, Model model) {
    	
    	Boolean logIn = (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");
    	
    	if(logIn==null || !logIn) {
    		return "redirect:/customer/login";
    	}
    	
    	String accounumber = (String) session.getAttribute("LOGGED_IN_ACC");
    	
    	if(accounumber==null || accounumber.isBlank()) {
    		return "redirect:/customer/login";
    	}
    	
    	Customer customer = cusDashboard.getCustomerByAccount(accounumber);
    	
    	
        if (customer == null) {
            return "redirect:/customer/login";
        }
        
        model.addAttribute("customer", customer);
        return "customer-dashboard";
    	
    }
    
    @GetMapping("/customer/transfer")
    public String transferPage(HttpSession session) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
             return "redirect:/customer/login";
        }

        return "fund-transfer";
    }
    
    @PostMapping("/customer/transfer")
    public String fundTransfer(
            @RequestParam("toAccount") String toAccount,
            @RequestParam("amount") double amount,
            HttpSession session,
            Model model) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/customer/login";
        }

        String fromAccount =
                (String) session.getAttribute("LOGGED_IN_ACC");

        String result =
                traservice.transfer(fromAccount, toAccount, amount);

        model.addAttribute("message", result);

        return "fund-transfer"; // ✅ STAY HERE
    }
    
    @GetMapping("/customer/loan/apply")
    public String applyLoanPage(HttpSession session) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/customer/login";
        }

        return "customer-loan-apply";
    }
    
    @PostMapping("/customer/loan/apply")
    public String applyLoan( @RequestParam String loanType,
            @RequestParam double amount,
            @RequestParam int tenure,
            HttpSession session,
            Model model) {
    	
    	String accuntnum= (String) session.getAttribute("LOGGED_IN_ACC");
    	
    	String result =loanService.applyloan(accuntnum, loanType, amount, tenure);
    	
    	model.addAttribute("message", result);
    	  return "customer-loan-apply";
    	
    }
    
    @GetMapping("/customer/logout")
    public String logout(HttpSession session) {
    	if(session!=null) {
    	session.invalidate();
    	}
    	return "redirect:/customer/login";
    }
    
    @GetMapping("/customer/statement")
    public String miniStatement(HttpSession session , Model model) {
    	Boolean loggedIn = (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");
    	if(loggedIn==null || !loggedIn) {
    		return "redirect:/customer/login";
    	}
    	  String accNo =
                  (String) session.getAttribute("LOGGED_IN_ACC");
    	 List<Transaction> transactions= cusDashboard.ministatement(accNo);
    	  
    	 for(Transaction tx : transactions) {
    		 if(accNo.equals(tx.getFromAccount())) {
    			 tx.setType("DEBIT");
    		 }
    		 else {
    			 tx.setType("CREDIT");
    		 }
    	 }
    	  
    	  model.addAttribute("transacctions", transactions);
    	  return "customer-statement";
    }
    
    @GetMapping("/customer/statement/filter")
    public String filterStatement(
            @RequestParam(required = false) Integer month,
            @RequestParam Integer year,
            HttpSession session,
            Model model) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn)
            return "redirect:/customer/login";

        String accNo =
                (String) session.getAttribute("LOGGED_IN_ACC");

        List<Transaction> list =
                cusDashboard.getFilteredStatement(accNo, month, year);

        model.addAttribute("transactions", list);
        model.addAttribute("month", month);
        model.addAttribute("year", year);

        return "customer-statement";
    }
    
    @GetMapping("/customer/statement/download")
    public String downloadStatement(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            HttpSession session,
            HttpServletResponse response,
            Model model) throws Exception {

        // 🔐 LOGIN CHECK
        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/customer/login";
        }

        String accNo =
                (String) session.getAttribute("LOGGED_IN_ACC");

        if (accNo == null) {
            return "redirect:/customer/login";
        }

        // 📄 FETCH DATA (ALL / MONTHLY / YEARLY)
        List<Transaction> list;

        if (year != null) {
            // monthly OR yearly
            list = cusDashboard.getFilteredStatement(accNo, month, year);
        } else {
            // fallback → mini statement
            list = cusDashboard.ministatement(accNo);
        }

        if (list == null || list.isEmpty()) {
            model.addAttribute("transactions", list);
            model.addAttribute("error", "No transactions to download");
            return "customer-statement";
        }

        // 📥 CSV DOWNLOAD
        response.setContentType("text/csv");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=transaction_statement.csv"
        );

        PrintWriter writer = response.getWriter();
        writer.println("Date,Type,From,To,Amount");

        for (Transaction tx : list) {

            // ✅ DEBIT / CREDIT LOGIC (IMPORTANT)
            String displayType;
            if (accNo.equals(tx.getFromAccount())) {
                displayType = "DEBIT";
            } else {
                displayType = "CREDIT";
            }

            writer.println(
                    tx.getTransactionDate() + "," +
                    displayType + "," +
                    tx.getFromAccount() + "," +
                    tx.getToAccount() + "," +
                    tx.getAmount()
            );
        }

        writer.flush();
        return null; // ✅ REQUIRED FOR FILE DOWNLOAD
    }
    
    
    
    
}
