package com.example.prateek.spotmate;

/**
 * Created by prateek on 11/6/16.
 */
public class User {

    String name, password, username, mobile;

    public User(String name, String username, String mobile, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.mobile = mobile;
    }

    public User(String username, String password) {
        this("",username, "", password);
    }
}