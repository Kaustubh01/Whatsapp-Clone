package com.kaustubh.whatsappclone.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaustubh.whatsappclone.user.User;
import com.kaustubh.whatsappclone.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    private final ChatMapper mapper;

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser){

        final String userId = currentUser.getName();
        return chatRepository.findChatsBySenderId(userId)
                .stream()
                .map(c-> mapper.toChatResponse(c, userId))
                .toList();

    }

    public String createChat(String senderId, String reciverId){
        Optional<Chat> existingChat = chatRepository.findChatByReciverAndSender(senderId, reciverId);
        if (existingChat.isPresent()) {
            return existingChat.get().getId();
        }

        User sender = userRepository.findByPublicId(senderId)
                        .orElseThrow(()-> new EntityNotFoundException("User with id " + senderId + " not found"));


        User receiver = userRepository.findByPublicId(reciverId)
                        .orElseThrow(()-> new EntityNotFoundException("User with id " + reciverId + " not found"));



        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(receiver);

        Chat savedChat = chatRepository.save(chat);
        return savedChat.getId();
    }

}
