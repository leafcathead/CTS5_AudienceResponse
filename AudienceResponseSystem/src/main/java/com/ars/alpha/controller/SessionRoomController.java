package com.ars.alpha.controller;

import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.model.SessionUser;
import com.ars.alpha.model.TestStudent;
import com.ars.alpha.service.SessionService;
import com.ars.alpha.service.TestStudentService;
import com.ars.alpha.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"},methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
@RequestMapping("/session")
public class SessionRoomController {

    @Autowired
    private TestStudentService testStudentService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;


    @GetMapping("/createSession")
    Map<String, Object> createSession() {
        System.out.println("Create session listener...");
        return sessionService.createSession();
    }

    @PostMapping("/joinSession")
    Map<String, Object> joinSession() {
        Map<String, Object> myMap = null;

        return myMap;
    }



    @CrossOrigin
    @GetMapping("/testPost")
    SessionUser testingPost(@RequestBody TestStudent student) {
        System.out.println("Post detected");
        System.out.println(student);
        System.out.println(student.getDisplayName());

        SessionUser newUser = new SessionUser();
        return newUser;
    }

}
