package com.ars.alpha.service;

import com.ars.alpha.other.Status;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserServiceInterface {

    @Override
    public Map<String, Object> updateDisplayName(Long userID, Long sessionID, String newName) {

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        try {

            if (newName.length() > 20) {
                throw new IllegalArgumentException("New display name will be truncated...");
            }
            // Need a SPROC!
            
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
}
