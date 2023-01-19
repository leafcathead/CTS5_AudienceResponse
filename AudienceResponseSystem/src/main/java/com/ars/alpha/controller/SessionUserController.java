package com.ars.alpha.controller;

import com.ars.alpha.model.SessionUser;
import com.ars.alpha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
@RequestMapping("/user")
public class SessionUserController {

    @Autowired
    UserService userService;

    /**
     * @param su Json object in the form:
     *           {
     *           "id": <Long>,
     *           "displayName": <String>,
     *           "session": {
     *           "id": <Long>
     *           }
     *           }
     * @return JSON object in the form:
     * {
     * "Status": <SUCCESS, WARNING, ERROR>,
     * "Code": <int>
     * }
     */
    @PutMapping("/updateDisplayName")
    Map<String, Object> updateMessageContent(@RequestBody SessionUser su) {
        return userService.updateDisplayName(su.getId(), su.getSession().getID(), su.getDisplayName());
    }

}
