package com.example.roopan.stuconnect;

/**
 * Created by Roopan on 25-12-2016.
 */
public class UserPojo {

    private String userName;
    private String eMail;
    private String uId;

    public UserPojo()
    {
        // empty reqd
    }

    public String getUid() {
        return uId;
    }

    public void setUid(String uid) {
        this.uId = uid;
    }

    public UserPojo(String userName, String eMail, String Uid)
    {
        this.userName = userName;
        this.eMail = eMail;
        this.uId = Uid;
    }

    /*public UserPojo(String userName, String eMail)
    {
        this.userName = userName;
        this.eMail = eMail;
    }*/

    public String geteMail() {
        return eMail;
    }

    public String getUserName() {
        return userName;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
