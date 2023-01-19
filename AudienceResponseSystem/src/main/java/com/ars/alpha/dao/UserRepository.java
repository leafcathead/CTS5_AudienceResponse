package com.ars.alpha.dao;

import com.ars.alpha.model.SessionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<SessionUser,Long> {

    /**
     * Updates the display name for a SessionUser given the ID
     * @param userID ID field of the SessionUser class
     * @param sessionID ID field of the SessionRoom class
     * @param newName new String value for the DisplayName field of the SessionUser class.
     */
    @Procedure(name = "UPDATE_DISPLAY_NAME")
    void UPDATE_DISPLAY_NAME(Long userID, Long sessionID, String newName);

}
