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
     * @param PosterID ID field of SessionUser class
     * @param SessionID ID field of SessionRoom class
     * @param MsgContent MsgContent field of Message class
     * @param MessageID ID field of Message class
     * @return the new Message ID
     * @throws PersistenceException If an error occurs in the Database, it will throw a SQLGrammarException which is of type PersistenceException
     */
    @Procedure(name = "INSERT_MESSAGE")
    Long INSERT_MESSAGE(Long PosterID, Long SessionID, String MsgContent, Long MessageID) throws PersistenceException;

    /**
     * Inserts a reply into the database
     * @param posterID ID field of SessionUser class
     * @param sessionID ID field of SessionRoom class
     * @param replyToID ID field of Message class
     * @param msgContent MsgContent field of Message class
     * @param newMessageID ID field of Message class
     * @return The new message ID
     * @throws PersistenceException If an error occurs in the Database, it will throw a SQLGrammarException which is of type PersistenceException
     */
    @Procedure(name = "INSERT_REPLY")
    Long INSERT_REPLY (Long posterID, Long sessionID, Long replyToID, String msgContent, Long newMessageID) throws PersistenceException;

    /**
     * Returns an ordered list by MessageID
     *
     * @param sessionID ID field of SessionRoom class
     * @return List<Message> containing all Message objects in the DB for that SessionRoom
     * @throws PersistenceException If an error occurs in the Database, it will throw a SQLGrammarException which is of type PersistenceException
     */
    @Procedure(name = "RETRIEVE_MESSAGES")
    List<Message> RETRIEVE_MESSAGES(Long sessionID) throws PersistenceException;

    /**
     * Updates the message content of a message given all of its ID information
     * @param messageID ID field of Message class
     * @param posterID ID field of SessionUser class
     * @param sessionID ID field of SessionRoom class
     * @param newBody String that will become the new MsgContent field of the Message class
     * @throws PersistenceException If an error occurs in the Database, it will throw a SQLGrammarException which is of type PersistenceException
     */
    @Procedure(name = "UPDATE_MESSAGE")
    void UPDATE_MESSAGE(Long messageID, Long posterID, Long sessionID, String newBody) throws PersistenceException;

    /**
     * Toggles whether a message is visible.
     * @param messageID ID field of the Message class
     * @param posterID ID field of the poster class
     * @param sessionID ID field of the SessionRoom class
     * @throws PersistenceException If an error occurs in the Database, it will throw a SQLGrammarException which is of type PersistenceException
     */
    @Procedure(name = "FLIP_VISIBILITY")
    void FLIP_VISIBILITY(Long messageID, Long posterID, Long sessionID) throws PersistenceException;

    /**
     * DEPRECIATED: Deletes a message from the DB given the Session, User, and Message ID
     * @param messageID ID field of Message class
     * @param posterID ID field of SessionUser class
     * @param sessionID ID field of SessionRoom class
     * @throws PersistenceException If an error occurs in the Database, it will throw a SQLGrammarException which is of type PersistenceException
     *
     */
    @Procedure(name = "DELETE_MESSAGE")
    void DELETE_MESSAGE(Long messageID, Long posterID, Long sessionID) throws PersistenceException;
//    @Query(value = "RETRIEVE_MESSAGES(:sessionID);", nativeQuery = true)
//    List<Message> RETRIEVE_MESSAGES(@Param("sessionID") Long sessionID);

    /**
     * Toggles a like for a specific message from a specific user.
     * @param messageID ID field of the Message class
     * @param likerID ID field of the SessionUser clas
     * @param liked INOUT parameter that will be returned by the DB
     * @return boolean True if the message has been changed to 'liked', false otherwise.
     * @throws PersistenceException If an error occurs in the Database, it will throw a SQLGrammarException which is of type PersistenceException
     */
    @Procedure(name = "LIKE_MESSAGE")
    boolean LIKE_MESSAGE(Long messageID, Long likerID, boolean liked) throws PersistenceException;

}

