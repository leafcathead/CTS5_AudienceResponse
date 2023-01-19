package com.ars.alpha.dao;

import com.ars.alpha.model.SessionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.util.Map;

@Repository
public interface SessionRepository extends JpaRepository<SessionRoom, Long> {


    /**
     * Creates a Session in the DB
     * @param newSessionID INOUT parameter that returns the new SessionID
     * @param newUserID INOUT parameter that returns the OwnerID
     * @param randomPassword INOUT parameter that returns the randomly generated SessionRoom password.
     * @return Map<String, Object> that contains the value of all three INOUT parameters.
     */
    @Procedure(name = "CREATE_SESSION")
    Map<String, Object> CREATE_SESSION(Long newSessionID,Long newUserID,String randomPassword);

    /**
     * Returns the SessionRoom ID given the random password.
     * @param sessionPassword Random String password
     * @param sessionID ID field of the SessionRoom class
     * @return Long SessionID if corresponding session found
     * @throws PersistenceException If an error occurs in the Database, it will throw a SQLGrammarException which is of type PersistenceException
     */
    @Procedure(name = "GET_SESSION_ROOM_ID_FROM_PASSWORD")
    Long GET_SESSION_ROOM_ID_FROM_PASSWORD(String sessionPassword, Long sessionID) throws PersistenceException;

    /**
     * Creates a new UserID for a session
     * @param sessionID ID field of the SessionRoom class
     * @param newUserID INOUT Parameter for the new user ID for the SessionUser class
     * @return Long New User ID
     */
    @Procedure(name = "JOIN_SESSION")
    Long JOIN_SESSION (Long sessionID, Long newUserID);

    /**
     * Deletes everything from the database that belongs to a session.
     * @param sessionID ID field of the SessionRoom class
     * @throws PersistenceException If an error occurs in the Database, it will throw a SQLGrammarException which is of type PersistenceException
     */
    @Procedure(name = "CLOSE_SESSION")
    void CLOSE_SESSION (Long sessionID) throws PersistenceException;
}
