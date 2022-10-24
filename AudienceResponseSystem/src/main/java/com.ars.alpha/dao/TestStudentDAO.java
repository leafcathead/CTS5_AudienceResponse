package com.ars.alpha.dao;

import com.ars.alpha.model.TestStudent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestStudentDAO extends JpaRepository<TestStudent, Long> {


}
