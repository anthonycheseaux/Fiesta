package com.example.Arnaud.myapplication.backend.Chat;

import com.google.appengine.api.mail.MailService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darle on 02.09.2016.
 */

@Entity
public class MailBoxEntity {

    @Id
    private Long id;
    public Long getId() {return id;}

    private List<MessageEntity> incomeMessages;
    private List<MessageEntity> outcomeMessages;
    private MessageEntity nullMessage = null;


    public MailBoxEntity(){
        incomeMessages = new ArrayList<>();
        incomeMessages.add(nullMessage);
        outcomeMessages = new ArrayList<>();
        outcomeMessages.add(nullMessage);
    }

    public List<MessageEntity> getOutcomeMessages() {
        return outcomeMessages;
    }

    public List<MessageEntity> getIncomeMessages() {
        return incomeMessages;
    }

    public void addOutcomeMessage(MessageEntity outcomeMessage){
        this.outcomeMessages.add(outcomeMessage);
    }

    public void addIncomeMessage(MessageEntity incomeMessage){
        this.incomeMessages.add(incomeMessage);
    }
}
