package com.ars.alpha.dao;

import com.ars.alpha.model.Message;
import com.ars.alpha.model.PanicResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import javax.persistence.PersistenceException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PanicRepository extends JpaRepository<PanicResponse, Long> {
    @Procedure(name = "INSERT_PANIC")
    void INSERT_PANIC (String PanicButtonPushed , Long Panicker, Long SessionRoom) throws PersistenceException;
//
    @Procedure(name = "GET_PANIC_RESPONSES")
    List<PanicResponse> GET_PANIC_RESPONSE (Long sessionID) throws PersistenceException;
}
