package com.example.evelina.blomapp.db;

import android.content.Context;

import com.example.evelina.blomapp.User;
import com.example.evelina.blomapp.interfaces.UserStore;

/**
 * Created by Evelina on 2017-11-22.
 */

public class UserStoreFactory {

    static UserStore store;


    public static  UserStore getInstance(Context c){
        if (store == null )
        {
            store = new FakeUserStore(c);
            store.fillHashMap();

        }
        return store;

    }


}
