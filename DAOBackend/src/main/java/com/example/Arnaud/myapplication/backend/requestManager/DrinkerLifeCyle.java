package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.DrinkerMapperEntity;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 07.09.2016.
 */

class DrinkerLifeCyle extends AbstractManager {
    static final String[] misssions = new String[]{
            _NavigationsRules.SN_SEARCH_TRANSPORT_STATE+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_SEARCH_TRANSPORT_STATE,
            _NavigationsRules.SN_IN_LIFT_STATE+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_IN_LIFT_STATE
    };
    private boolean hasLift;
    private LiftEntity potentialLift;
    private DrinkerMapperEntity dme;
    protected List<LiftEntity> availebleLift;
    protected EventEntity selectedEvent;
    /**
     * @param media the media who will be managed
     */
    DrinkerLifeCyle(Media media) {
        super(media);
    }

    @Override
    protected void getData() throws Exception {
        owner = ofy().load().type(UserEntity.class).id(media.owner.getEmail()).now();
        selectedEvent =ofy().load().entity( media.selectedEvent).now();
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
        ofy().save().entity(owner).now();
        this.owner = ofy().load().entity(owner).now();
        this.owner.putMails();

        try {
            dme = ofy().load().type(DrinkerMapperEntity.class).id(owner.getEmail()+selectedEvent.getId()).safe();
        }catch (com.googlecode.objectify.NotFoundException e) {
        }

        selectedEvent =ofy().load().entity(selectedEvent).now();

        if (dme == null){
            dme = new DrinkerMapperEntity(selectedEvent,owner);
            ofy().save().entity(dme);
        }
        hasLift= (dme.getLiftEntity()!= null);
        if (hasLift){
            potentialLift= dme.getLiftEntity();
            potentialLift = ofy().load().entity(potentialLift).now();
            if (potentialLift!= null)
                media.stateType = _NavigationsRules.SN_IN_LIFT_STATE;
            else hasLift= false;
        }
        if (false == hasLift){
            List<LiftEntity> allLift = ofy().load().type(LiftEntity.class).list();
            availebleLift = new LinkedList<>();
            for (Iterator<LiftEntity> iterator = allLift.iterator(); iterator.hasNext();){
                LiftEntity tmp = iterator.next();
                if (false == tmp.isFull())
                    if (0==(selectedEvent.getId()-tmp.getEvent().getId()))
                        availebleLift.add(tmp);
            }
        }
    }


    @Override
    protected void setNededData() {
        media.owner = owner;

        if (hasLift){
            media.lift= potentialLift;
        }else {
            media.selectedEvent = selectedEvent;
            media.availableLifts =availebleLift;
        }
    }
}
