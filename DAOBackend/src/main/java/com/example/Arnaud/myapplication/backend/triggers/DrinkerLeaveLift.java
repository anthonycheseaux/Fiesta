package com.example.Arnaud.myapplication.backend.triggers;

import com.example.Arnaud.myapplication.backend.DrinkerMapperEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;

import java.util.Iterator;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;
/**
 * Created by Arnaud on 09.09.2016.
 */
public class DrinkerLeaveLift extends AbstractTrigger {
    private LiftEntity liftEntity;
    private UserEntity drinker;


    public DrinkerLeaveLift(LiftEntity liftEntity, UserEntity drinker) {
        this.liftEntity = liftEntity;
        this.drinker = drinker;
    }

    @Override
    protected void performeAction() {
        liftEntity =ofy().load().entity(liftEntity).now();
        List<UserEntity> drinkers = liftEntity.getDrinkers();
        int index =0;
        Iterator<UserEntity> iterator = drinkers.iterator();
        boolean finded = false;
        while (iterator.hasNext()&& false == finded)
            if (iterator.next().getEmail().equals(drinker.getEmail()))
                finded = true;
            else
                ++index;
        if (finded)
            drinkers.remove(index);
        liftEntity.setDrinkers(drinkers);
        ofy().save().entity(liftEntity);
        DrinkerMapperEntity dme = ofy().load().type(DrinkerMapperEntity.class).id(drinker.getEmail()+liftEntity.getEvent().getId()).now();
        dme.setLiftEntity(null);
        ofy().save().entity(dme);

    }
}
