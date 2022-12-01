package com.ars.alpha.controller;

import com.ars.alpha.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"},methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

}
