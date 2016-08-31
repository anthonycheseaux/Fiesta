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

    private UserEntity owner;
    private EventEntity selectedEvent;
    private DriverEntity driverEntity;
    private LiftEntity lift;

    AddTransport(Media media) {
        super(media);
    }

    @Override
    protected boolean checkDataConsistency() {
        boolean respons;
        if (respons= media.owner != null)
            if (respons = media.selectedEvent!= null)
                if (respons = media.lift != null)
                    if (respons = media.lift.getDriver() != null)
                ;
        return respons;
    }

    @Override
    protected void getData() {
        owner = ofy().load().entity(media.owner).now();
        selectedEvent = ofy().load().entity(media.selectedEvent).now();
        driverEntity = ofy().load().entity(media.lift.getDriver()).now();
        lift = new LiftEntity(selectedEvent, driverEntity, media.lift.getDestination(), media.lift.getCapacity(),media.lift.getDeparture());
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
        media.owner = ofy().load().entity(owner).now();
        media.lift= ofy().load().entity(lift).now();
    }
}
