package com.example.Arnaud.myapplication.backend;

/**
 * Created by Arnaud on 17.07.2016.
 */

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.*;

@Entity
public class UserEntity {



    @Id
    private Long id;
    public Long getId() {return id;}

    @Index
    private String userName;
    public void setUserName(String userName){this.userName=userName;}
    public String getUserName() {return userName;}


    @Index
    private String email;
    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return email;}

    private String phoneNumber;
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getPhoneNumber() {return phoneNumber;}


    public UserEntity(String userName, String email, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public UserEntity(Long id, String userName, String email, String phoneNumber) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UserEntity() {
    }
}
