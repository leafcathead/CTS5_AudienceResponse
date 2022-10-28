package com.ars.alpha.service;

import com.ars.alpha.dao.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionService implements SessionServiceInterface {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Map<String, Object> createSession() {
        System.out.println("Create Session Service");

   //     sessionRepository.favNum(0);
        System.out.println("Stored procedure returned");
//        for(Map.Entry<String, Object> entry : test.entrySet()) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//            System.out.println("Output from stored procedure:");
//            System.out.println(key);
//            System.out.println(value);
//
//            // do what you have to do here
//            // In your case, another loop.
//        }

        return sessionRepository.CREATE_SESSION(1L, 1L, "Hel");
    }

    @Override
    public Map<String, Long> joinSession(String password) {
        Map<String, Long> returnMap = new HashMap<String, Long>();
        // First I have to get the session ID from the password.
        Long sessionID = sessionRepository.GET_SESSION_ROOM_ID_FROM_PASSWORD(password, 1L);
        System.out.println("Session ID: " + sessionID);

        if (sessionID == 0) {

            returnMap.put("userID", 0L);
            returnMap.put("sessionID", 0L);
        }

        // Now we can add them to the session
        Long userID = sessionRepository.JOIN_SESSION(sessionID, 1L);
        System.out.println("User ID: " + userID);

        returnMap.put("newUserID", userID);
        returnMap.put("newSessionID", sessionID);

        return (Map<String,Long>) returnMap;
    }

    @Override
    public int closeSession() {
        return 0;
    }
}
