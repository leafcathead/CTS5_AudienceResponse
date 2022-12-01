package com.ars.alpha.dao;

import com.ars.alpha.model.QuizResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResponseRepository extends JpaRepository<QuizResponse, Long> {
}
