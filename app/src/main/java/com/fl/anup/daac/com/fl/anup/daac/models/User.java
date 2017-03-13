package com.fl.anup.daac.com.fl.anup.daac.models;

/**
 * Created by Anup on 3/14/2017.
 */

public class User {

    private String userName;

    private String eMail;

    private String phoneNum;

    private String userType;

    private String userId;

    public User() {

    }

    public User(String userName, String eMail, String phoneNum, String userType) {
        this.userName = userName;
        this.eMail = eMail;
        this.phoneNum = phoneNum;
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
