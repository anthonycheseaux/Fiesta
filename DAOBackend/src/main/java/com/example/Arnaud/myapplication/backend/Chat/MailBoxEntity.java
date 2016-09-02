package com.example.Arnaud.myapplication.backend.Chat;

import com.google.appengine.api.mail.MailService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by darle on 02.09.2016.
 */

@Entity
public class MailBoxEntity {

    @Id
    private String id;
    public String getId(){return id;}

    private List<Ref<MessageEntity>> content;
    public List<MessageEntity> getContent(){
        List<MessageEntity> respons = new ArrayList<>(content.size());
        for (Iterator<Ref<MessageEntity>> iterator = content.iterator(); iterator.hasNext();)
            respons.add(iterator.next().get());
        return respons;
    }


    public MailBoxEntity(){

    }

    public MailBoxEntity(String id){
        this.id = id;
        this.content = new ArrayList<>();
    }


    public void addMessage(MessageEntity message){
        this.content.add(Ref.create(message));
    }

}
