package com.example.smartcity.backend.entity;

/**
 *  Represent a User object with name and password attributes, which corresponds
 *  to user properties stored in Firebase.
 *
 */
public class User {

    // global variable used to track current login user
    private static User loginUser = new User();
    private String name;
    private String pwd;

    public User() {
    }

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    /**
     * Return the singleton instance representing the currently logged-in user.
     * @return the global variable loginUser
     */
    public static User getInstance() {
        return loginUser;
    }

    /**
     * update the login user state
     * @param name the login user's name
     */
    public void updateLoginUserState(String name) {
        loginUser.setName(name);
    }

    public String getLoginUserName() {
        return loginUser.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
