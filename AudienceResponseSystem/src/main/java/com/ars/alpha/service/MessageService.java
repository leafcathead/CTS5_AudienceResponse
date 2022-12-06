package com.ars.alpha.service;

import com.ars.alpha.dao.MessageRepository;
import com.ars.alpha.dao.SessionRepository;
import com.ars.alpha.dao.UserRepository;
import com.ars.alpha.model.Message;
import com.ars.alpha.model.SessionUser;
import com.ars.alpha.other.Status;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
     * @param posterID
     * @param sessionID
     * @param message
     * @param iD
     * @return HashMap<String, Object> containing:
     * 1.  Status
     * 2.  Exit code
     * 3.  Message Id
     */
    public Map<String, Object> postComment(Long posterID, Long sessionID, String message, Long iD) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            Long msgID = messageRepository.INSERT_MESSAGE(posterID, sessionID, message, iD);
            ret.put("Status", Status.SUCCESS);
            ret.put("Code", 0);
            ret.put("MessageID", msgID);
        }catch (PersistenceException e){
            SQLServerException ex = (SQLServerException) e.getCause();
            System.out.println(ex.getSQLServerError().getErrorMessage());
            System.out.println(ex.getSQLServerError().getErrorState());
            ret.put("Status", Status.ERROR);
            ret.put("Code", ex.getSQLServerError().getErrorState());
            ret.put("messageID", 0L);
        }
        return ret;
    }


    /**
     * Posts a reply to the database. Has very simple error checking that will return an ID of 0 if an error is encountered.
     * @param posterID
     * @param sessionID
     * @param repliedToMessageID
     * @param message
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
                System.out.println(ex.getSQLServerError().getErrorState()); // This is the important one.
                // Do further useful stuff
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
     * @param sessionID
     *
     *
     *
     * @return
     */

    @Override
    public Map<String, Object> getMessages(java.lang.Long sessionID) {
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
                // messageMap.put(i, new Message(m.getId(), new SessionUser(m.getPoster().getId()), m.getMessageContents(), m.getLikes(), m.getVisible(), m.getReplyTo(), m.getTimestamp()));
                // messageMap.put(i, new Message(m.getId(), new SessionUser(m.getPoster().getId(), m.getPoster().getDisplayName()), m.getMessageContents(), m.getLikes(), m.getVisible(), m.getReplyTo() == null ? null : new Message(m.getReplyTo().getId()), m.getTimestamp()));
            }

        } catch (PersistenceException e) {
            System.out.println("Exception caught!");
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                System.out.println(ex.getSQLServerError().getErrorMessage());
                System.out.println(ex.getSQLServerError().getErrorState()); // This is the important one.
                // Do further useful stuff
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
            messageRepository.DELETE_MESSAGE(posterID, sessionID, iD);
            ret.put("Status", Status.SUCCESS);
            ret.put("Code", 0);
        } catch (PersistenceException e){
            SQLServerException ex = (SQLServerException) e.getCause();
            System.out.println(ex.getSQLServerError().getErrorMessage());
            System.out.println(ex.getSQLServerError().getErrorState());
            ret.put("Status", Status.ERROR);
            ret.put("Code", ex.getSQLServerError().getErrorState());
        }
        return ret;
    }

    @Override
    public int changeMessageApproval() {
        return 0;
    }

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
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                returnerMap.put("Status", Status.ERROR);
                returnerMap.put("Code", ex.getSQLServerError().getErrorState());
            } else {
                throw new IllegalStateException("How???");
            }
        }

        return returnerMap;
    }


}


