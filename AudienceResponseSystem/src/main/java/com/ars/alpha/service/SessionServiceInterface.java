package com.ars.alpha.service;

import java.util.Map;

public interface SessionServiceInterface {


    public Map<String, Object> createSession();
    public int closeSession();

}
