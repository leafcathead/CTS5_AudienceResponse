package com.ars.alpha.service;

import com.ars.alpha.dao.SessionRepository;
import com.ars.alpha.dao.TestStudentRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int closeSession() {
        return 0;
    }
}
