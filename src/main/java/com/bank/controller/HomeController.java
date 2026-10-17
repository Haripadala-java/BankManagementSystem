package com.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "forward:/index.jsp";
    }
    
    @GetMapping("/home")
    public String homePage() {
        return "forward:/index.jsp";
    }
}