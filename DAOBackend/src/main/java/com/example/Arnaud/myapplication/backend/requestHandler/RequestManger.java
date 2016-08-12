package com.example.Arnaud.myapplication.backend.requestHandler;

import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.service.BackAbstractState;
import com.example.Arnaud.myapplication.backend.service.BackInsciptionState;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 12.08.2016.
 */
public abstract class RequestManger {
    public BackAbstractState manage(BackInsciptionState backInsciptionState){
        BackAbstractState respons = null;
        if(backInsciptionState.user.getClass() == DriverEntity.class){
            ofy().save().entity(backInsciptionState.user).now();

            ofy().load().entity(backInsciptionState.user).now();
        }
        else{

        }

            return respons;
    }
}
