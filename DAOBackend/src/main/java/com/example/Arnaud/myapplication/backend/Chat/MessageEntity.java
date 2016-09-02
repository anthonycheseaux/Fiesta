package com.example.Arnaud.myapplication.backend.Chat;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by darle on 02.09.2016.
 */
@Entity
public class MessageEntity {

    @Id
    private Long id;
    public Long getId() {return id;}

    private String text;
    private String receiver;
    private String sender;

    public MessageEntity(String text, String receiver, String sender){
        this.text = text;
        this.receiver = receiver;
        this.sender = sender;
    }

}
