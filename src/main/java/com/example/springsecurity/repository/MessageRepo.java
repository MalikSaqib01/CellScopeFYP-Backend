package com.example.springsecurity.repository;


import com.example.springsecurity.models.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<MessageEntity, Long> {

    @Query("SELECT m FROM MessageEntity m WHERE (m.senderName = :sender1 OR m.senderName = :sender2) AND (m.receiverName = :receiver1 OR m.receiverName = :receiver2)")
    List<MessageEntity> findBySenderNameAndReceiverName(String sender1, String sender2, String receiver1, String receiver2);

    List<MessageEntity> findBySenderName(String senderName);
}
