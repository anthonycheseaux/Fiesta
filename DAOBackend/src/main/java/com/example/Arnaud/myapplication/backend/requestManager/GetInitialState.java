package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.service.Media;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 30.08.2016.
 */
class GetInitialState extends AbstractManager {

    private static final Logger logger = Logger.getLogger(GetInitialState.class.getName());

    static final String[] misssions = new String[]{
            Media.SN_INSCRIPTION_STATE+ Facade.CONNECTION_TO + Media.SN_INSCRIPTION_STATE
    };

    GetInitialState(){
        super(new Media());
    }

    GetInitialState(Media media) {
        super(media);
    }

    @Override
    protected boolean securityCheck() {
        return true;
    }

    @Override
    protected boolean checkDataConsistency() {
        return true;
    }


    @Override
    protected void getData() {

    }

    @Override
    protected void setStateType() {
        media.stateType=Media.SN_INSCRIPTION_STATE;
    }

    @Override
    protected void setNextStep() {
        media.availableStates= new ArrayList<String>();
        media.availableStates.add(Media.SN_CREATE_TRANSPORT_STATE);
        media.availableStates.add(Media.SN_SEARCH_TRANSPORT_STATE);
    }

    @Override
    protected void setNededData() {
        Query<EventEntity> query = ofy().load().type(EventEntity.class);
        QueryResultIterator<EventEntity> queryIterator = query.iterator();
        List<EventEntity> eventEntityList = new ArrayList<EventEntity>();
        while (queryIterator.hasNext()) {
            eventEntityList.add(queryIterator.next());
        }
        media.availableEvent=eventEntityList;
    }
}
