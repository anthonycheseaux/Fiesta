package com.example.Arnaud.myapplication.backend;

import com.googlecode.objectify.annotation.*;

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

    private DrinkerEntity[] drinkers;
    public DrinkerEntity[] getDrinkers() {return drinkers;}

    public LiftEntity(EventEntity event, DriverEntity driver, DrinkerEntity[] drinkers) {
        this.event = event;
        this.driver = driver;
        this.drinkers = drinkers;
    }

    public LiftEntity() {
    }
}
