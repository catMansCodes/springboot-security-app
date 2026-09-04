package com.catmanscodes.securityapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @GetMapping("/hi")
    public String getHi() {
        return "Hi there!";
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello there!";
    }

    @GetMapping("/profile")
    public Map<String, Object> getProfile(@AuthenticationPrincipal OAuth2User userProfile) {

        Map<String, Object> profile = new HashMap<>();

        profile.put("name", userProfile.getAttribute("name"));
        profile.put("email", userProfile.getAttribute("email"));
        profile.put("picture", userProfile.getAttribute("picture"));

        return profile;
    }

}
