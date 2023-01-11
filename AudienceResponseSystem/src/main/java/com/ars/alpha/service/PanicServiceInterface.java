package com.ars.alpha.service;

import java.sql.Timestamp;
import java.util.Map;

public interface PanicServiceInterface {

    Map<String, Object> postPanic(String buttonPushed, Long Panicker, Long SessionRoom);

    Map<String, Object> getPanicResponses(Long sessionID);
}
