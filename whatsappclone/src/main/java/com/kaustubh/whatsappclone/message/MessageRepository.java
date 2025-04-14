package com.kaustubh.whatsappclone.message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kaustubh.whatsappclone.chat.Chat;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

    @Query(name = MessageConstants.FIND_MESSAGE_BY_CHAT_ID)
    List<Message> findMessagesByChatId(String chatId);


    @Query(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT)
    @Modifying
    void setMessagesToSeenByChatId(@Param("chatId") String chatId, @Param("newState") MessageState seen);
}
