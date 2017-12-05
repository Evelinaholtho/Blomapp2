package com.example.evelina.blomapp;

import android.app.usage.UsageEvents;

import java.util.ArrayList;

/**
 * Created by Evelina on 2017-11-13.
 */

public class User {
    private String name;
    private String username;
    private  String password;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;

    }

    public String getName(){
        return name;
    }

    public String getUsername(){
        return username;
    }

     public  String getPassword(){
        return password;
     }

    public void setName(String name){
         this.name=name;
    }

    public void setUsername (String username){
        this.name =name;
    }

    public void setPassword (String password){
        this.password = password;
    }

}






