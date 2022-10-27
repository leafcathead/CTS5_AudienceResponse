package com.ars.alpha.controller;

import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.model.SessionUser;
import com.ars.alpha.model.TestStudent;
import com.ars.alpha.service.SessionService;
import com.ars.alpha.service.TestStudentService;
import com.ars.alpha.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionRoomController {

    @Autowired
    private TestStudentService testStudentService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;


    @GetMapping("/createNewSession")
    Map<String, Object> createSession() {
        Map<String, Object> myMap = new HashMap<String, Object>();
        myMap.put("User", new SessionUser());
        myMap.put("Session", new SessionRoom());

        return myMap;
    }



    @PostMapping(value="/testPost")
    SessionUser testingPost(@RequestBody TestStudent student) {
        System.out.println("Post detected");
        System.out.println(student);
        System.out.println(student.getDisplayName());

        SessionUser newUser = new SessionUser();
        return newUser;
    }

}
