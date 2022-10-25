package com.ars.alpha.service;

import com.ars.alpha.exception.TestStudentStoredProcedureException;
import com.ars.alpha.model.TestStudent;

import java.sql.SQLException;

public interface TestStudentInterface {

    int addStudent(String displayName);
    int addStudentBySPROC(String displayName) throws TestStudentStoredProcedureException;
    int deleteStudent();
    int updateStudent();

}
