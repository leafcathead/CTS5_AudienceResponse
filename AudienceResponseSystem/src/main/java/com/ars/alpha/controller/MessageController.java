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

        System.out.println(newComment.toString());

        return messageService.postComment(newComment.getPoster().getId(), newComment.getSession().getID(), newComment.getMessageContents(), 0L);
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

        return messageService.postReply(newReply.getPoster().getId(), newReply.getSession().getID(), newReply.getReplyTo().getId(), newReply.getMessageContents());
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
     *      ALTERNATE JSON RESPONSE BODY:
     *      {
     *     "Status": <SUCCESS, WARNING, ERROR>,
     *     "Messages": {
     *         "0": {
     *             "visible": <boolean>,
     *             "replyTo": <Long or NULL>,
     *             "id": <Long>,
     *             "sessionOwnerID": <Long>,
     *             "Timestamp": <Timestamp>,
     *             "posterID": <Long>,
     *             "posterDisplayName": <String>,
     *             "messageContent": <String>,
     *             "likes": <int>
     *         }
     *     "Code": <int>
     */
   //Must be post type JS can't send GET request with body!!!
        // LAME! -Connor
    @PostMapping("/getMessages")
    public @ResponseBody Map<String, Object> getMessages(@RequestBody SessionRoom session) {
        System.out.println(session.toString());
        return messageService.getMessages(session.getID());
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
        return messageService.updateMessageContent(m.getId(), m.getPoster().getId(), m.getSession().getID(), m.getMessageContents());
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

        return messageService.updateMessageVisibility(m.getId(), m.getPoster().getId(), m.getSession().getID());
    }




}
