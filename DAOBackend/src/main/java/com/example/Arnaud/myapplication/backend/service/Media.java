package com.example.Arnaud.myapplication.backend.service;

import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.OnSave;

import java.util.List;

/**
 * Created by Arnaud on 18.08.2016.
 */
@Entity
public class Media {
    @Id
    public Long id;


    public String stateType;
    @Ignore
    public List<String> availableStates;
    @Ignore
    public String wantedState;


    @Ignore
    public UserEntity owner;
    public transient Ref<UserEntity> refOwner;

    @Ignore
    public EventEntity selectedEvent;
    public transient Ref<EventEntity> refSelctedEvent;
    @Ignore
    public List<EventEntity> availableEvent;

    @Ignore
    public LiftEntity lift;
    public transient Ref<LiftEntity> refLift;
    @Ignore
    public List<LiftEntity> availableLifts;


    public void cleanAll(){
        stateType = null;
        availableStates = null;
        wantedState = null;
        owner = null;
        selectedEvent = null;
        availableEvent = null;
        lift = null;
        availableLifts = null;

    }
    @OnSave
    public void toRef(){
        if (owner!= null)
            refOwner = Ref.create(owner);
        if (lift!= null)
            refLift = Ref.create(lift);
        if (selectedEvent!= null)
            refSelctedEvent = Ref.create(selectedEvent);
    }
    @OnLoad
    public void toReal(){
        if (refOwner!= null)
            owner = refOwner.get();
        if (refSelctedEvent!= null)
            selectedEvent = refSelctedEvent.get();
        if (refLift!= null)
            lift = refLift.get();
    }


}
