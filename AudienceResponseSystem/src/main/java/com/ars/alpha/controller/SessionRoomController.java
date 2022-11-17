package com.ars.alpha.controller;

import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.model.SessionUser;
import com.ars.alpha.model.TestStudent;
import com.ars.alpha.other.Password;
import com.ars.alpha.service.SessionService;
import com.ars.alpha.service.TestStudentService;
import com.ars.alpha.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
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


    /**
     * Creates both a session and a user within the database
     * @return JSON Mapping of the new session and user ID in the form of:
     *  {
     *     "newUserID": <Long>,
     *     "randomPassword": <Password String for Session>,
     *     "newSessionID": <Long>
     *  }
     */
    @GetMapping("/createSession")
    Map<String, Object> createSession() {
        System.out.println("Create session listener...");
        return sessionService.createSession();
    }

    /**
     *
     * @param password Special object to handle mapping from JSON into String. POST in the form of:
     * {
     *       "password": <sample password here>
     * }
     *
     * @return JSON object that contains the new User ID and the session ID that they wish to join. If the session does not exist an ID of 0 is returned.
     *{
     *       "newSessionID": <Long>,
     *       "newUserID": <Long>
     * }
     */
    @PostMapping("/joinSession")
    Map<String, Long> joinSession(@RequestBody Password password) {
        System.out.println();
        System.out.println("Join session");
        System.out.println("Password: " + password.getPassword());

        return sessionService.joinSession(password.getPassword());
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
