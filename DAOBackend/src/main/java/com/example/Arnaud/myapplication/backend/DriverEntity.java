package com.example.Arnaud.myapplication.backend;

import com.googlecode.objectify.annotation.*;

/**
 * Created by Arnaud on 17.07.2016.
 */

@Subclass
public class DriverEntity extends UserEntity {



    public DriverEntity(String userName, String email, String phoneNumber) {
        super(userName, email, phoneNumber);
    }

    public DriverEntity() {
    }
}
