package com.university.mediaverse.service;

import com.university.mediaverse.model.Message;
import com.university.mediaverse.model.user.User;
import com.university.mediaverse.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message){
        return messageRepository.save(message);
    }

    public void deletePost(Message  message){
        messageRepository.delete(message);
    }

    public List<Message> findAll(){return messageRepository.findAll();}

    public List<Message> findMessageForUser(User user) {
        return messageRepository.findByUser1(user);
    }

    public List<Message> findMessagesForUserWithFriend(User currentUser, User friend) {
        List<Message> messagesSendByCurrentUser = messageRepository.findAllByUser1AndUser2(currentUser, friend);
        List<Message> messagesSendByFriend = messageRepository.findAllByUser2AndUser1(currentUser, friend);

        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(messagesSendByCurrentUser);
        allMessages.addAll(messagesSendByFriend);

        allMessages.sort(Comparator.comparing(Message::getTime));

        return allMessages;

    }
}
