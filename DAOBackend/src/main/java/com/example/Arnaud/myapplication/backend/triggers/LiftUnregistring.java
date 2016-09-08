package com.example.Arnaud.myapplication.backend.triggers;


import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;

/**
 * Created by Arnaud on 08.09.2016.
 */
public class LiftUnregistring extends AbstractTrigger {
    private LiftEntity liftEntity;
    public LiftUnregistring(LiftEntity liftEntity){
        this.liftEntity = liftEntity;
    }

    @Override
    protected void performeAction() {
        NotifyDrinkers_on_liftUpdate ouserced = new NotifyDrinkers_on_liftUpdate(new ArrayList<UserEntity>(),liftEntity.getDrinkers());
        ouserced.performeAction();
        ofy().delete().entity(liftEntity);
    }
}
