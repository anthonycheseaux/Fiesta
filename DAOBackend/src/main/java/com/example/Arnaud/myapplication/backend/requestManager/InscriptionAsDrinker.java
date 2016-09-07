package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.service.Media;

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

    InscriptionAsDrinker(Media media) {
        super(media);
        logger = Logger.getLogger(InscriptionAsDrinker.class.getName());
    }


    @Override
    protected void perfomeActions() {
        ofy().save().entity(owner).now();
        this.owner = ofy().load().entity(owner).now();
        this.owner.putMails();

        owner = ofy().load().entity(owner).now();
        selectedEvent =ofy().load().entity(selectedEvent).now();

        availebleLift= ofy().load().type(LiftEntity.class).list();
    }


    @Override
    protected void setState() {
        media.stateType= _NavigationsRules.SN_SEARCH_TRANSPORT_STATE;
    }

    @Override
    protected void setNededData() {


        media.owner = owner;
        media.selectedEvent = selectedEvent;
        media.availableLifts =availebleLift;

    }
}
