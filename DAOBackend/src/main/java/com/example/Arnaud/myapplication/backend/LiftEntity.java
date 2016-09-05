package com.example.Arnaud.myapplication.backend;


import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Arnaud on 17.07.2016.
 */

@Entity
public class LiftEntity {



    @Id
    private Long id;
    public Long getId() {return id;}



    transient private Ref<EventEntity> event_ref;
    @Ignore
    private EventEntity event;
    public EventEntity getEvent() {return event;}


    transient private Ref<UserEntity> driver_ref;
    @Ignore
    private UserEntity driver;
    public UserEntity getDriver(){return driver;}


    transient private Ref<LiftMapperEntity> drinkers_ref;
    @Ignore
    private HashMap<String, UserEntity> drinkers;
    public HashMap<String, UserEntity> getDrikers() {return  drinkers;}


    private String destination;
    public String getDestination(){return destination;}
    public void setDestination(String destination) {
        this.destination = destination;
    }



    private Date departure;
    public Date getDeparture(){return departure;}
    public void setDeparture(Date departure) {
        this.departure = departure;
    }


    private int capacity;
    public int getCapacity(){ return capacity;}
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    private boolean isFull;
    public boolean isFull(){return isFull;}

    public LiftEntity(EventEntity event, UserEntity driver){
        this.event_ref = Ref.create(event);
        this.event =event;
        this.driver_ref = Ref.create(driver);
        this.driver = driver;
        this.destination = "";
        this.capacity = 0;
        this.departure = event.getEnd();

    }


    public LiftEntity() {
    }
    @OnLoad
    protected void switchToReal(){
        driver = driver_ref.get();
        event = event_ref.get();

        driver_ref = null;
        event_ref = null;

        if (drinkers_ref != null){
            LiftMapperEntity mapper = drinkers_ref.get();
            drinkers = mapper.getDrinkers();
        }
        else {
            drinkers = new HashMap<>();
        }
        driver_ref = null;


    }
    @OnSave
    protected void switchToRef(){
        driver_ref = Ref.create(driver);
        event_ref = Ref.create(event);

        LiftMapperEntity mapper = null;
        try {
            ofy().load().type(LiftEntity.class).id(this.id).safe();
        }catch (com.googlecode.objectify.NotFoundException e) {
           mapper = new LiftMapperEntity(this.id);
        }
        try {
            mapper.setDrinkers(drinkers);
            ofy().save().entity(drinkers).now();
            mapper = ofy().load().entity(mapper).now();
            drinkers_ref = Ref.create(mapper);
        }catch (IllegalStateException e){
            ofy().delete().entity(mapper);
            drinkers_ref = null;
        }


    }

}

