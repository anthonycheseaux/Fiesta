package com.example.Arnaud.myapplication.backend.Chat;

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
public class MessageBoxEntity {

    @Id
    private String id;
    public String getId(){return id;}

    private List<Ref<Message>> content;
    public List<Message> getContent(){
        List<Message> respons = new ArrayList<>(content.size());
        for (Iterator<Ref<Message>> iterator = content.iterator(); iterator.hasNext();)
            respons.add(iterator.next().get());
        return respons;
    }


    public MessageBoxEntity(){

    }

    public MessageBoxEntity(String id){
        this.id = id;
        this.content = new ArrayList<>();
    }


    public void addMessage(Message message){
        this.content.add(Ref.create(message));
    }

}
