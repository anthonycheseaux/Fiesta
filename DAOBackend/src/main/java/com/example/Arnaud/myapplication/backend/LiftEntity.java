package com.example.Arnaud.myapplication.backend;

import static com.googlecode.objectify.ObjectifyService.ofy;
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



    private Ref<UserEntity> driver;
    public UserEntity getDriver() {return driver.get();}

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




    private List<Ref<UserEntity>> content;
    public List<UserEntity> getDrinkers() {
        List<UserEntity> respons = new ArrayList<UserEntity>(content.size());
        for (Iterator<Ref<UserEntity>> iterator = content.iterator(); iterator.hasNext();)
            respons.add(iterator.next().get());
        return respons;
    }



    private int capacity;
    public int getCapacity(){ return capacity;}
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isFull(){return content.size()<=capacity;}

    public LiftEntity(EventEntity event, UserEntity driver){
        this.event = Ref.create(event);
        this.driver = Ref.create(driver);
        this.destination = "";
        this.capacity = 0;
        this.departure = event.getEnd();
        content = new ArrayList<Ref<UserEntity>>(1);
        content.add( Ref.create(driver));
    }


    public LiftEntity() {
    }
}
