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

    /**
     * Inserts a Panic Response into the database
     * @param PanicButtonPushed 4 Character string abbreviation of the Panic Button pushed. Available options seen in the DB
     * @param Panicker ID field of the SessionUser class
     * @param SessionRoom ID field of the SessionRoom class
     * @throws PersistenceException If an error occurs in the Database, it will throw a SQLGrammarException which is of type PersistenceException
     */
    @Procedure(name = "INSERT_PANIC")
    void INSERT_PANIC (String PanicButtonPushed , Long Panicker, Long SessionRoom) throws PersistenceException;

    /**
     * Returns all the PanicResponse objects for a specific session in the DB
     * @param sessionID ID field of the SessionRoom class
     * @return List<PanicRespnse> List of all PanicResponse objects for that session.
     * @throws PersistenceException If an error occurs in the Database, it will throw a SQLGrammarException which is of type PersistenceException
     */
    @Procedure(name = "GET_PANIC_RESPONSES")
    List<PanicResponse> GET_PANIC_RESPONSE (Long sessionID) throws PersistenceException;
}
