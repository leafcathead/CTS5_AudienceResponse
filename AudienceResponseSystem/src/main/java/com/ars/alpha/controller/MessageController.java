package com.ars.alpha.controller;

import com.ars.alpha.model.Message;
import com.ars.alpha.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"})
@RequestMapping("/message")
public class MessageController {

    private MessageService messageService;

    /**
     *
     * @param newComment JSON object in the form of:
     *       {
     *                   "poster": {
     *                      "id": <Long>,
     *                      "session": {
     *                          "id": <Long>
     *                      }
     *                   }
     *                   "messageContents": <String>
     *       }
     *
     *
     * @return TODO
     */
    @PostMapping("/postComment")
    Map<String, Object> postComment(@RequestBody Message newComment) {
        return messageService.postComment(newComment.getPoster().getId(), newComment.getSession().getID(), newComment.getMessageContents());
    }

    @PostMapping("/postReply")
    Map<String, Object> postReply() {


        return null;
    }



}
