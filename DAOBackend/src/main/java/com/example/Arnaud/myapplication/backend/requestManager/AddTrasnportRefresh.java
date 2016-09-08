package com.example.Arnaud.myapplication.backend.requestManager;


import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.service.Media;

import static com.googlecode.objectify.ObjectifyService.ofy;


/**
 * Created by Arnaud on 08.09.2016.
 */
@Deprecated
public class AddTrasnportRefresh extends AbstractManager {
    static final String[] misssions = new String[]{
            _NavigationsRules.SN_CREATE_TRANSPORT_STATE+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_CREATE_TRANSPORT_STATE
    };
    protected LiftEntity lift;

    AddTrasnportRefresh(Media media) {
        super(media);
    }

    @Override
    protected void getData() throws Exception {
        owner = media.owner;
        lift = media.lift;


    }

    @Override
    protected void perfomeActions() {

        this.owner = ofy().load().entity(owner).now();
        this.owner.putMails();
        this.lift = ofy().load().type(LiftEntity.class).id(lift.getId()).now();

    }


    @Override
    protected void setState() {
        media.stateType = _NavigationsRules.SN_CREATE_TRANSPORT_STATE;
    }


    @Override
    protected void setNededData() {
        media.owner = owner;
        media.lift = lift;

    }
}
