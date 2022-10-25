package com.ars.alpha.dao;

import com.ars.alpha.exception.TestStudentStoredProcedureException;
import com.ars.alpha.model.TestStudent;
import org.hibernate.HibernateException;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.sql.SQLException;

@Repository
public interface TestStudentRepository extends JpaRepository<TestStudent, Long> {

    @Procedure
    void addStudent(String displayName) throws PersistenceException;
}