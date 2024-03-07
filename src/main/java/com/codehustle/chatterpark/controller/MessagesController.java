package com.codehustle.chatterpark.controller;

import com.codehustle.chatterpark.model.ChatMessage;
import com.codehustle.chatterpark.service.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/messages")
@RequiredArgsConstructor
public class MessagesController {

    private final MessagesService messagesService;

    @GetMapping
    public List<ChatMessage> getMessages(){
        return messagesService.getAllMessages();
    }
}
