package com.ars.alpha.service;

import com.ars.alpha.dao.TestStudentRepository;
import com.ars.alpha.exception.TestStudentStoredProcedureException;
import com.ars.alpha.model.TestStudent;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.sql.SQLException;

@Service
public class TestStudentService implements TestStudentInterface{

    @Autowired
    private TestStudentRepository testStudentRepo;

    @Override
    public int addStudent(String displayName) {
        testStudentRepo.save(new TestStudent(displayName));
        return 0;
    }

    @Override
    public int addStudentBySPROC(String displayName) throws TestStudentStoredProcedureException {
        try {
            testStudentRepo.addStudent(displayName);

        } catch (PersistenceException e) {
            System.out.println("I've caught you!");
            e.getCause();
        }

        return 0;
    }

    @Override
    public int deleteStudent() {
        return 0;
    }

    @Override
    public int updateStudent() {
        return 0;
    }
}
