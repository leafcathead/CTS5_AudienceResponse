package com.ars.alpha.controller;

import com.ars.alpha.dao.TestStudentDAO;
import com.ars.alpha.dao.TestStudentRepository;
import com.ars.alpha.model.TestStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
    private TestStudentRepository testStudentRepo;

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
            List<TestStudent> myList = testStudentRepo.findAll();
            System.out.println(myList.size());
            for (TestStudent tester : myList) {
                System.out.println(tester.getDisplayName());
            }

            if (false) {
                throw new SQLException();
            }

        } catch (SQLException e) {

            System.out.println("Uh-oh");
            return 1;
        }

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
