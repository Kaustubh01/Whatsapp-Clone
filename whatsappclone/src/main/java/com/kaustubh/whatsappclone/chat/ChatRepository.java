package com.kaustubh.whatsappclone.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String>{


    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID)
    List<Chat> findChatsBySenderId(@Param("senderId") String userId);


    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID)
    Optional<Chat> findChatByReciverAndSender(@Param("senderId") String senderId,@Param("recipientId") String reciverId);

}
