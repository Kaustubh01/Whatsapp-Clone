package com.kaustubh.whatsappclone.chat;

import org.springframework.stereotype.Service;

@Service
public class ChatMapper {

    public ChatResponse toChatResponse(Chat c, String senderId) {

        return ChatResponse.builder()
            .id(c.getId())
            .name(c.getChatName(senderId))
            .ureadCount(c.getUnreadMessages(senderId))
            .lastMessage(c.getLastMessage())
            .isRecipientOnline(c.getRecipient().isUserOnline())
            .senderId(c.getSender().getId())
            .reciverId(c.getRecipient().getId())
            .build();
    }




}
