package com.example.Arnaud.myapplication.backend;

import com.googlecode.objectify.annotation.*;

import java.util.List;

/**
 * Created by Arnaud on 17.07.2016.
 */

@Entity
public class LiftEntity {
    @Id
    private Long id;
    public Long getId() {return id;}

    @Index
    private EventEntity event;
    public EventEntity getEvent() {return event;}

    private DriverEntity driver;
    public DriverEntity getDriver() {return driver;}

    private String destination;
    public String getDestination(){return destination;}

    private List<DrinkerEntity> drinkers;
    public List<DrinkerEntity> getDrinkers() {return drinkers;}

    private int capacity;
    public int getCapacity(){ return capacity;}

    public boolean isFull(){return drinkers.size()<capacity;}

    public LiftEntity(EventEntity event, DriverEntity driver, String destination, int capacity) {
        this.event = event;
        this.driver = driver;
        this.destination = destination;
        this.capacity = capacity;
    }

    public LiftEntity(EventEntity event, DriverEntity driver,String destination, int capacity, List<DrinkerEntity> drinkers) {
        this.event = event;
        this.driver = driver;
        this.destination = destination;
        this.capacity = capacity;
        this.drinkers = drinkers;
    }

    public LiftEntity() {
    }
}
