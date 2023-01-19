package com.ars.alpha.controller;

import com.ars.alpha.model.PanicResponse;
import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.service.PanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
@RequestMapping("/panic")
public class PanicController {

    static final String GET_PANIC_PATH = "/topic/retrievePanic";
    @Autowired
    PanicService panicService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    /**
     * @param newPanic JSON Object of the form:
     *                 {
     *                 "panicType": {
     *                 "panicType": <4 Char String> (See DB for more information on acceptable formats)
     *                 },
     *                 "panicker": {
     *                 "id": <Long>
     *                 },
     *                 "session": {
     *                 "id": <Long>
     *                 }
     *                 }
     * @return JSON Object in the form:
     * {
     * "Status": <SUCCESS, WARNING, ERROR>,
     * "Code": <int>
     * }
     * @throws Exception Generic exception
     */
    @PostMapping("/postPanic")
    Map<String, Object> postPanic(@RequestBody PanicResponse newPanic) throws Exception {

        if (newPanic.checkOverSize()) {
            throw new IllegalArgumentException("Panic type not accepted");
        }


        Map<String, Object> returnerMap = new HashMap<String, Object>();

        returnerMap = panicService.postPanic(newPanic.getPanicType().getPanicType(), newPanic.getPanicker().getId(), newPanic.getSession().getID());
        messagingTemplate.convertAndSendToUser(Long.toString(newPanic.getSession().getID()), GET_PANIC_PATH, getResponses(newPanic.getSession()));
        return returnerMap;
    }


    /**
     * @param s a JSON Object in the format:
     *          {
     *          "id": <Long>
     *          }
     * @return a JSON object in the form:
     * {
     * "Status": <SUCCESS, WARNING, ERROR>,
     * "Code": <int>,
     * "PanicResponse":
     * [
     * {
     * "id": <Long>,
     * "panicker": <SessionUser>,
     * "panicType": <PanicButton>,
     * "session": <SessionRoom>,
     * "timestamp": <java.sql.timestamp>
     * }
     * ]
     * }
     * @Note: PanicResponse is actually an array this time. I figured out how to do it like that. Don't ask me to change the other one though!
     */
    @PostMapping("/getPanicResponses")
    Map<String, Object> getResponses(@RequestBody SessionRoom s) {

        if (s.getID() == null) {
            throw new IllegalArgumentException("Cannot be null");
        }

        return panicService.getPanicResponses(s.getID());
    }

}
