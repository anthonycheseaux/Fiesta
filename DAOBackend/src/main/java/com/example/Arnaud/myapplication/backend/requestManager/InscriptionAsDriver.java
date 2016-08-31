package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
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

    private DriverEntity driver;

    InscriptionAsDriver(Media media) {
        super(media);
    }



    @Override
    protected void setStateType() {
        media.stateType= Media.SN_CREATE_TRANSPORT_STATE;
    }

    @Override
    protected void setNextStep() {
        media.availableStates= new ArrayList<String>();
        media.availableStates.add(Media.SN_MANAGE_LIFT);
    }

    @Override
    protected void setNededData() {
        owner = ofy().load().entity(owner).now();
        selectedEvent = ofy().load().entity(selectedEvent).now();
        driver = new DriverEntity(owner, selectedEvent);
        ofy().save().entities(driver);
        media.owner = owner;
        media.selectedEvent = selectedEvent;
    }
}
