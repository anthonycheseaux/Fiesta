package com.example.Arnaud.myapplication.backend.service;

import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 19.08.2016.
 */
class LiftHelper {
    private LiftHelper(){}


    static Media refreshLiftList(Media media){
        //-+-+-+-+-+-+- get data -+-+-+-+-+-+-
        UserEntity owner = ofy().load().entity(media.owner).now();
        EventEntity selectedEvent = ofy().load().entity(media.selectedEvent).now();

        //-+-+-+-+-+-+- clean data -+-+-+-+-+-+-
        media.cleanAll();

        //-+-+-+-+-+-+- set state type -+-+-+-+-+-+-
        media.stateType= Media.SN_SEARCH_TRANSPORT_STATE;

        //-+-+-+-+-+-+- set next step -+-+-+-+-+-+-
        media.availableStates= new ArrayList<String>();
        media.availableStates.add(Media.SN_SEARCH_TRANSPORT_STATE);
        media.availableStates.add(Media.SN_IN_LIFT_STATE);

        //-+-+-+-+-+-+- set needed data -+-+-+-+-+-+-
        media.owner = ofy().load().entity(owner).now();
        media.selectedEvent = selectedEvent;

        //TODO use query whith join instead of all and keep needed
        Query<LiftEntity> query = ofy().load().type(LiftEntity.class);
        QueryResultIterator<LiftEntity> queryIterator = query.iterator();
        List<LiftEntity> liftEntityList = new ArrayList<LiftEntity>();
        while (queryIterator.hasNext()) {
            LiftEntity current =queryIterator.next();
            if (current.getEvent() == selectedEvent)
                if (current.isFull()==false)
                    liftEntityList.add(current);
        }
        media.availableLifts = liftEntityList;
        //TODO conserver state dans ofy;
        return media;
    }
    static Media liftInscription(Media media){

        //-+-+-+-+-+-+- get data -+-+-+-+-+-+-
        UserEntity owner = ofy().load().entity(media.owner).now();
        EventEntity selectedEvent = ofy().load().entity(media.selectedEvent).now();
        DriverEntity driverEntity = ofy().load().entity(media.lift.getDriver()).now();

        LiftEntity lift = new LiftEntity(selectedEvent, driverEntity, media.lift.getDestination(), media.lift.getCapacity());
        ofy().save().entity(lift).now();

        //-+-+-+-+-+-+- clean data -+-+-+-+-+-+-
        media.cleanAll();

        //-+-+-+-+-+-+- set state type -+-+-+-+-+-+-
        media.stateType= Media.SN_MANAGE_LIFT;

        //-+-+-+-+-+-+- set next step -+-+-+-+-+-+-
        media.availableStates= new ArrayList<String>();
        media.availableStates.add(Media.SN_MANAGE_LIFT);

        //-+-+-+-+-+-+- set needed data -+-+-+-+-+-+-
        media.owner = ofy().load().entity(owner).now();

        media.lift= ofy().load().entity(lift).now();

        //TODO conserver state dans ofy;
        return media;
    }
}
