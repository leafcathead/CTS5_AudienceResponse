package com.ars.alpha.service;

import com.ars.alpha.dao.MessageRepository;
import com.ars.alpha.dao.SessionRepository;
import com.ars.alpha.dao.UserRepository;
import com.ars.alpha.other.Status;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageService implements MessageServiceInterface {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Override
    public Map<String, Object> postComment(Long posterID, Long sessionID, String message) {
        return null;
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
     * @return
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Map<String, Object> getMessages(Long sessionID) {
        System.out.println("Message service");
        messageRepository.RETRIEVE_MESSAGES(1L);
        return null;
    }

    @Override
    public int changeMessageApproval() {
        return 0;
    }
}
