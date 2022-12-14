package com.ars.alpha.dao;

import com.ars.alpha.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Map;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Inserts a new message into the database
     * @param PosterID
     * @param SessionID
     * @param MsgContent
     * @param MessageID
     * @return the new Message ID
     */
    @Procedure(name = "INSERT_MESSAGE")
    Long INSERT_MESSAGE(Long PosterID, Long SessionID, String MsgContent, Long MessageID) throws PersistenceException;

    /**
     * Inserts a reply into the database
     * @param posterID
     * @param sessionID
     * @param replyToID
     * @param msgContent
     * @param newMessageID
     * @return The new message ID
     * @throws PersistenceException
     */
    @Procedure(name = "INSERT_REPLY")
    Long INSERT_REPLY (Long posterID, Long sessionID, Long replyToID, String msgContent, Long newMessageID) throws PersistenceException;

    /**
     * Returns an ordered list by MessageID
     *
     * @param sessionID
     * @return
     * @throws PersistenceException
     */
    @Procedure(name = "RETRIEVE_MESSAGES")
    List<Message> RETRIEVE_MESSAGES(Long sessionID) throws PersistenceException;

    /**
     * Updates the message content of a message given all of its ID information
     * @param messageID
     * @param posterID
     * @param sessionID
     * @param newBody
     * @throws PersistenceException
     */
    @Procedure(name = "UPDATE_MESSAGE")
    void UPDATE_MESSAGE(Long messageID, Long posterID, Long sessionID, String newBody) throws PersistenceException;

    /**
     * Toggles whether a message is visible.
     * @param messageID
     * @param posterID
     * @param sessionID
     * @throws PersistenceException
     */
    @Procedure(name = "FLIP_VISIBILITY")
    void FLIP_VISIBILITY(Long messageID, Long posterID, Long sessionID) throws PersistenceException;

    @Procedure(name = "DELETE_MESSAGE")
    void DELETE_MESSAGE(Long messageID, Long posterID, Long sessionID) throws PersistenceException;
//    @Query(value = "RETRIEVE_MESSAGES(:sessionID);", nativeQuery = true)
//    List<Message> RETRIEVE_MESSAGES(@Param("sessionID") Long sessionID);

    @Procedure(name = "LIKE_MESSAGE")
    int LIKE_MESSAGE(Long messageID, Long likerID, int numberOfLikes) throws PersistenceException;

}

