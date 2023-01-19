package com.ars.alpha.controller;

import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.other.Password;
import com.ars.alpha.service.SessionService;
import com.ars.alpha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/session")
public class SessionRoomController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;


    /**
     * Creates both a session and a user within the database
     *
     * @return JSON Mapping of the new session and user ID in the form of:
     * {
     * "newUserID": <Long>,
     * "randomPassword": <Password String for Session>,
     * "newSessionID": <Long>
     * }
     */
    @GetMapping("/createSession")
    Map<String, Object> createSession() {
        return sessionService.createSession();
    }

    /**
     * @param password Special object to handle mapping from JSON into String. POST in the form of:
     *                 {
     *                 "password": <sample password here>
     *                 }
     * @return JSON object that contains the new User ID and the session ID that they wish to join. If the session does not exist an ID of 0 is returned.
     * {
     * "newSessionID": <Long>,
     * "newUserID": <Long>
     * }
     */
    @PostMapping("/joinSession")
    Map<String, Long> joinSession(@RequestBody Password password) {

        return sessionService.joinSession(password.getPassword());
    }

    /**
     * @param session JSON Object in the form
     *                {
     *                "id": <Long> (Of session)
     *                }
     * @return JSON Object in the form:
     * {
     * "Status": <ERROR, WARNING, SUCCESS>,
     * "Code": <int>
     * }
     */
    @PostMapping("/closeSession")
    Map<String, Object> closeSession(@RequestBody SessionRoom session) {
        System.out.println("Closing session: " + session.getID());

        return sessionService.closeSession(session.getID(), null);
    }

    /**
     * @param session JSON Object in the form:
     *                {
     *                "id": <Long> (Of Session)
     *                }
     * @return boolean value. If TRUE, the session is still active. If FALSE, the session is closed.
     */
    @PostMapping("/checkSessionStatus")
    boolean checkStatus(@RequestBody SessionRoom session) {
        return sessionService.checkSessionStatus(session.getID());
    }


}
