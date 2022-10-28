package com.ars.alpha.service;

import java.util.Map;

public interface SessionServiceInterface {


    public Map<String, Object> createSession();

    public Map<String, Long> joinSession(String password);
    public int closeSession();

}
