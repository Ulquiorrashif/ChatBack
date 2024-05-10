package com.example.chatback.entity;

import lombok.Data;

@Data
public class MessageDTO {
    public Long chatId;
    public String text;
    public String author;
}
