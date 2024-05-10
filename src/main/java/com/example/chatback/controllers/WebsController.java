package com.example.chatback.controllers;


import com.example.chatback.entity.Chat;
import com.example.chatback.entity.Message;
import com.example.chatback.entity.MessageDTO;
import com.example.chatback.services.ChatService;
import com.example.chatback.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class WebsController {
    private final ChatService chatService;
    private final MessageService messageService;

    @MessageMapping("/webs")
    @SendTo("/topic/99")
    public Message send(MessageDTO message)  {

        System.out.println(message+" ");
        Message message1 = new Message();
        message1.setText(message.text);
        message1.setAuthor(message.author);
        Chat chat = chatService.getById(message.chatId);
        message1.setChat(chat);
        messageService.save(message1);
        System.out.println(message.chatId);

        chat.getMessageList().add(message1);
        chatService.save(chat);
//        List<String> messageList = chat.getMessageList().stream().map(item->item.getText()).toList();
//        model.addAttribute("message",messageList);

        return message1;
    }
}