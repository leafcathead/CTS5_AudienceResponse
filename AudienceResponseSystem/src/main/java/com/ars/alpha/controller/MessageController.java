package com.ars.alpha.controller;

import com.ars.alpha.model.Message;
import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"})
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     *
     * @param newComment JSON object in the form of:
     *       {
     *                   "poster": {
     *                      "id": <Long>
     *                   },
     *                   "session": {
     *                      "id": <Long>
     *                   },
     *                   "messageContents": <String>
     *       }
     *
     *
     * @return JSON object in the form
     *      {
     *          "Status": <SUCCESS, WARNING, ERROR>
     *          "MessageID": <Long>,
     *          "Code": <int>
     *      }
     */
    @PostMapping("/postComment")
    Map<String, Object> postComment(@RequestBody Message newComment) {

        System.out.println(newComment.toString());

        return messageService.postComment(newComment.getPoster().getId(), newComment.getSession().getID(), newComment.getMessageContents());
    }

    /**
     *
     * @param newReply
     *       {
     *                   "poster": {
     *                      "id": <Long>
     *                   },
     *                   "session": {
     *                      "id": <Long>
     *                   },
     *                   "replyTo": {
     *                      "id": <Long>
     *                   },
     *                   "messageContent": <String>
     *       }
     * @return TODO
     */
    @PostMapping("/postReply")
    Map<String, Object> postReply(@RequestBody Message newReply) {
        System.out.println(newReply.toString());

        return messageService.postReply(newReply.getPoster().getId(), newReply.getSession().getID(), newReply.getReplyTo().getId(), newReply.getMessageContents());
    }

    /**
     *
     *
     * @param session
     *      {
     *          "session": {
     *              "id": <Long>
     *          }
     *      }
     *
     *
     * @return TODO
     */
    @GetMapping("/getMessages")
    Map<String, Object> getMessages(@RequestBody SessionRoom session) {

        return messageService.getMessages(session.getID());
    }



}
