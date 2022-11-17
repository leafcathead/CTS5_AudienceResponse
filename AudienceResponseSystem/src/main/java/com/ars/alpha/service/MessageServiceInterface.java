package com.ars.alpha.service;

import com.ars.alpha.model.Message;

import java.util.Map;

public interface MessageServiceInterface {

    Map<String, Object> postComment(Long posterID, Long sessionID, String message, Long MesageId);

    Map<String, Object> postReply(Long posterID, Long sessionID, Long repliedToMessageID, String message);

    Map<String, Object> getMessages(Long sessionID);
    public int changeMessageApproval();

}
