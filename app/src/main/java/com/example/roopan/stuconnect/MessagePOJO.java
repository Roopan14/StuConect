package com.example.roopan.stuconnect;

/**
 * Created by Roopan on 26-01-2017.
 */

public class MessagePOJO {

    String name,message;

    public MessagePOJO()
    {

    }

    public MessagePOJO(String name, String message)
    {
        this.name = name;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

}
