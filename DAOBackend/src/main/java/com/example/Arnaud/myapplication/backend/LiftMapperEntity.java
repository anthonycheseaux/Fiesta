package com.example.Arnaud.myapplication.backend;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.impl.ref.LiveRef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by Arnaud on 02.09.2016.
 */
@Entity
public class LiftMapperEntity {
    @Id
    private Long liftId;




    private List<Ref<UserEntity>> drinkers;
    public List<UserEntity> getDrinkers() {
        List<UserEntity> respons = new ArrayList<>(drinkers.size());
        for (Iterator<Ref<UserEntity>> iterator = drinkers.iterator(); iterator.hasNext();)
            respons.add(iterator.next().get());


        return respons;
    }
    public void setDrinkers(List<UserEntity> list) throws IllegalStateException{
        if (list.size() == 0)
            throw new IllegalStateException("list is empty");
        drinkers = new ArrayList<>();
        for (Iterator<UserEntity> iterator = list.iterator(); iterator.hasNext();)
                drinkers.add(Ref.create(iterator.next()));

    }

    public LiftMapperEntity(Long liftId){
        this.liftId = liftId;
        drinkers = new ArrayList<>();
    }
    public LiftMapperEntity(){}
}
