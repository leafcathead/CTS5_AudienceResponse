package com.ars.alpha.dao;

import com.ars.alpha.model.SessionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface SessionRepository extends JpaRepository<SessionRoom, Long> {

//    @Procedure(name = "CREATE_SESSION")
//    Map<String, Object> CREATE_SESSION(@Param("newSessionID") Long sessionID,@Param("newUserID") Long userID,@Param("randomPassword") String password);
    @Procedure(name = "CREATE_SESSION")
    Map<String, Object> CREATE_SESSION(Long newSessionID,Long newUserID,String randomPassword);

    @Procedure("FAVORITE_NUM")
    int favNum(Integer useless);
}
