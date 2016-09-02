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
import com.googlecode.objectify.impl.ref.LiveRef;

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
    public String getEmail() {return email;}

    private String phoneNumber;
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getPhoneNumber() {return phoneNumber;}




    public UserEntity(String userName, String email, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UserEntity() {
    }
}
