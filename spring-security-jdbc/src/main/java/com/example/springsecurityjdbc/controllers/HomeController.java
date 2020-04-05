package com.example.springsecurityjdbc.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "Welcome home";
    }
    @RequestMapping("/user")
    public String user(){
        return "welcome user";
    }
    @RequestMapping("/admin")
    public String admin(){
        return "welcome Admin";
    }


}
