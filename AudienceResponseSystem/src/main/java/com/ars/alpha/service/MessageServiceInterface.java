package com.ars.alpha.service;

import java.util.Map;

public interface MessageServiceInterface {

    Map<String, Object> postComment(Long posterID, Long sessionID, String message);

    Map<String, Object> postReply(Long posterID, Long sessionID, Long repliedToMessageID, String message);
    public int changeMessageApproval();

}
