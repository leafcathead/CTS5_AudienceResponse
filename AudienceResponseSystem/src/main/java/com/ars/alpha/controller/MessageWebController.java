package com.ars.alpha.controller;

import com.ars.alpha.model.Message;
import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.other.Status;
import com.ars.alpha.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MessageWebController {

    @Autowired
    MessageService messageService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/postComment")
    @SendTo("/topic/webMessage")
    public Map<String, Object> postMessage(Message newComment) {
        System.out.println("Got a response here");
        //messagingTemplate.convertAndSendToUser("/topic/webMessage", getMessages(newComment.getSession()));
        getMessages(newComment.getSession());
        return messageService.postComment(newComment.getPoster().getId(), newComment.getSession().getID(), newComment.getMessageContents(), 0L);
    }

    @MessageMapping("/getMessages")
    @SendTo("/topic/webMessage")
    public @ResponseBody Map<String, Object> getMessages(SessionRoom session) {

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        try {
            returnerMap = messageService.getMessages(session.getID());
        } catch (UnexpectedRollbackException e) { // I don't like this. This is a bandage fix.
            returnerMap.put("Status", Status.ERROR);
            returnerMap.put("Code", 99);
            returnerMap.put("Messages", new HashMap<Integer, Message>());
        }

        return returnerMap;

    }

}
