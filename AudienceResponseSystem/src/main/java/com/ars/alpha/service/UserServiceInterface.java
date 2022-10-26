package com.ars.alpha.service;

public interface UserServiceInterface {

    public int bindUserToSession();
    public int validateUserInSession();
    public int removeUserFromSession();
    public int updateUserUsername();

}
