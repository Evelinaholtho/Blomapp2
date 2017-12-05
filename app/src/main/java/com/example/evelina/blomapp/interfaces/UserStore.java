package com.example.evelina.blomapp.interfaces;

import com.example.evelina.blomapp.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Evelina on 2017-11-15.
 */

public interface UserStore {

//  List<User> getAllUsers();
    Map<String, String> getAllUsersFromHashMap();
    void addUser(User user);
  //  void fillUserList();
    void fillHashMap();
}
