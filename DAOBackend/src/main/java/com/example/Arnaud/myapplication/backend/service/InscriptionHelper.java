package com.example.Arnaud.myapplication.backend.service;

import com.example.Arnaud.myapplication.backend.DrinkerEntity;
import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 19.08.2016.
 * purpose of this class is to help RequestManger
 * provide simple static methode who act on Media, visibility to package
 * this class DO NOT HAVE TO BE INSTANCIED
 */
class InscriptionHelper {
    private InscriptionHelper(){}

    static Media getInscriptionState(){
        Media respons = new Media();

        //-+-+-+-+-+-+- set state type -+-+-+-+-+-+-
        respons.stateType= Media.SN_INSCRIPTION_STATE;

        //-+-+-+-+-+-+- set next step -+-+-+-+-+-+-
        respons.availableStates= new ArrayList<String>();
        respons.availableStates.add(Media.SN_CREATE_TRANSPORT_STATE);
        respons.availableStates.add(Media.SN_SEARCH_TRANSPORT_STATE);

        //-+-+-+-+-+-+- set needed data -+-+-+-+-+-+-
        Query<EventEntity> query = ofy().load().type(EventEntity.class);
        QueryResultIterator<EventEntity> queryIterator = query.iterator();
        List<EventEntity> eventEntityList = new ArrayList<EventEntity>();
        while (queryIterator.hasNext()) {
            eventEntityList.add(queryIterator.next());
        }
        respons.availableEvent=eventEntityList;

        //TODO conservert state dans ofy;

        return respons;
    }

    static Media doInscriptionAsDriver(Media media){

        //-+-+-+-+-+-+- get data -+-+-+-+-+-+-
        DriverEntity owner = new DriverEntity(
                media.owner.getUserName(),
                media.owner.getEmail(),
                media.owner.getPhoneNumber()
        );
        ofy().save().entity(owner).now();

        EventEntity eventEntity = ofy().load().entity(media.selectedEvent).now();

        //-+-+-+-+-+-+- clean data -+-+-+-+-+-+-
        media.cleanAll();

        //-+-+-+-+-+-+- set state type -+-+-+-+-+-+-
        media.stateType= Media.SN_CREATE_TRANSPORT_STATE;

        //-+-+-+-+-+-+- set next step -+-+-+-+-+-+-
        media.availableStates= new ArrayList<String>();
        media.availableStates.add(Media.SN_IN_LIFT_STATE);

        //-+-+-+-+-+-+- set needed data -+-+-+-+-+-+-
        media.owner = ofy().load().entity(owner).now();
        media.selectedEvent = ofy().load().entity(eventEntity).now();

        //TODO conservert state dans ofy;
        return media;
    }
    static Media doInscriptionAsDrinker(Media media){

        //-+-+-+-+-+-+- get data -+-+-+-+-+-+-
        DrinkerEntity owner = new DrinkerEntity(
                media.owner.getUserName(),
                media.owner.getEmail(),
                media.owner.getPhoneNumber()
        );
        ofy().save().entity(owner).now();

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


        //TODO conserver state dans ofy;
        return media;
    }
}
