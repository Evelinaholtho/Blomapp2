package com.example.evelina.blomapp;

/**
 * Created by Evelina on 2017-12-04.
 */

public class SessionPlant {
    private static SessionPlant sessionPlant;

    public Plant currentPlant;

    // private constructor to prevent other from creating Session objects
    private SessionPlant() {
    };

    static {
        sessionPlant = new SessionPlant();
    }

    public static SessionPlant getInstance() {
        return sessionPlant;
    }

}


