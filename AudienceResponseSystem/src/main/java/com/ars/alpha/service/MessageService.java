package com.ars.alpha.service;

import com.ars.alpha.dao.MessageRepository;
import com.ars.alpha.dao.SessionRepository;
import com.ars.alpha.dao.UserRepository;
import com.ars.alpha.model.Message;
import com.ars.alpha.model.SessionUser;
import com.ars.alpha.other.Status;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService implements MessageServiceInterface {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    /**
     * Posts a comment to the database
     *
     * @param posterID ID field of SessionUser class
     * @param sessionID ID field of SessionRoom class
     * @param message MsgContent of Message class
     * @param iD INOUT ID for Message Class
     * @return HashMap<String, Object> containing:
     * 1.  Status
     * 2.  Exit code
     * 3.  Message Id
     */
   @Transactional
    public Map<String, Object> postComment(Long posterID, Long sessionID, String message, Long iD) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            Long msgID = messageRepository.INSERT_MESSAGE(posterID, sessionID, message, iD);
            ret.put("Status", Status.SUCCESS);
            ret.put("Code", 0);
            ret.put("MessageID", msgID);
        }catch (PersistenceException e){
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                System.out.println(ex.getSQLServerError().getErrorMessage());
                System.out.println(ex.getSQLServerError().getErrorState());
                ret.put("Status", Status.ERROR);
                ret.put("Code", ex.getSQLServerError().getErrorState());
                ret.put("MessageID", 0L);

            } else {
                throw new IllegalStateException("How???");
            }

        }
        return ret;
    }


    /**
     * Posts a reply to the database. Has very simple error checking that will return an ID of 0 if an error is encountered.
     * @param posterID ID field of SessionUser
     * @param sessionID ID field of SessionRoom
     * @param repliedToMessageID ID field of Message
     * @param message MsgContent field of Message
     * @return HashMap<String, Object> containing:
     *          1. STATUS
     *          2. EXIT CODE
     *          3. Message ID
     */
    @Override
    public Map<String, Object> postReply(Long posterID, Long sessionID, Long repliedToMessageID, String message) {

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        try {
            Long newMessageID = messageRepository.INSERT_REPLY(posterID, sessionID, repliedToMessageID, message, 0L);
            returnerMap.put("Status", Status.SUCCESS);
            returnerMap.put("Code", 0);
            returnerMap.put("MessageID", newMessageID);

        } catch (PersistenceException e) {
            System.out.println("Exception caught!");
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                System.out.println(ex.getSQLServerError().getErrorMessage());
                System.out.println(ex.getSQLServerError().getErrorState());
                returnerMap.put("Status", Status.ERROR);
                returnerMap.put("Code", ex.getSQLServerError().getErrorState());
                returnerMap.put("messageID", 0L);

            } else {
                throw new IllegalStateException("How???");
            }

        }

        return returnerMap;
    }

    /**
     *
     * Gets all messages for a SessionRoom in the database
     * @param sessionID ID field of SessionRoom class
     *
     * @return Map containing status, code, and List of Message objects
     */

    @Override
    @Transactional(noRollbackFor = {IllegalStateException.class, PersistenceException.class, Exception.class})
    public Map<String, Object> getMessages(java.lang.Long sessionID) throws UnexpectedRollbackException {
        System.out.println(sessionID);

        Map<String, Object> returnerMap = new HashMap<String, Object>();
        Map<Integer, Object> messageMap = new HashMap<Integer, Object>();

        try {

            List<Message> returnerList = messageRepository.RETRIEVE_MESSAGES(sessionID);

            returnerMap.put("Status", Status.SUCCESS);
            returnerMap.put("Code", 0);

            // Not sure how helpful this construction was. Experimenting with a different one.
            for (int i = 0; i < returnerList.size(); i++) {
                Message m = returnerList.get(i);
                messageMap.put(i, m);

            }

        } catch (PersistenceException e) {
            System.out.println("Exception caught!");
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                System.out.println(ex.getSQLServerError().getErrorMessage());
                System.out.println(ex.getSQLServerError().getErrorState());
                returnerMap.put("Status", Status.ERROR);
                returnerMap.put("Code", ex.getSQLServerError().getErrorState());
                returnerMap.put("messageID", 0L);

            } else {
                throw new IllegalStateException("How???");
            }
        }

        returnerMap.put("Messages", messageMap);

        return returnerMap;
    }

    public Map<String, Object> deleteComment(Long posterID, Long sessionID, Long iD) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try{
            messageRepository.DELETE_MESSAGE(iD, posterID, sessionID);
            ret.put("Status", Status.SUCCESS);
            ret.put("Code", 0);
        } catch (PersistenceException e){
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                System.out.println(ex.getSQLServerError().getErrorMessage());
                System.out.println(ex.getSQLServerError().getErrorState());
                ret.put("Status", Status.ERROR);
                ret.put("Code", ex.getSQLServerError().getErrorState());
            } else {
                throw new IllegalStateException("Illegal state when deleting");
            }
        }
        return ret;
    }



    /**
     * Updates body text of Message object
     * @param messageID ID field of Message class
     * @param posterID ID field of SessionUser class
     * @param sessionID ID field of SessionRoom class
     * @param newContent new Body content for MsgContent
     * @return Map containing Status and Code
     */
    @Override
    public Map<String, Object> updateMessageContent(Long messageID, Long posterID, Long sessionID, String newContent) {

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        try {

            if (newContent.length() > 500) {
                throw new IllegalArgumentException("Message content will be truncated.");
            }

            messageRepository.UPDATE_MESSAGE(messageID, posterID, sessionID, newContent);

            returnerMap.put("Status", Status.SUCCESS);
            returnerMap.put("Code", 0);

        } catch (PersistenceException e) {
            System.out.println("Exception caught!");
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                returnerMap.put("Status", Status.ERROR);
                returnerMap.put("Code", ex.getSQLServerError().getErrorState());
            } else {
                throw new IllegalStateException("How???");
            }
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Message content will be truncated.")) {
                returnerMap.put("Status", Status.ERROR);
                returnerMap.put("Code", 2);
            } else {
                throw new IllegalStateException("Illegal Argument somewhere...");
            }

        }

        return returnerMap;
    }

    /**
     * Toggles message visibility
     * @param messageID ID field of Message class
     * @param posterID ID field of SessionUser class
     * @param sessionID ID field of SessionRoom class
     * @return Map containing Status and Code
     */
    @Override
    public Map<String, Object> updateMessageVisibility(Long messageID, Long posterID, Long sessionID) {

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        try {

            messageRepository.FLIP_VISIBILITY(messageID, posterID, sessionID);

            returnerMap.put("Status", Status.SUCCESS);
            returnerMap.put("Code", 0);

        } catch (PersistenceException e) {
            System.out.println("Exception caught!");
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                returnerMap.put("Status", Status.ERROR);
                returnerMap.put("Code", ex.getSQLServerError().getErrorState());
            } else {
                throw new IllegalStateException("How???");
            }
        }

        return returnerMap;
    }

    /**
     * Deletes a message from the DB
     * @param messageID ID field of Message class
     * @param posterID ID field of SessionUser class
     * @param sessionID ID field of SessionRoom class
     * @return Map containing Status and Code
     */
    @Override
    public Map<String, Object> deleteMessage(Long messageID, Long posterID, Long sessionID) {

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        try {

            messageRepository.DELETE_MESSAGE(messageID, posterID, sessionID);

            returnerMap.put("Status", Status.SUCCESS);
            returnerMap.put("Code", 0);

        } catch (PersistenceException e) {
            System.out.println("Exception caught!");
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                System.out.println("Is Instance of SQL Server Exception");
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                returnerMap.put("Status", Status.ERROR);
                returnerMap.put("Code", ex.getSQLServerError().getErrorState());
            } else {
                throw new IllegalStateException("How???");
            }
        }

        return returnerMap;
    }

    /**
     * Toggles message like
     * @param messageID ID field of Message class
     * @param likerID ID field of SessionUser class
     * @return Map containing Status, Code, and MessageLiked status (True or False)
     */
    @Override
    @Transactional
    public Map<String, Object> likeMessage(Long messageID, Long likerID) {

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        try {

            boolean liked = messageRepository.LIKE_MESSAGE(messageID, likerID, true);

            returnerMap.put("Status", Status.SUCCESS);
            returnerMap.put("Code", 0);
            returnerMap.put("Liked", liked);

        } catch (PersistenceException e) {
            System.out.println("Exception caught!");
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                returnerMap.put("Status", Status.ERROR);
                returnerMap.put("Code", ex.getSQLServerError().getErrorState());
                returnerMap.put("Liked", false);
            } else {
                throw new IllegalStateException("How???");
            }
        }

        return returnerMap;
    }


}


