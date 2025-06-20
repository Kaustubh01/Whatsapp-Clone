package com.kaustubh.whatsappclone.notification;

import com.kaustubh.whatsappclone.message.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    private String chatId;
    private String content;
    private String senderId;
    private String reciverId;
    private String chatName;
    private MessageType messageType;
    private NotificationType type;
    private byte[] media;
}
