package com.example.Arnaud.myapplication.backend;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Arnaud on 17.07.2016.
 */

@Entity
public class LiftEntity {



    @Id
    private Long id;
    public Long getId() {return id;}


    @Container
    private EventEntity event;
    public EventEntity getEvent() {return event;}


    @Container
    private DriverEntity driver;
    public DriverEntity getDriver() {return driver;}

    private String destination;
    public String getDestination(){return destination;}

    private Date departure;
    public Date getDeparture(){return departure;}


    @Container
    private List<DrinkerEntity> drinkers;
    public List<DrinkerEntity> getDrinkers() {return drinkers;}

    private int capacity;
    public int getCapacity(){ return capacity;}

    public boolean isFull(){return drinkers.size()<capacity;}

    public LiftEntity(EventEntity event, DriverEntity driver){
        this.event = event;
        this.driver = driver;
        this.destination = "";
        this.capacity = 0;
        this.departure = event.getEnd();
    }

    public LiftEntity(EventEntity event, DriverEntity driver, String destination, int capacity, Date departure) {
        this.event = event;
        this.driver = driver;
        this.destination = destination;
        this.capacity = capacity;
        this.departure = departure;
    }

    public LiftEntity(EventEntity event, DriverEntity driver,String destination, int capacity, List<DrinkerEntity> drinkers,Date departure) {
        this.event = event;
        this.driver = driver;
        this.destination = destination;
        this.capacity = capacity;
        this.drinkers = drinkers;
        this.departure = departure;
    }

    public LiftEntity() {
    }
}
