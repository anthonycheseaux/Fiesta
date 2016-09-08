package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.service.Media;
import com.example.Arnaud.myapplication.backend.triggers.LiftUnregistring;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 08.09.2016.
 */
public class LiftUnregistration extends AbstractManager {
    static final String[] misssions = new String[]{
            _NavigationsRules.SN_MANAGE_LIFT+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_INSCRIPTION_STATE
    };
    private LiftEntity lift;

    /**
     * @param media the media who will be managed
     */
    LiftUnregistration(Media media) {
        super(media);
    }

    @Override
    protected void getData() throws Exception {
        this.owner = media.owner;
        this.lift = media.lift;
    }

    @Override
    protected void setState() {
        media.stateType=_NavigationsRules.SN_INSCRIPTION_STATE;
    }

    @Override
    protected void perfomeActions() {
        triggers.addAtEnd(new LiftUnregistring(this.lift));
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
