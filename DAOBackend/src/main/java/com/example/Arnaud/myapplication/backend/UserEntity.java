package com.example.Arnaud.myapplication.backend;

/**
 * Created by Arnaud on 17.07.2016.
 */

import com.example.Arnaud.myapplication.backend.Chat.MailMapperEntity;
import com.example.Arnaud.myapplication.backend.Chat.MessageBoxEntity;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnLoad;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Entity
public class UserEntity {

    @Index
    private String userName;
    public void setUserName(String userName){this.userName=userName;}
    public String getUserName() {return userName;}


    @Id
    private String email;
    public String getEmail() {return email;}

    private String phoneNumber;
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getPhoneNumber() {return phoneNumber;}

    @Ignore
    private List<String> myMailsId;

    public List<String> getMyMails() {
        return myMailsId;
    }


    public void putMails(){
        MailMapperEntity myMapper= null;
        try {
            myMapper = ofy().load().type(MailMapperEntity.class).id(email).safe();
        }  catch (com.googlecode.objectify.NotFoundException e) {}

        if (myMapper != null)
            myMailsId= myMapper.getMyMailsId();
        else
            myMailsId = null;
    }



    public UserEntity(String userName, String email, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UserEntity() {
    }
}
