package com.ars.alpha.dao;

import com.ars.alpha.model.SessionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionRoom, Long> {

}
