package com.ars.alpha.service;

import com.ars.alpha.dao.MessageRepository;
import com.ars.alpha.dao.SessionRepository;
import com.ars.alpha.dao.UserRepository;
import com.ars.alpha.model.Message;
import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.model.SessionUser;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class MessageService implements MessageServiceInterface {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Override
    @Transactional
    public Map<String, Object> postComment(Long posterID, Long sessionID, String message) {
        System.out.println("Message posted");
        return messageRepository.INSERT_MESSAGE(posterID, sessionID, message);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Map<String, Object> postReply(Long posterID, Long sessionID, Long repliedToMessageID, String message) {

        // First check to make sure the poster actually belongs to the session they claim they're in.
        Optional<SessionUser> tmpUser = userRepository.findById(posterID);
        Optional<SessionRoom> tmpRoom = sessionRepository.findById(sessionID);
        Optional<Message> tmpMessage = messageRepository.findById(repliedToMessageID);
        if (tmpUser.isEmpty() || tmpRoom.isEmpty() || tmpMessage.isEmpty()) {
            // TODO: Error handling because User or Session or Message does not exist.
            // Do something!
            return null;
        }

        SessionUser poster = tmpUser.get();
        SessionRoom session = tmpRoom.get();

        // Now check to see if the poster actually belongs to that session
        if (!Objects.equals(poster.getSession().getID(), sessionID)) {
            //TODO: Error handling because User does not belong to that Session.
            // Do something!
            return null;
        }

        // Now that parameters are validated, we can begin the update.


        return null;
    }

    /**
     * @transaction Isolation = Repeatable_Read
     * @param sessionID
     * @return
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Map<String, Object> getMessages(Long sessionID) {

        return null;
    }

    @Override
    public int changeMessageApproval() {
        return 0;
    }
}
