package com.example.Arnaud.myapplication.backend;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Arnaud on 17.07.2016.
 */

@Entity
public class LiftEntity {



    @Id
    private Long id;
    public Long getId() {return id;}



    private Ref<EventEntity> event;
    public EventEntity getEvent() {return event.get();}


    private Ref<DriverEntity> driver;
    public DriverEntity getDriver() {return driver.get();}

    private String destination;
    public String getDestination(){return destination;}

    private Date departure;
    public Date getDeparture(){return departure;}



    private List<Ref<DrinkerEntity>> drinkers;
    public List<DrinkerEntity> getDrinkers() {
        List<DrinkerEntity> respons = new ArrayList<>(drinkers.size());
        for (Iterator<Ref<DrinkerEntity>> iterator = drinkers.iterator(); iterator.hasNext();)
            respons.add(iterator.next().get());
        return respons;
    }
    private int capacity;
    public int getCapacity(){ return capacity;}

    public boolean isFull(){return drinkers.size()<capacity;}

    public LiftEntity(EventEntity event, DriverEntity driver){
        this.event = Ref.create(event);
        this.driver = Ref.create(driver);
        this.destination = "";
        this.capacity = 0;
        this.departure = event.getEnd();
    }

    public LiftEntity(EventEntity event, DriverEntity driver, String destination, int capacity, Date departure) {
        this.event = Ref.create(event);
        this.driver = Ref.create(driver);
        this.destination = destination;
        this.capacity = capacity;
        this.departure = departure;
    }

    public LiftEntity(EventEntity event, DriverEntity driver,String destination, int capacity, List<DrinkerEntity> drinkers,Date departure) {
        this.event = Ref.create(event);
        this.driver = Ref.create(driver);
        this.destination = destination;
        this.capacity = capacity;
        this.drinkers = new ArrayList<>(drinkers.size());
        for (Iterator<DrinkerEntity> iterator = drinkers.iterator(); iterator.hasNext();)
            this.drinkers.add(Ref.create(iterator.next()));
        this.departure = departure;
    }

    public LiftEntity() {
    }
}
