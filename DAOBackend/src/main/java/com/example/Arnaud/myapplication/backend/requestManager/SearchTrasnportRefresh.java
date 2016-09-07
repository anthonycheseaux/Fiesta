package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 07.09.2016.
 */
@Deprecated
class SearchTrasnportRefresh extends AbstractManager {
    static final String[] misssions = new String[]{
            _NavigationsRules.SN_SEARCH_TRANSPORT_STATE+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_SEARCH_TRANSPORT_STATE
    };



    protected List<LiftEntity> availebleLift;
    protected EventEntity selectedEvent;
    /**
     * @param media the media who will be managed
     */
    SearchTrasnportRefresh(Media media) {
        super(media);
    }

    @Override
    protected void getData() throws Exception {
        owner = ofy().load().type(UserEntity.class).id(media.owner.getEmail()).safe();
        selectedEvent = media.selectedEvent;
        selectedEvent =ofy().load().entity(selectedEvent).now();
    }


    @Override
    protected void setState() {
        media.stateType = _NavigationsRules.SN_SEARCH_TRANSPORT_STATE;

    }

    @Override
    protected void cleanMedia() {

    }

    @Override
    protected void perfomeActions() {
        this.owner.putMails();
        selectedEvent =ofy().load().entity(selectedEvent).now();
        availebleLift= ofy().load().type(LiftEntity.class).list();
    }

    @Override
    protected void setNededData() {
        media.owner = this.owner;
        media.selectedEvent = this.selectedEvent;
        media.availableLifts = this.availebleLift;
    }
}
