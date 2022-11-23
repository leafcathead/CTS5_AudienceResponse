package com.ars.alpha.service;

import java.util.Map;

public interface UserServiceInterface {

    Map<String, Object> updateDisplayName(Long userID, Long sessionID, String newName);

}
