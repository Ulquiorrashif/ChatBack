package com.example.chatback.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@AllArgsConstructor
@Data
@CrossOrigin("*")
public class TestControll {
    @GetMapping("/user")
    @ResponseBody
    public String getUserInfo(@AuthenticationPrincipal OAuth2User oauth2User) {
        return "Hello, " + oauth2User.getName();
    }
}
