package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.service.Media;

import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 30.08.2016.
 */
class AddTransport extends AbstractManager {
    static final String[] misssions = new String[]{
            _NavigationsRules.SN_CREATE_TRANSPORT_STATE+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_MANAGE_LIFT
    };

    private LiftEntity lift;

    AddTransport(Media media) {
        super(media);
        logger = Logger.getLogger(AddTransport.class.getName());
    }

    @Override
    protected void getData() throws NullPointerException {
        owner = media.owner;
        lift = media.lift;
    }

    @Override
    protected void perfomeActions() {
        ofy().save().entity(lift).now();
    }


    @Override
    protected void setState() {
        media.stateType= _NavigationsRules.SN_MANAGE_LIFT;
    }

    @Override
    protected void setNededData() {
        owner = ofy().load().entity(owner).now();
        lift  = ofy().load().entity(lift).now();


        media.owner = owner;
        media.lift= lift;
    }
}
