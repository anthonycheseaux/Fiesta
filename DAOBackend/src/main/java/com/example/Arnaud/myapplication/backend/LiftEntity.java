package com.example.Arnaud.myapplication.backend;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import java.util.Date;

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
    }
    @OnSave
    protected void switchToRef(){
        driver_ref = Ref.create(driver);
        event_ref = Ref.create(event);

        driver = null;
        event = null;
    }

}

