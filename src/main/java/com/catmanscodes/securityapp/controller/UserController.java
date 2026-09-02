package com.catmanscodes.securityapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/hi")
    public String getHi() {
        return "Hi there!";
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello there!";
    }


}
