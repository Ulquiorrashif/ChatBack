package com.example.chatback.controllers;



import com.example.chatback.entity.Users;
import com.example.chatback.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Controller
@AllArgsConstructor
@Data
@CrossOrigin("*")
public class MainController {
    private UserService userService;

    @GetMapping("/loginPage")
    public String login(){
        return "loginPage";
    }
    @GetMapping("/regPage")
    public String reg(){
        return "regPage";
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails){
        List<String> userList = userService.getAllUsers().stream().map(item->item.getName()).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                if (s.equals(userDetails.getUsername()))
                        return false;
                else
                    return true;
            }
        }).toList();
        model.addAttribute("userList", userList);
        model.addAttribute("me", userDetails.getUsername());
        return "index";
    }

}
