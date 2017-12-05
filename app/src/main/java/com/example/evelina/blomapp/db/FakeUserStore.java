package com.example.evelina.blomapp.db;

import android.content.Context;
import android.util.Log;

import com.example.evelina.blomapp.User;
import com.example.evelina.blomapp.interfaces.UserStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Evelina on 2017-11-13.
 */

public class FakeUserStore implements UserStore {

    private static final String LOG_TAG = FakeUserStore.class.getSimpleName();
    private Map <String, String > userHashmap = new HashMap<>();

    {
        Log.d(LOG_TAG, "FakeUserStore constructor");
        Log.d(LOG_TAG, "FakeUserStore constructor size: " + userHashmap.size());

    }

    @Override
    public void addUser(User user) {
        Log.d(LOG_TAG, "addUser():  " + userHashmap.size());
        userHashmap.put(user.getUsername(),user.getPassword());
        Log.d(LOG_TAG, "addUser():  " + userHashmap.size());
    }

    public  FakeUserStore (Context context){
        Log.d(LOG_TAG, "fakeUserStore()");

    }

    @Override
    public Map <String, String> getAllUsersFromHashMap(){
        Log.d(LOG_TAG, "getAllUsersFromHashMap size: " + userHashmap.size());
        return userHashmap;
    }
    @Override
    public void fillHashMap() {
        userHashmap.put("Adas", "qwerty");
        userHashmap.put("E", "1");
        Log.d(LOG_TAG, "fillHashMap size: " + userHashmap.size());
    }
}



