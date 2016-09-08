package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.service.Media;

import java.util.logging.Logger;

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
            _NavigationsRules.SN_INSCRIPTION_STATE+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_CREATE_TRANSPORT_STATE,
            _NavigationsRules.SN_CREATE_TRANSPORT_STATE+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_CREATE_TRANSPORT_STATE

    };

    private LiftEntity lift;

    InscriptionAsDriver(Media media) {
        super(media);
        logger = Logger.getLogger(InscriptionAsDriver.class.getName());
    }


    @Override
    protected void perfomeActions() {

        ofy().save().entity(owner).now();
        this.owner = ofy().load().entity(owner).now();
        this.owner.putMails();

        selectedEvent =ofy().load().entity(selectedEvent).now();
        lift = null;
        try {
            lift=ofy().load().type(LiftEntity.class).id(owner.getEmail()+selectedEvent.getId()).safe();
            if (lift.getDestination()!= null)
                media.stateType=_NavigationsRules.SN_MANAGE_LIFT;
        }catch (Exception e){}
        if (lift == null){
            lift = new LiftEntity(selectedEvent, owner);
            ofy().save().entities(lift).now();
            lift = ofy().load().entity(lift).now();
        }

    }


    @Override
    protected void setState() {
        media.stateType= _NavigationsRules.SN_CREATE_TRANSPORT_STATE;
    }



    @Override
    protected void setNededData() {
        media.owner = owner;
        media.lift = lift;
        media.selectedEvent = selectedEvent;
    }
}
