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
    private static UserEntity noneUser = new UserEntity("none", "none", "none");
    static {
        ofy().save().entities(noneUser).now();
        noneUser = ofy().load().entity(noneUser).now();
    }

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




    private List<Ref<UserEntity>> drinkers;
    public List<UserEntity> getDrinkers() {
        List<UserEntity> respons = new ArrayList<UserEntity>(drinkers.size());
        for (Iterator<Ref<UserEntity>> iterator = drinkers.iterator(); iterator.hasNext();)
            respons.add(iterator.next().get());
        respons.remove(noneUser);
        return respons;
    }

    public void setDrinkers(List<UserEntity> drinkers) {
        if (drinkers == null || drinkers.size()==0){
            this.drinkers = new ArrayList<Ref<UserEntity>>(1);
            this.drinkers.add(Ref.create(noneUser));
        }else {
            this.drinkers = new ArrayList<Ref<UserEntity>>(drinkers.size());
            for (Iterator<UserEntity> iterator = drinkers.iterator(); iterator.hasNext();)
                this.drinkers.add(Ref.create(iterator.next()));
        }
    }

    private int capacity;
    public int getCapacity(){ return capacity;}
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isFull(){return drinkers.size()<capacity;}

    public LiftEntity(EventEntity event, UserEntity driver){
        this.event = Ref.create(event);
        this.driver = Ref.create(driver);
        this.destination = "";
        this.capacity = 0;
        this.departure = event.getEnd();
        drinkers = new ArrayList<Ref<UserEntity>>(1);
        drinkers.add(Ref.create(noneUser));
    }


    public LiftEntity() {
    }
}
