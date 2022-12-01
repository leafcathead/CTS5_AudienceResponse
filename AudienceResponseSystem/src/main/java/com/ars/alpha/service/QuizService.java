package com.ars.alpha.service;

import com.ars.alpha.dao.QuizRepository;
import com.ars.alpha.dao.QuizResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService implements QuizServiceInterface {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizResponseRepository quizResponseRepository;
}
