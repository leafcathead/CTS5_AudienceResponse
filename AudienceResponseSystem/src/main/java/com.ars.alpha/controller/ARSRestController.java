package com.ars.alpha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ARSRestController {

//    @GetMapping("/")
//    String tester () {
//        System.out.println("Input received");
//        return "Hello world";
//    }

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
