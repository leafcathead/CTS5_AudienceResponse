package com.ars.alpha.service;

import com.ars.alpha.dao.UserRepository;
import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.other.Status;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    /**
     * Updates the display name of a SessionUser
     * @param userID ID field of SessionUser class
     * @param sessionID ID field of SessionRoom class
     * @param newName DisplayName field of SessionUser class
     * @return Map containing Status and Code
     */
    @Override
    public Map<String, Object> updateDisplayName(Long userID, Long sessionID, String newName) {

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        try {

            if (newName.length() > 20) {
                throw new IllegalArgumentException("New display name will be truncated...");
            }
            userRepository.UPDATE_DISPLAY_NAME(userID, sessionID, newName);
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
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("New display name will be truncated...")) {
                returnerMap.put("Status", Status.ERROR);
                returnerMap.put("Code", -1);
            } else {
                throw new IllegalStateException("Some other illegal argument");
            }
        }


        return returnerMap;
    }

    /**
     * Returns the sessionRoomID given a userID, assuming the User exists.
     *
     * @param userID the userID, self-explanatory
     * @return SessionRoom object
     * @throws NoSuchElementException Occurs if the user does not exist
     */
    @Override
    public SessionRoom getSessionRoomByID(Long userID) throws NoSuchElementException {
        return userRepository.findById(userID).orElseThrow().getSession();
    }


}
