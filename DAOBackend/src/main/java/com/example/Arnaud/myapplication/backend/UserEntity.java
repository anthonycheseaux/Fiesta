package com.example.Arnaud.myapplication.backend;

/**
 * Created by Arnaud on 17.07.2016.
 */

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.example.Arnaud.myapplication.backend.Chat.MailBoxEntity;
import com.example.Arnaud.myapplication.backend.Chat.MessageEntity;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import com.googlecode.objectify.ObjectifyService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
public class UserEntity {

    @Index
    private String userName;
    public void setUserName(String userName){this.userName=userName;}
    public String getUserName() {return userName;}


    @Id
    private String email;
    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return email;}

    private String phoneNumber;
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getPhoneNumber() {return phoneNumber;}


    private Ref<MailBoxEntity> ownMail;
    public MailBoxEntity getOwnMail(){
        return ownMail.get();
    }

    private List<Ref<MailBoxEntity>> myMails;
    public List<MailBoxEntity> getMyMails(){
        List<MailBoxEntity> respons = new ArrayList<>(myMails.size());
        for (Iterator<Ref<MailBoxEntity>> iterator = myMails.iterator(); iterator.hasNext();)
            respons.add(iterator.next().get());
        respons.remove(ownMail.get());
        return respons;
    }
    public void addMailBox(MailBoxEntity mailBox){
        if (false == mailBox.getId().equals(email+email))
            myMails.add(Ref.create(mailBox));
    }

    public UserEntity(String userName, String email, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        myMails = new ArrayList<>();

        MessageEntity msg = new MessageEntity();
        MailBoxEntity  ownMB= new MailBoxEntity(email+email);
        ofy().save().entity(msg).now();
        ownMB.addMessage(msg);
        ofy().save().entity(ownMB).now();
        ownMail = Ref.create(ownMB);
    }

    public UserEntity() {
    }
}
