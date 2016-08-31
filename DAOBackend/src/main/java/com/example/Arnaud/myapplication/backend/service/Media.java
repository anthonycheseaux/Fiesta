package com.example.Arnaud.myapplication.backend.service;

import com.example.Arnaud.myapplication.backend.DrinkerEntity;
import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Arnaud on 18.08.2016.
 */
@Entity
public class Media {
    public final static String SN_INSCRIPTION_STATE = "inscription_state";
    public final static String SN_CREATE_TRANSPORT_STATE = "create_transport_state";
    public final static String SN_SEARCH_TRANSPORT_STATE = "search_transport_state";
    public final static String SN_IN_LIFT_STATE = "in_lift_sate";
    public final static String SN_MANAGE_LIFT = "manage_lift";

    @Id
    public long id;


    public String stateType;
    public List<String> availableStates;
    public String wantedState;


    public UserEntity owner;


    public EventEntity selectedEvent;
    public List<EventEntity> availableEvent;

    public LiftEntity lift;
    public List<LiftEntity> availableLifts;

    public void cleanAll(){
        stateType = null;
        availableStates = null;
        wantedState = "";
        owner = null;
        selectedEvent = null;
        availableEvent = null;
        lift = null;
        availableLifts = null;
    }


}
