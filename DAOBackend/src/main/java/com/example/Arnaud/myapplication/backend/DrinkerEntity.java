package com.example.Arnaud.myapplication.backend;


import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.*;

/**
 * Created by Arnaud on 17.07.2016.
 */
@Entity
public class DrinkerEntity {




    @Id Long id;

    public Long getId() {
        return id;
    }


    @Container
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }


    @Container
    private EventEntity event;

    public EventEntity getEvent() {
        return event;
    }


    @Container
    private LiftEntity liftEntity;

    public LiftEntity getLiftEntity() {
        return liftEntity;
    }
    public void setLiftEntity(LiftEntity liftEntity) {
        this.liftEntity = liftEntity;
    }


    public boolean hasLift(){
        return (liftEntity != null);
    }


    public DrinkerEntity() {
    }
    public DrinkerEntity(UserEntity user, EventEntity event) {
        this.user = user;
        this.event = event;
        this.liftEntity = null;

    }
    public DrinkerEntity(UserEntity user, EventEntity event, LiftEntity liftEntity) {
        this.user = user;
        this.event = event;
        this.liftEntity = liftEntity;
    }
}
