package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.service.Media;

import java.util.ArrayList;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 30.08.2016.
 *
 * can hanle :
 *  registrationn of new user as Driver
 *  update driver data
 *  uregistre drinke and registre as driver
 *
 */
class InscriptionAsDriver extends Inscription {
    static final String[] misssions = new String[]{
            Media.SN_INSCRIPTION_STATE+ Facade.CONNECTION_TO + Media.SN_CREATE_TRANSPORT_STATE
    };

    private LiftEntity lift;
    InscriptionAsDriver(Media media) {
        super(media);
    }

    @Override
    protected void setNavigation() {
        media.stateType= Media.SN_CREATE_TRANSPORT_STATE;
        media.availableStates= new ArrayList<String>();
        media.availableStates.add(Media.SN_MANAGE_LIFT);
    }


    @Override
    protected void setNededData() {
        owner = ofy().load().entity(owner).now();
        selectedEvent = ofy().load().entity(selectedEvent).now();
        lift = new LiftEntity(selectedEvent, owner);
        ofy().save().entities(lift).now();
        lift = ofy().load().entity(lift).now();
        media.owner = owner;
        media.lift = lift;
    }
}
