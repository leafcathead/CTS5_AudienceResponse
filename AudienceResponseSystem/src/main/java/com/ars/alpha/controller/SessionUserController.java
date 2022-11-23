package com.ars.alpha.controller;

import com.ars.alpha.model.Message;
import com.ars.alpha.model.SessionUser;
import com.ars.alpha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"},methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
@RequestMapping("/user")
public class SessionUserController {

    @Autowired
    UserService userService;

    /**
     *
     * @param su Json object in the form:
     *           {
     *              "id": <Long>,
     *              "name": <String>,
     *              "session": {
     *                  "id": <Long>
     *              }
     *           }
     * @return TODO
     */
    @PutMapping("/updateDisplayName")
    Map<String, Object> updateMessageContent(@RequestBody SessionUser su) {
        System.out.println("Updating display name...");
        return userService.updateDisplayName(su.getId(), su.getSession().getID(), su.getDisplayName());
    }

}
