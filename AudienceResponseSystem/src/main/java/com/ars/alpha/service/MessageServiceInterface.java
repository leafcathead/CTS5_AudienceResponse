package com.ars.alpha.service;

import java.util.Map;

public interface MessageServiceInterface {

    Map<String, Object> postComment(Long posterID, Long sessionID, String message);
    public int changeMessageApproval();

}
