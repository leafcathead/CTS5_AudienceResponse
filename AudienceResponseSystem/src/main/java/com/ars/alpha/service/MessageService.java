package com.ars.alpha.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageService implements MessageServiceInterface {


    @Override
    public Map<String, Object> postComment(Long posterID, Long sessionID, String message) {
        return null;
    }

    @Override
    public int changeMessageApproval() {
        return 0;
    }
}
