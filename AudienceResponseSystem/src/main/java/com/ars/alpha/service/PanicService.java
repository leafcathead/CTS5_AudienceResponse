package com.ars.alpha.service;

import com.ars.alpha.dao.PanicRepository;
import com.ars.alpha.model.PanicResponse;
import com.ars.alpha.other.Status;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PanicService implements PanicServiceInterface{


    @Autowired
    PanicRepository panicRepository;

    /**
     * Posts PanicResponse to DB
     * @param buttonPushed 4 Char String abbreviation for PanicButton
     * @param Panicker ID field of SessionUser
     * @param SessionRoom ID field of SessionRoom
     * @return Map containing Status, Code, and PanicResponse ID
     */
    @Override
    @Transactional
    public Map<String, Object> postPanic(String buttonPushed, Long Panicker, Long SessionRoom) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            panicRepository.INSERT_PANIC(buttonPushed, Panicker, SessionRoom);
            ret.put("Status", Status.SUCCESS);
            ret.put("Code", 0);
            ret.put("PanicId", buttonPushed);
        }catch (PersistenceException e){
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                ret.put("Status", Status.ERROR);
                ret.put("Code", ex.getSQLServerError().getErrorState());
                ret.put("PanicResponse",buttonPushed);
            } else {
                throw new IllegalStateException("How???");
            }
        }
        return ret;
    }


    /**
     * Gets all PanicResponse objects as a list from the DB
     * @param sessionID ID field of SessionRoom class
     * @return Map containing Status, Code, and List of PanicResponse
     */
    @Override
    @Transactional
    public Map<String, Object> getPanicResponses(Long sessionID) {

        Map<String, Object> returnerMap = new HashMap<String, Object>();
        List<PanicResponse> panicArray = null;

        try {

            panicArray = panicRepository.GET_PANIC_RESPONSE(sessionID);
            returnerMap.put("PanicResponse", panicArray);
            returnerMap.put("Status", Status.SUCCESS);
            returnerMap.put("Code", 0);

        } catch (PersistenceException e) {
            System.out.println("Exception caught!");
            if (e.getCause().getCause() != null && e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
                SQLServerException ex = (SQLServerException) e.getCause().getCause();
                returnerMap.put("Status", Status.ERROR);
                returnerMap.put("Code", ex.getSQLServerError().getErrorState());
                returnerMap.put("PanicResponse",panicArray);
            } else {
                throw new IllegalStateException("How???");
            }
            

        }

        return returnerMap;
    }
}
