package com.codehustle.chatterpark.controller;

import com.codehustle.chatterpark.entity.Messages;
import com.codehustle.chatterpark.entity.User;
import com.codehustle.chatterpark.enums.MessageType;
import com.codehustle.chatterpark.model.ChatMessage;
import com.codehustle.chatterpark.security.SecurityUtils;
import com.codehustle.chatterpark.service.MessagesService;
import com.codehustle.chatterpark.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final UserService userService;
    private final MessagesService messagesService;

    @MessageMapping("/user.connect")
    @SendTo("/public/topic")
    public ChatMessage publishUsers(Principal principal){
        User user = SecurityUtils.getUserFromPrincipal(principal);
        userService.updateUserOnlineStatus(true,user.getUserId());
        return ChatMessage
                .builder()
                .userId(user.getUserId())
                .users(userService.getActiveUsers())
                .messageType(MessageType.JOIN)
                .username(user.getUsername())
                .build();
    }

    @MessageMapping("/user.chat")
    @SendTo("/public/topic")
    public ChatMessage publishChatMessage(@Payload ChatMessage chatMessage){
        LocalDateTime msgTime = LocalDateTime.now();
        Messages messages = new Messages();
        messages.setUserId(chatMessage.getUserId());
        messages.setUsername(chatMessage.getUsername());
        messages.setMessage(chatMessage.getMessage());
        messages.setMessageTime(msgTime);

        messagesService.saveMessage(messages);

        chatMessage.setMessageType(MessageType.CHAT);
        chatMessage.setMessageTime(msgTime);

        return chatMessage;
    }

    @MessageMapping(value = "/user.disconnect")
    @SendTo("/public/topic")
    public ChatMessage publishUserDisconnected(Principal principal){
        User user = SecurityUtils.getUserFromPrincipal(principal);
        userService.updateUserOnlineStatus(false,user.getUserId());
        SecurityContextHolder.clearContext();
        return ChatMessage
                .builder()
                .userId(user.getUserId())
                .users(userService.getActiveUsers())
                .messageType(MessageType.LEAVE)
                .build();
    }



}
