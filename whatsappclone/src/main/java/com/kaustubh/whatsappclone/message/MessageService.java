package com.kaustubh.whatsappclone.message;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kaustubh.whatsappclone.chat.Chat;
import com.kaustubh.whatsappclone.chat.ChatRepository;
import com.kaustubh.whatsappclone.file.FileService;
import com.kaustubh.whatsappclone.file.FileUtils;
import com.kaustubh.whatsappclone.notification.Notification;
import com.kaustubh.whatsappclone.notification.NotificationService;
import com.kaustubh.whatsappclone.notification.NotificationType;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper mapper;
    private final FileService fileService;
    private final NotificationService notificationService;

    public void saveMessage(MessageRequest messageRequest) {
        Chat chat = chatRepository.findById(messageRequest.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        Message message = new Message();
        message.setContent(messageRequest.getContent());
        message.setChat(chat);
        message.setSenderId(messageRequest.getSenderId());
        message.setReceiverId(messageRequest.getReciverId());
        message.setType(messageRequest.getType());
        message.setState(MessageState.SENT);

        messageRepository.save(message);

        Notification notification = Notification.builder()
            .chatId(chat.getId())
            .messageType(messageRequest.getType())
            .content(messageRequest.getContent())
            .senderId(messageRequest.getSenderId())
            .reciverId(messageRequest.getReciverId())
            .type(NotificationType.MESSAGE)
            .chatName(chat.getChatName(message.getSenderId()))
            .build();

        notificationService.sendNotification(messageRequest.getReciverId(), notification);

    }

    public List<MessageResponse> findChatMessages(String chatId) {
        return messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(mapper::toMessageResponse)
                .toList();
    }

    @Transactional
    public void setMessagesToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not Found"));
        final String recipientId = getRecipientId(chat, authentication);

        messageRepository.setMessagesToSeenByChatId(chatId, MessageState.SEEN);

        
        Notification notification = Notification.builder()
            .chatId(chat.getId())
            .senderId(getSenderId(chat, authentication))
            .reciverId(recipientId)
            .type(NotificationType.SEEN)
            .build();

        notificationService.sendNotification(recipientId, notification);
    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }

    void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not Found"));

        final String senderId = getSenderId(chat, authentication);
        final String recipentId = getRecipientId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);
        Message message = new Message();
        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(recipentId);
        message.setType(MessageType.IMAGE);
        message.setState(MessageState.SENT);

        message.setMediaFilePath(filePath);

        messageRepository.save(message);

        
        Notification notification = Notification.builder()
            .chatId(chat.getId())
            .messageType(MessageType.IMAGE)
            .senderId(senderId)
            .reciverId(recipentId)
            .type(NotificationType.IMAGE)
            .media(FileUtils.readFileFromLocation(filePath))
            .build();

        notificationService.sendNotification(recipentId, notification);
    }

    private String getSenderId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }

        return chat.getRecipient().getId();
    }
}
