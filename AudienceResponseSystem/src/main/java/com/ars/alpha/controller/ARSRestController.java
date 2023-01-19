package com.ars.alpha.controller;


import com.ars.alpha.service.MessageService;
import com.ars.alpha.service.SessionService;
import com.ars.alpha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@CrossOrigin
public class ARSRestController {


    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;


    /**
     * Returns the index of the site
     *
     * @return index.html
     */
    @GetMapping("/")
    ModelAndView welcome() {
        System.out.println("Someone visited our website!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    /**
     * A secret test site.
     * @return WebSocketTest.html
     */
    @GetMapping("/test")
    ModelAndView test() {
        System.out.println("Someone visited our website!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("WebSocketTest.html");
        return modelAndView;
    }

    /**
     * Returns the current csrf token to prevent Cross-Site Forgery.
     * @param token
     * @return CSRF token
     */
    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }


    /**
     * Returns 404
     * @return 404.html
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ModelAndView pageNotFound() {
        System.out.println("404: Page Not Found");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404.html");
        return modelAndView;
    }

}
