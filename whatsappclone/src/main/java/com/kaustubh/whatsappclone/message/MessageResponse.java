package com.kaustubh.whatsappclone.message;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MessageResponse {
    private Long id;
    private String content;
    private MessageType type;
    private MessageState state;
    private String senderId;
    private String reciverId;
    private LocalDateTime createdAt;
    private byte[] media;
}
