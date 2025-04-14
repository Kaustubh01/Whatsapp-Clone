package com.kaustubh.whatsappclone.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequest {
    private String content;
    private String senderId;
    private String reciverId;
    private MessageType type;
    private String chatId;
}
