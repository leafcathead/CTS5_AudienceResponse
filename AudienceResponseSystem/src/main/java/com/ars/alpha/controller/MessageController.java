package com.ars.alpha.controller;

import com.ars.alpha.model.Liked;
import com.ars.alpha.model.Message;
import com.ars.alpha.model.SessionRoom;
import com.ars.alpha.other.Status;
import com.ars.alpha.service.MessageService;
import org.assertj.core.error.future.Warning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"https://rhit-r90y2r8w/"})
@RequestMapping("/message")
//@SendTo("/topic/message")
public class MessageController {

    static final String GET_MESSAGE_PATH = "/topic/retrieveMessages";

    @Autowired
    private MessageService messageService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

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
     *                   "messageContent": <String>
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
    Map<String, Object> postComment (@RequestBody Message newComment) throws Exception{

        if(newComment.checkOverSize()){
            throw new IllegalArgumentException("You cannot send a message longer than 1024 characters.");
        }
        Map<String, Object> returnerMap = new HashMap<String, Object>();
      //  System.out.println(newComment.toString());
        returnerMap = messageService.postComment(newComment.getPoster().getId(), newComment.getSession().getID(), newComment.getMessageContents(), 0L);
        messagingTemplate.convertAndSendToUser(Long.toString(newComment.getSession().getID()), GET_MESSAGE_PATH, getMessages(newComment.getSession()));
        return returnerMap;
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
     * @return JSON object in the form
     *      {
     *          "Status": <SUCCESS, WARNING, ERROR>
     *          "MessageID": <Long>,
     *          "Code": <int>
     *      }
     */
    @PostMapping("/postReply")
    Map<String, Object> postReply(@RequestBody Message newReply) {
        System.out.println(newReply.toString());

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        returnerMap = messageService.postReply(newReply.getPoster().getId(), newReply.getSession().getID(), newReply.getReplyTo().getId(), newReply.getMessageContents());
        messagingTemplate.convertAndSendToUser(Long.toString(newReply.getSession().getID()), GET_MESSAGE_PATH, getMessages(newReply.getSession()));
        return returnerMap;
    }

    /**
     *
     *
     * @param session
     *      {
     *          "id": <Long>
     *      }
     *
     *
     * @return a Complicated JSON object. Unnecessary information in JSON object omitted.
     *      {
     *          "Status": <ERROR, WARNING, SUCCESS>,
     *          "Code": <int>,
     *          "Messages": {
     *             "0": {
     *                  "id": <Long>,
     *                  "poster": {
     *                      "id": <Long>
     *                  },
     *                  "replyTo": {
     *                      "id": <Long>
     *                  },
     *                  "timestamp": <Timestamp>,
     *                  "visible": <boolean>,
     *                  "likes": <int>,
     *                  "messageContents": <String>
     *             },
     *             ...
     *          }
     *      }
     *
     */
   //Must be post type JS can't send GET request with body!!!
        // LAME! -Connor
    @PostMapping("/getMessages")
    public @ResponseBody Map<String, Object> getMessages(@RequestBody SessionRoom session) {

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

    /**
     *
     * @param m JSON object in the form of:
     *          {
     *              "id": <Long>,
     *              "poster": {
     *                  "id": <Long>
     *              },
     *              "session": {
     *                  "id": <Long>
     *              },
     *              "messageContent": <String>
     *          }
     * @return JSON object in the form
     *           {
     *              "Status": <SUCCESS, WARNING, ERROR>,
     *              "Code": <int>
     *           }
     */
    @PutMapping("/updateMessageContent")
    Map<String, Object> updateMessageContent(@RequestBody Message m) {
        System.out.println("Updating message...");

        if (m.getMessageContents() == null) {
            Map<String, Object> returnerMap = new HashMap<String, Object>();
            returnerMap.put("Status", Status.WARNING);
            returnerMap.put("Code", 99);
            return returnerMap;
        };

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        returnerMap = messageService.updateMessageContent(m.getId(), m.getPoster().getId(), m.getSession().getID(), m.getMessageContents());
        messagingTemplate.convertAndSendToUser(Long.toString(m.getSession().getID()), GET_MESSAGE_PATH, getMessages(m.getSession()));
        return returnerMap;
    }

    /**
     *
     * @param m JSON Object in the form:
     *          {
     *              "id": <Long>,
     *              "poster": {
     *                  "id": <Long>
     *              },
     *              "session": {
     *                  "id": <Long>
     *              }
     *          }
     * @return JSON Object formatted like this:
     *          {
     *              "Status": <SUCCESS, WARNING, ERROR>,
     *              "Code": <int>
     *          }
     */
    @PutMapping("/updateVisibility")
    Map<String, Object> updateMessageVisibility(@RequestBody Message m) {

        // We don't need the new value of the visibility, calling this method just flips it.

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        returnerMap = messageService.updateMessageVisibility(m.getId(), m.getPoster().getId(), m.getSession().getID());
        messagingTemplate.convertAndSendToUser(Long.toString(m.getSession().getID()), GET_MESSAGE_PATH, getMessages(m.getSession()));
        return returnerMap;
    }

    /**
     *
     * @param delComment Message Object in JSON form
     *          {
     *              "id": <Long>,
     *              "poster": {
     *                  "id": <Long>
     *              },
     *              "session": {
     *                  "id": <Long>
     *              }
     *          }
     *
     * @return A JSON Object in the form of:
     *          {
     *              "Status": <SUCCESS, WARNING, ERROR>,
     *              "Code": <int>
     *          }
     */
    @DeleteMapping("/deleteMessage")
    Map<String, Object> deleteComment(@RequestBody Message delComment){
        Map<String, Object> returnerMap = new HashMap<String, Object>();

        returnerMap = messageService.deleteComment(delComment.getPoster().getId(), delComment.getSession().getID(), delComment.getId());
        messagingTemplate.convertAndSendToUser(Long.toString(delComment.getSession().getID()), GET_MESSAGE_PATH, getMessages(delComment.getSession()));
        return returnerMap;
    }

    /**
     *
     * @param like JSON Object of the form
     *             {
     *                  "liker": {
     *                      "id": <Long>
     *                  },
     *                  "likedMessage": {
     *                      "id": <Long>
     *                  }
     *             }
     * @return a JSON Object of the form:
     *          {
     *              "Status": <SUCCESS, WARNING, ERROR>,
     *              "Code": <int>,
     *              "Liked": <boolean> // Likes are toggled just like visibility. This communicates the new status.
     *          }
     */
    @PutMapping("/likeMessage")
    Map<String, Object> likeMessage(@RequestBody Liked like) {

        Map<String, Object> returnerMap = new HashMap<String, Object>();

        returnerMap = messageService.likeMessage(like.getLikedMessage().getId(), like.getLiker().getId());
       // messagingTemplate.convertAndSendToUser(Long.toString(.getSession().getID()), GET_MESSAGE_PATH, getMessages(m.getSession()));
        return returnerMap;
    }
}
