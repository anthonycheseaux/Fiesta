package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.DrinkerEntity;
import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;

import java.util.ArrayList;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 30.08.2016.
 * can hanle :
 *  registrationn of new user as drinker
 *  update drinker data
 *  uregistre driver and registre as drinker
 *
 */
class InscriptionAsDrinker extends Inscription {
    static final String[] misssions = new String[]{
            Media.SN_INSCRIPTION_STATE+ Facade.CONNECTION_TO + Media.SN_SEARCH_TRANSPORT_STATE
    };

    InscriptionAsDrinker(Media media) {
        super(media);
    }



    @Override
    protected void setStateType() {
        media.stateType= Media.SN_SEARCH_TRANSPORT_STATE;
    }

    @Override
    protected void setNextStep() {
        media.availableStates= new ArrayList<String>();
        media.availableStates.add(Media.SN_IN_LIFT_STATE);
    }

    @Override
    protected void setNededData() {
        owner = ofy().load().entity(owner).now();
        selectedEvent = ofy().load().entity(selectedEvent).now();
        media.owner = owner;

        media.availableLifts = ofy().load().type(LiftEntity.class).list();
        int i = 0;
    }
}
