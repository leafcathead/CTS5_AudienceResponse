package com.ars.alpha.service;

import com.ars.alpha.dao.SessionRepository;
import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.other.Status;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SessionService implements SessionServiceInterface {

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Calls a SPROC to create a session. See more information in SessionRoom NamedStoredProcedureQuery and in SessionRepository interface.
     *
     * @return Result set from Stored Procedure Call CREATE_SESSION
     */
    @Override
    public Map<String, Object> createSession() {
        System.out.println("Create Session Service");

        return sessionRepository.CREATE_SESSION(1L, 1L, "Hel");
    }

    /**
     * Calls two stored procedures to join a session:
     * 1. One that retrieves the correct Session ID from a provided password. If sessionID = 0, the session does not exist.
     * 2. Joins a user into the found session.
     *
     *
     * @return Specific columns from the result set of Stored Procedure Call JOIN_SESSION
     */
    @Override
    public Map<String, Long> joinSession(String password) {
        Map<String, Long> returnMap = new HashMap<String, Long>();
        // First I have to get the session ID from the password.
        Long sessionID = 0L;
        try {

            sessionID = sessionRepository.GET_SESSION_ROOM_ID_FROM_PASSWORD(password, 1L);

        } catch (PersistenceException e) {
            System.out.println("Caught persistence exception");
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                System.out.println(ex.toString());
                System.out.println(ex.getErrorCode());
                System.out.println(ex.getSQLState());
                System.out.println(ex.getSQLServerError().getErrorNumber());
                System.out.println(ex.getSQLServerError().getErrorMessage());
                System.out.println(ex.getSQLServerError().getErrorSeverity());
                System.out.println(ex.getSQLServerError().getErrorState()); // This is the important one.
                // Do further useful stuff
            } else {
                throw new IllegalStateException("How???");
            }
        } catch (Exception e) {
            System.out.println("What?");
        }

        System.out.println("Session ID: " + sessionID);

        // A non-existent session is communicated to the frontend using a sessionID and userID of 0
        if (sessionID == 0) {

            returnMap.put("userID", 0L);
            returnMap.put("sessionID", 0L);
            return returnMap;
        }

        // Now we can add them to the session since we know the session exists.
        Long userID = sessionRepository.JOIN_SESSION(sessionID, 1L);
        System.out.println("User ID: " + userID);

        returnMap.put("newUserID", userID);
        returnMap.put("newSessionID", sessionID);

        return (Map<String,Long>) returnMap;
    }

    @Override
    public Map<String, Object> closeSession(Long sessionID, Long ownerID) {

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        try {

            sessionRepository.CLOSE_SESSION(sessionID, ownerID);

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
    public boolean checkSessionStatus(Long sessionID) {

        Optional<SessionRoom> result = sessionRepository.findById(sessionID);
        sessionRepository.flush();

        return result.isPresent();
    }
}
