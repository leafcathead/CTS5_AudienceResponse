package com.ars.alpha.controller;


import com.ars.alpha.exception.TestStudentStoredProcedureException;
import com.ars.alpha.service.MessageService;
import com.ars.alpha.service.SessionService;
import com.ars.alpha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@RestController
@CrossOrigin
public class ARSRestController {

//    @GetMapping("/")
//    String tester () {
//        System.out.println("Input received");
//        return "Hello world";
//    }



    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;





    @GetMapping("/")
    ModelAndView welcome () {
        System.out.println("Someone visited our website!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }



    @ResponseStatus(HttpStatus.NOT_FOUND)
    ModelAndView pageNotFound () {
        System.out.println("404: Page Not Found");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404.html");
        return modelAndView;
    }

}
