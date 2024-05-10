package com.example.chatback.controllers;

import com.example.chatback.entity.Chat;
import com.example.chatback.entity.Message;
import com.example.chatback.entity.UserDto;
import com.example.chatback.entity.Users;
import com.example.chatback.services.ChatService;
import com.example.chatback.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@Data
@RequestMapping("/api/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/create")
    public Long createUser(
            String name
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);
        if (principal instanceof UserDto) {
            UserDto userDetails = (UserDto) principal;
            System.out.println(
                    userDetails.getName()
                            +" TTTT "+
                            name
            );
            chatService.create(userDetails.getName(),name);
            return chatService.getDataFromDatabase(userDetails.getName(),name).get(0).getId();
        }
        System.out.println("Не достал аутентифицированного пользователя");
        return null;
    }
    @GetMapping("/show")
    public List<Chat> show(String me, String name2  ) {

        return chatService.getDataFromDatabase(me, name2);
    }
    @GetMapping("/get/message")
    public List<Message>  getMessageByChat(Long id  ) {
            System.out.println(id +" "+"get");
        return chatService.geMessageByChat(id);
    }
}
