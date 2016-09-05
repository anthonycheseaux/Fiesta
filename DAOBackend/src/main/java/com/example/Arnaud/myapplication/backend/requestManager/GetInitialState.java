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
    static final String[] misssions = new String[]{
            _NavigationsRules.SN_INSCRIPTION_STATE+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_INSCRIPTION_STATE
    };

    GetInitialState(){
        super(new Media());
    }

    GetInitialState(Media media) {
        super(media);
        logger = Logger.getLogger(GetInitialState.class.getName());
    }

    @Override
    protected boolean securityCheck() {
        return true;
    }

    @Override
    protected void getData()  {

    }

    @Override
    protected void perfomeActions() {

    }

    @Override
    protected void setState() {
        media.stateType = _NavigationsRules.SN_INSCRIPTION_STATE;
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
