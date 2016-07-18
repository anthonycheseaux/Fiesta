package com.example.Arnaud.myapplication.backend;

/**
 * Created by Arnaud on 17.07.2016.
 */

import com.googlecode.objectify.annotation.*;

@Entity
public abstract class UserEntity {

    @Id
    private Long id;
    public Long getId() {return id;}

    @Index
    private String userName;
    public String getUserName() {return userName;}


    @Index
    private String email;
    public String getEmail() {return email;}

    private String phoneNumber;
    public String getPhoneNumber() {return phoneNumber;}


    public UserEntity(String userName, String email, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UserEntity() {
    }
}
