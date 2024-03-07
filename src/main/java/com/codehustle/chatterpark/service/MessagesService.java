package com.codehustle.chatterpark.service;

import com.codehustle.chatterpark.entity.Messages;
import com.codehustle.chatterpark.model.ChatMessage;
import com.codehustle.chatterpark.repository.MessagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessagesService {

    private final MessagesRepository messagesRepository;
    private final MapperService mapperService;

    public void saveMessage(Messages messages){
        messagesRepository.save(messages);
    }

    public List<ChatMessage> getAllMessages(){
        List<Messages> messages = messagesRepository.findMessagesByMessageTimeAfter(LocalDateTime.now().minusHours(1L));
        if(CollectionUtils.isEmpty(messages))
            return null;
        return mapperService.map(messages,ChatMessage.class);
    }

}
