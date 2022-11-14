package com.ars.alpha.dao;

import com.ars.alpha.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Procedure(name = "INSERT_MESSAGE")
    Map<String, Object> INSERT_MESSAGE(Long newSessionID, Long newUserID, String Message);
}