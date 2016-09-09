package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;
import com.example.Arnaud.myapplication.backend.triggers.DrinkerLeaveLift;
import com.example.Arnaud.myapplication.backend.triggers.NotifyDrinkers_on_liftUpdate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 08.09.2016.
 */
class DrinkerUnregistration extends AbstractManager {
    static final String[] misssions = new String[]{
            _NavigationsRules.SN_IN_LIFT_STATE+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_SEARCH_TRANSPORT_STATE
    };

    private EventEntity selectedEvent;
    private List<LiftEntity> availebleLifts;
    private LiftEntity oldLift;
    /**
     * @param media the media who will be managed
     */
    DrinkerUnregistration(Media media) {
        super(media);
    }

    @Override
    protected void getData() throws Exception {
        owner = ofy().load().type(UserEntity.class).id(media.owner.getEmail()).now();
        oldLift = media.lift;
        selectedEvent = media.lift.getEvent();

    }

    @Override
    protected void setState() {
        media.stateType=_NavigationsRules.SN_SEARCH_TRANSPORT_STATE;
    }

    @Override
    protected void perfomeActions() {
        triggers.addAtTop(new DrinkerLeaveLift(oldLift,owner));

        List<LiftEntity> allLift = ofy().load().type(LiftEntity.class).list();
        availebleLifts = new LinkedList<>();
        for (Iterator<LiftEntity> iterator = allLift.iterator(); iterator.hasNext();){
            LiftEntity tmp = iterator.next();
            if (false == tmp.isFull())
                if (0==(selectedEvent.getId()-tmp.getEvent().getId()))
                    availebleLifts.add(tmp);
        }
    }

    @Override
    protected void setNededData() {
        media.owner = owner;
        media.selectedEvent = selectedEvent;
        media.availableLifts =availebleLifts;
    }
}
