package com.ars.alpha.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class MessageService implements MessageServiceInterface {


    @Override
    @Transactional
    public Map<String, Object> postComment(Long posterID, Long sessionID, String message) {
        return null;
    }

    @Override
    public Map<String, Object> postReply(Long posterID, Long sessionID, Long repliedToMessageID, String message) {
        return null;
    }

    @Override
    public int changeMessageApproval() {
        return 0;
    }
}
