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
        return null;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Map<String, Object> postReply(Long posterID, Long sessionID, Long repliedToMessageID, String message) {

        Long newMessageID = messageRepository.INSERT_REPLY(posterID, sessionID, repliedToMessageID, message, 0L);


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
