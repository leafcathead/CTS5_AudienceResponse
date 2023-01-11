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

    public Map<String, Object> postPanic(String buttonPushed, Long Panicker, Long SessionRoom) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            panicRepository.INSERT_PANIC(buttonPushed, Panicker, SessionRoom);
            ret.put("Status", Status.SUCCESS);
            ret.put("Code", 0);
            ret.put("PanicId", buttonPushed);
        }catch (PersistenceException e){ // Will error, need the IF statement to check to make sure e.getCause().getCause() is a SQLServerException
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
            if (e.getCause() != null && e.getCause().getCause() instanceof SQLServerException) {
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
