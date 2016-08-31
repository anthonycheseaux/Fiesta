package com.example.Arnaud.myapplication.backend;


import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

/**
 * Created by Arnaud on 17.07.2016.
 */
@Entity
public class DrinkerEntity {

    @Id Long id;
    public Long getId() {
        return id;
    }

    private Ref<UserEntity> user;
    public UserEntity getUser() {return user.get();}

    private Ref<EventEntity> event;
    public EventEntity getEvent() {
        return event.get();
    }

    private Ref<LiftEntity> liftEntity;
    public LiftEntity getLiftEntity() {
        return liftEntity.get();
    }
    public void setLiftEntity(LiftEntity liftEntity) {
        this.liftEntity = Ref.create(liftEntity);
    }

    public boolean hasLift(){
        return (liftEntity != null);
    }


    public DrinkerEntity() {
    }
    public DrinkerEntity(UserEntity user, EventEntity event) {
        this.user = Ref.create(user);
        this.event = Ref.create(event);
        this.liftEntity = null;

    }
    public DrinkerEntity(UserEntity user, EventEntity event, LiftEntity liftEntity) {
        this.user = Ref.create(user);
        this.event = Ref.create(event);
        this.liftEntity = Ref.create(liftEntity);
    }
}
