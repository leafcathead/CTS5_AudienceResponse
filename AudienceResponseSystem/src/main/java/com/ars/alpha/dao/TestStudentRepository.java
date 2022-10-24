package com.ars.alpha.dao;

import com.ars.alpha.model.TestStudent;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestStudentRepository extends JpaRepository<TestStudent, Long> {
}