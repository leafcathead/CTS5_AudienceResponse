package com.ars.alpha.dao;

import com.ars.alpha.model.PanicResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface PanicRepository extends JpaRepository<PanicResponse, Long> {
//    @Procedure(name = "INSERT_PANIC")
//    Long INSERT_PANIC (Long ID/*, Long PanicButtonPushed*/, Long Panicker, Long SessionRoom, String LogTime) throws PersistenceException;
//
}
