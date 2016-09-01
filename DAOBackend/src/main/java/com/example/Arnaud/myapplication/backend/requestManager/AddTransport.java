package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;

import java.util.ArrayList;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 30.08.2016.
 */
class AddTransport extends AbstractManager {
    static final String[] misssions = new String[]{
            Media.SN_CREATE_TRANSPORT_STATE+ Facade.CONNECTION_TO + Media.SN_MANAGE_LIFT
    };

    private LiftEntity lift;

    AddTransport(Media media) {
        super(media);
    }

    @Override
    protected boolean checkDataConsistency() {
        boolean respons;
        if (respons= media.owner != null)
            if (respons = media.lift_id != null)
                if(respons = media.lift_capacity>0)
                    if (respons = media.lift_departure!= null)
                        if (respons = media.lift_destination!= null)
                            if (respons != media.lift_destination.equals(""))
                                if(respons= media.lift_eventId != null)
                                    if (respons = media.owner.getEmail().equals(media.lift_owner))
                                        ;
        return respons;
    }

    @Override
    protected void getData() {
        owner = ofy().load().entity(media.owner).now();
        lift = ofy().load().type(LiftEntity.class).id(media.lift_id).now();
        lift.setDestination(media.lift_destination);
        lift.setCapacity(media.lift_capacity);
        lift.setDeparture(media.lift_departure);
        ofy().save().entity(lift).now();
    }

    @Override
    protected void setStateType() {
        media.stateType= Media.SN_MANAGE_LIFT;
    }

    @Override
    protected void setNextStep() {
        media.availableStates= new ArrayList<String>();
        media.availableStates.add(Media.SN_MANAGE_LIFT);
    }

    @Override
    protected void setNededData() {
        owner = ofy().load().entity(owner).now();
        lift= ofy().load().entity(lift).now();


        media.owner = owner;
        media.lift= lift;
    }
}
