package com.ars.alpha.dao;

import com.ars.alpha.model.SessionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<SessionUser,Long> {

    @Procedure(name = "UPDATE_DISPLAY_NAME")
    void UPDATE_DISPLAY_NAME(Long userID, Long sessionID, String newName);

}
