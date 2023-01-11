package com.ars.alpha.controller;

import com.ars.alpha.model.PanicResponse;
import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.service.PanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"},methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
@RequestMapping("/panic")
public class PanicController {

    @Autowired
    PanicService panicService;

    /**
     *
     * @param newPanic JSON Object of the form:
     *                 {
     *                      "panicType": {
     *                          "panicType": <4 Char String> (See DB for more information on acceptable formats)
     *                      },
     *                      "panicker": {
     *                          "id": <Long>
     *                      },
     *                      "session": {
     *                          "id": <Long>
     *                     }
     *                 }
     * @return JSON Object in the form:
     *          {
     *              "Status": <SUCCESS, WARNING, ERROR>,
     *              "Code": <int>
     *          }
     * @throws Exception
     */
    @PostMapping("/postPanic")
    Map<String, Object> postPanic (@RequestBody PanicResponse newPanic) throws Exception{

        if(newPanic.checkOverSize()){
            throw new IllegalArgumentException("Panic type not accepted");
        }

        //  System.out.println(newComment.toString());

        return panicService.postPanic(newPanic.getPanicType().getPanicType(), newPanic.getPanicker().getId(), newPanic.getSession().getID());
    }


    /**
     *
     * @param s a JSON Object in the format:
     *          {
     *              "id": <Long>
     *          }
     * @Note: PanicResponse is actually an array this time. I figured out how to do it like that. Don't ask me to change the other one though!
     * @return a JSON object in the form:
     *          {
     *              "Status": <SUCCESS, WARNING, ERROR>,
     *              "Code": <int>,
     *              "PanicResponse":
     *                  [
     *                      {
     *                          "id": <Long>,
     *                          "panicker": <SessionUser>,
     *                          "panicType": <PanicButton>,
     *                          "session": <SessionRoom>,
     *                          "timestamp": <java.sql.timestamp>
     *                      }
     *                  ]
     *          }
     */
    @PostMapping("/getPanicResponses")
    Map<String, Object> getResponses (@RequestBody SessionRoom s) {

        if (s.getID() == null) {
            throw new IllegalArgumentException("Cannot be null");
        }

        return panicService.getPanicResponses(s.getID());
    }

}
