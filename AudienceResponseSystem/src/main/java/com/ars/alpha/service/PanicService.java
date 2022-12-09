package com.ars.alpha.service;

import com.ars.alpha.dao.PanicRepository;
import com.ars.alpha.other.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class PanicService implements PanicServiceInterface{

//    @Autowired
//    PanicRepository panicRepository;
//
//    Map<String, Object> postPanic(Long ID, Long PanicButtonPushed, Long Panicker, Long SessionRoom, String LogTime){
//        Map<String, Object> ret = new HashMap<String, Object>();
//        try {
//            Long Time = panicRepository.INSERT_PANIC(ID, Panicker, SessionRoom, LogTime);
//            ret.put("Status", Status.SUCCESS);
//            ret.put("Code", 0);
//            ret.put("PanicId", ID);
//            ret.put("LogTime", Time);
//        }catch (PersistenceException e){
//            SQLServerException ex = (SQLServerException) e.getCause();
//            System.out.println(ex.getSQLServerError().getErrorMessage());
//            System.out.println(ex.getSQLServerError().getErrorState());
//            ret.put("Status", Status.ERROR);
//            ret.put("Code", ex.getSQLServerError().getErrorState());
//            ret.put("PanicId", 0L);
//            ret.put("LogTime", "unknown");
//        }
//        return ret;
//    }
}
