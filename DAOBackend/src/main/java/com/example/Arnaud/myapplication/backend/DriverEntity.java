package com.example.Arnaud.myapplication.backend;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 17.07.2016.
 */

@Entity
public class DriverEntity {


    @Id Long id;

    public Long getId() {
        return id;
    }


    private Ref<UserEntity> user;
    public UserEntity getUser() {
        return user.get();
    }


    private Ref<EventEntity> event;
    public EventEntity getEvent() {
        return event.get();
    }


    private Ref<LiftEntity> liftEntity;
    public LiftEntity getLiftEntity() {
        if (liftEntity == null){
            LiftEntity lift = new LiftEntity(event.get(), this);
            ofy().save().entity(lift).now();
            this.liftEntity = Ref.create(lift);
        }
        return liftEntity.get();
    }


    public DriverEntity(UserEntity user, EventEntity event) {
        this.user =Ref.create(user);
        this.event = Ref.create(event);
    }

    public DriverEntity() {
    }
}
