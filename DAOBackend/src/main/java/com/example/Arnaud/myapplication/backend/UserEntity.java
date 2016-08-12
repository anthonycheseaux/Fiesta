package com.example.Arnaud.myapplication.backend;

/**
 * Created by Arnaud on 17.07.2016.
 */

import com.googlecode.objectify.annotation.*;

@Entity
public abstract class UserEntity {
    public static final String CLASS_PREFIX = "user_";


    public static final String ID = CLASS_PREFIX+"id";
    @Id
    private Long id;
    public Long getId() {return id;}

    public static final String USERNAME = CLASS_PREFIX+"userName";
    @Index
    private String userName;
    public String getUserName() {return userName;}


    public static final String EMAIL = CLASS_PREFIX+"email";
    @Index
    private String email;
    public String getEmail() {return email;}

    public static final String PHONE_NUMBER = CLASS_PREFIX+"phoneNumber";
    private String phoneNumber;
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
