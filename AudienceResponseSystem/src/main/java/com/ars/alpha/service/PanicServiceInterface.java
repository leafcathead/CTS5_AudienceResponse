package com.ars.alpha.service;

import java.sql.Timestamp;
import java.util.Map;

public interface PanicServiceInterface {

    Map<String, Object> postPanic(Long ID/* Long PanicButtonPushed,*/, Long Panicker, Long SessionRoom, String LogTime);
}
