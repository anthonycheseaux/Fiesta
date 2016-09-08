package com.example.Arnaud.myapplication.backend.triggers;


import com.example.Arnaud.myapplication.backend.DrinkerMapperEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;

import java.util.Iterator;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;
/**
 * Created by Arnaud on 07.09.2016.
 */
public class NotifyDrinkers_on_liftUpdate extends AbstractTrigger {
    List<UserEntity> added;
    List<UserEntity> removed;
    LiftEntity liftEntity;

    public NotifyDrinkers_on_liftUpdate(List<UserEntity> added, List<UserEntity> removed, LiftEntity liftEntity){
        this.added = added;
        this.removed = removed;
        this.liftEntity = liftEntity;
    }
    @Override
    protected void performeAction() {
        String eventId = liftEntity.getEvent().getId()+"";
        for (Iterator<UserEntity> iterator = removed.iterator();iterator.hasNext();){
            UserEntity removedDrinker = iterator.next();
            DrinkerMapperEntity dme = ofy().load().type(DrinkerMapperEntity.class).id(removedDrinker.getEmail()+eventId).now();
            dme.setLiftEntity(null);
        }
        for (Iterator<UserEntity> iterator = added.iterator();iterator.hasNext();){
            UserEntity addedDrinker = iterator.next();
            DrinkerMapperEntity dme = ofy().load().type(DrinkerMapperEntity.class).id(addedDrinker.getEmail()+eventId).now();
            dme.setLiftEntity(liftEntity);
        }
    }
}
