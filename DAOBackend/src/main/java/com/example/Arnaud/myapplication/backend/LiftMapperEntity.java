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
    public HashMap<String,UserEntity> getDrinkers() {
        HashMap<String,UserEntity> respons = new HashMap<String,UserEntity>((int)(drinkers.size()*1.4));
        for (Iterator<Ref<UserEntity>> iterator = drinkers.iterator(); iterator.hasNext();){
            UserEntity user = iterator.next().get();
            respons.put(user.getEmail(), user);
        }
        return respons;
    }
    public void setDrinkers(HashMap<String, UserEntity> hashMap) throws IllegalStateException{
        if (hashMap.size() == 0)
            throw new IllegalStateException("hasmap is empty");
        drinkers = new ArrayList<>();
        for (Iterator<Map.Entry<String,UserEntity>> iterator = hashMap.entrySet().iterator(); iterator.hasNext();) {
            UserEntity itered = iterator.next().getValue();
            if (itered != null)
                drinkers.add(Ref.create(itered));
        }


    }

    public LiftMapperEntity(Long liftId){
        this.liftId = liftId;
        drinkers = new ArrayList<>();
    }
    public LiftMapperEntity(){}
}
