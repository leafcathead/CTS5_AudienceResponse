package com.ars.alpha.controller;


import com.ars.alpha.dao.TestStudentRepository;
import com.ars.alpha.exception.TestStudentStoredProcedureException;
import com.ars.alpha.model.TestStudent;
import com.ars.alpha.service.TestStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ARSRestController {

//    @GetMapping("/")
//    String tester () {
//        System.out.println("Input received");
//        return "Hello world";
//    }

    @Autowired
    private TestStudentService testStudentService;

    @GetMapping("/")
    ModelAndView welcome () {
        System.out.println("Someone visited our website!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }


    @GetMapping("/testAddStudent")
    int testAddStudent() {

        System.out.println("Received");
        try {
            String displayName = "Jimmy John";
            testStudentService.addStudent(displayName);

            if (false) {
                throw new SQLException();
            }

        } catch (SQLException e) {

            System.out.println("Uh-oh");
            return 1;
        }

        return 0;
    }

    @GetMapping("/testAddStudentSPROC")
    int testAddStudentSPROC() {

        System.out.println("Received");
        try {
            String displayName = "francis";
            testStudentService.addStudentBySPROC(displayName);

            if (false) {
                throw new SQLException();
            }

        } catch (SQLException e) {

            System.out.println("Uh-oh");
            return 1;
        } catch(TestStudentStoredProcedureException e) {

            System.out.println("Procedure bug");
            return 500;
        }

        return 0;
    }

    @PostMapping(value="/testPost")
    int testingPost(@RequestBody TestStudent student) {
        System.out.println("Post detected");
        System.out.println(student);
        System.out.println(student.getDisplayName());
        return 0;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    ModelAndView pageNotFound () {
        System.out.println("404: Page Not Found");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404.html");
        return modelAndView;
    }

}
