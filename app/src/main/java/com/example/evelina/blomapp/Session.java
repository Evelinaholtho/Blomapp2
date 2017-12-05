package com.example.evelina.blomapp;

/**
 * Created by Evelina on 2017-11-27.
 */

public class Session {

    private static Session session;

    public User currentUser;

    // private constructor to prevent other from creating Session objects
    private Session() {
    };

    static {
        session = new Session();
    }

    public static Session getInstance() {
        return session;
    }

}
