package com.example.Arnaud.myapplication.backend;


import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.OnSave;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 17.07.2016.
 */

@Entity
public class LiftEntity {
    static {
        ObjectifyService.register(LiftMapperEntity.class);
    }



    @Id
    private String id;
    public String getId() {return id;}



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
    private List<UserEntity> drinkers;
    public List<UserEntity> getDrinkers() {return  drinkers;}
    public void setDrinkers(List<UserEntity> drinkers) {this.drinkers = drinkers;}

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

    @Ignore
    private boolean isFull;
    public boolean isFull(){
        int usedPlaces = 0;
        if (drinkers != null)
            usedPlaces = drinkers.size();
        return false == (capacity > usedPlaces);
    }

    public LiftEntity(EventEntity event, UserEntity driver){
        this.id = driver.getEmail()+event.getId();
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
            drinkers = new ArrayList<>();
        }
        driver_ref = null;


    }
    @OnSave
    protected void switchToRef(){
        driver_ref = Ref.create(driver);
        event_ref = Ref.create(event);

        if (id != null)
            if (drinkers != null && drinkers.size()>0) {
                LiftMapperEntity mapper = null;
                try {
                    mapper = ofy().load().type(LiftMapperEntity.class).id(this.id).safe();

                } catch (com.googlecode.objectify.NotFoundException e) {
                    mapper = new LiftMapperEntity(this.id);
                }
                try {
                    mapper.setDrinkers(drinkers);
                    ofy().save().entity(mapper).now();
                    mapper = ofy().load().entity(mapper).now();
                    drinkers_ref = Ref.create(mapper);
                } catch (IllegalStateException e) {
                    ofy().delete().entity(mapper);
                }
            }else
                try {
                    ofy().delete().type(LiftMapperEntity.class).id(this.id).now();
                } catch (com.googlecode.objectify.NotFoundException e) {

                }
    }

}

