package com.example.Arnaud.myapplication.backend;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.impl.ref.LiveRef;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Arnaud on 02.09.2016.
 */
@Entity
public class LiftMapperEntity {
    @Id
    private Long liftId;

    private String ownerId;

    private TreeSet<LiveRef<UserEntity>> content;
    public List<UserEntity> getDrinkers() {
        List<UserEntity> respons = new ArrayList<UserEntity>(content.size());
        for (Iterator<LiveRef<UserEntity>> iterator = content.iterator(); iterator.hasNext();)
            respons.add(iterator.next().get());
        return respons;
    }
    public boolean addDrinker(UserEntity drinker){
        if (false == drinker.getEmail().equals(ownerId)){
            return content.add((LiveRef) Ref.create(drinker));
        }
        return false;
    }
    public boolean removeDrinker(UserEntity drinker){
        return content.remove((LiveRef) Ref.create(drinker));
    }

    public LiftMapperEntity(Long liftId, String ownerId){
        this.liftId = liftId;
        this.ownerId = ownerId;
        content = new TreeSet<>();
    }
    public LiftMapperEntity(){}
}
