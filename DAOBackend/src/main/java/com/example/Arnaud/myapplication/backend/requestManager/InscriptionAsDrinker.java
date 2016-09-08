package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.DrinkerMapperEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.service.Media;
import com.google.api.server.spi.response.NotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

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
            _NavigationsRules.SN_INSCRIPTION_STATE+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_SEARCH_TRANSPORT_STATE,
            _NavigationsRules.SN_SEARCH_TRANSPORT_STATE+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_SEARCH_TRANSPORT_STATE
    };

    List<LiftEntity> availebleLift;
    private boolean hasLift;
    private LiftEntity potentialLift;
    private DrinkerMapperEntity dme;

    InscriptionAsDrinker(Media media) {
        super(media);
        hasLift = false;
        logger = Logger.getLogger(InscriptionAsDrinker.class.getName());
    }


    @Override
    protected void perfomeActions() {
        ofy().save().entity(owner).now();
        this.owner = ofy().load().entity(owner).now();
        this.owner.putMails();

        try {
            dme = ofy().load().type(DrinkerMapperEntity.class).id(owner.getEmail()+selectedEvent.getId()).safe();
        }catch (com.googlecode.objectify.NotFoundException e) {
        }

        selectedEvent =ofy().load().entity(selectedEvent).now();

        if (dme == null)
            dme = new DrinkerMapperEntity(selectedEvent,owner);
        hasLift= (dme.getLiftEntity()!= null);
        if (hasLift){
            potentialLift= dme.getLiftEntity();
            media.stateType = _NavigationsRules.SN_IN_LIFT_STATE;
        }else {
            List<LiftEntity> allLift = ofy().load().type(LiftEntity.class).list();
            availebleLift = new LinkedList<>();
            for (Iterator<LiftEntity> iterator= allLift.iterator();iterator.hasNext();){
                LiftEntity tmp = iterator.next();
                if (false == tmp.isFull())
                    if (0==(selectedEvent.getId()-tmp.getEvent().getId()))
                        availebleLift.add(tmp);
            }
        }
    }


    @Override
    protected void setState() {
        media.stateType= _NavigationsRules.SN_SEARCH_TRANSPORT_STATE;
    }

    @Override
    protected void setNededData() {


        media.owner = owner;

        if (hasLift){
            media.lift= potentialLift;
        }else {
            media.selectedEvent = selectedEvent;
            media.availableLifts =availebleLift;
        }


    }
}
