package com.ars.alpha.service;

import com.ars.alpha.model.SessionRoom;

import java.util.Map;

public interface UserServiceInterface {

    Map<String, Object> updateDisplayName(Long userID, Long sessionID, String newName);

    SessionRoom getSessionRoomByID(Long userID);

}
