package com.example.Arnaud.myapplication.backend.Chat;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

import java.util.Date;

/**
 * Created by darle on 02.09.2016.
 */
@Entity
public class Message {
    @Id
    private Long id;
    private String text;
    private String receiver;
    private String sender;
    private Date dateMessage;

    public String getText() {
        return text;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    public Message(String text, String receiver, String sender, Date dateMessage){
        this.text = text;
        this.receiver = receiver;
        this.sender = sender;
        this.dateMessage = dateMessage;
    }
    public Message(){

    }




}
