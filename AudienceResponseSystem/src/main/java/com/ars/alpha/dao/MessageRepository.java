package com.ars.alpha.dao;

import com.ars.alpha.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.util.Map;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {



    @Procedure(name = "INSERT_REPLY")
    Long INSERT_REPLY (Long posterID, Long sessionID, Long replyToID, String msgContent, Long newMessageID) throws PersistenceException;

}
