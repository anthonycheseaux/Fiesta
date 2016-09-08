package com.example.Arnaud.myapplication.backend;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.OnSave;

/**
 * Created by Arnaud on 08.09.2016.
 */
@Entity
public class DrinkerMapperEntity {
    @Id
    private String id;
    public String getId() {return id;}


    private transient Ref<LiftEntity> liftEntityRef;
    @Ignore
    private LiftEntity liftEntity;

    public LiftEntity getLiftEntity() {
        return liftEntity;
    }

    public void setLiftEntity(LiftEntity liftEntity) {
        this.liftEntity = liftEntity;
    }

    public DrinkerMapperEntity(EventEntity event, UserEntity drinker){
        this.id = drinker.getEmail()+event.getId();
    }
    private DrinkerMapperEntity(){

    }
    @OnLoad
    public void setReal(){
        if (liftEntityRef!= null)
            liftEntity= liftEntityRef.get();
        else
            liftEntity = null;
    }
    @OnSave
    public void setRef(){
        if (liftEntity!= null)
            liftEntityRef= Ref.create(liftEntity);
        else
            liftEntityRef = null;
    }
}
