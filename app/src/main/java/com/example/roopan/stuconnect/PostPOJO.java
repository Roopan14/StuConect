package com.example.roopan.stuconnect;

/**
 * Created by Roopan on 05-01-2017.
 */

public class PostPOJO {

    String post,name,time;

    public PostPOJO()
    {
        //empty reqd.
    }

    public PostPOJO(String post, String name, String time)
    {
        this.name = name;
        this.post = post;
        this.time = time;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
