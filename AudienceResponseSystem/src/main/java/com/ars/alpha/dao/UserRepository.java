package com.ars.alpha.dao;

import com.ars.alpha.model.SessionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SessionUser,Long> {

}
