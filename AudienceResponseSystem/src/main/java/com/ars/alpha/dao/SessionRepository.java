package com.ars.alpha.dao;

import com.ars.alpha.model.SessionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.util.Map;

@Repository
public interface SessionRepository extends JpaRepository<SessionRoom, Long> {

//    @Procedure(name = "CREATE_SESSION")
//    Map<String, Object> CREATE_SESSION(@Param("newSessionID") Long sessionID,@Param("newUserID") Long userID,@Param("randomPassword") String password);
    @Procedure(name = "CREATE_SESSION")
    Map<String, Object> CREATE_SESSION(Long newSessionID,Long newUserID,String randomPassword);

    @Procedure(name = "GET_SESSION_ROOM_ID_FROM_PASSWORD")
    Long GET_SESSION_ROOM_ID_FROM_PASSWORD(String sessionPassword, Long sessionID) throws PersistenceException;

    @Procedure(name = "JOIN_SESSION")
    Long JOIN_SESSION (Long sessionID, Long newUserID);

    @Procedure(name = "CLOSE_SESSION")
    void CLOSE_SESSION (Long sessionID) throws PersistenceException;
}
