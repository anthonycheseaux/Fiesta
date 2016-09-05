package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;
import com.example.Arnaud.myapplication.backend.triggers.NotifyDrinkers_LiftContentChange;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 03.09.2016.
 */
class ManageLiftRefresh extends AbstractManager {
    static final String[] misssions = new String[]{
            _NavigationsRules.SN_MANAGE_LIFT+ _NavigationsRules.CONNECTION_TO + _NavigationsRules.SN_MANAGE_LIFT
    };
    private LiftEntity lift;
    private List<UserEntity> oldContent;

    /**
     * @param media the media who will be managed
     */
    ManageLiftRefresh(Media media) {
        super(media);
    }

    @Override
    protected void getData() throws Exception {
        this.owner = media.owner;
        this.lift = media.lift;
    }

    @Override
    protected void perfomeActions() {
        LiftEntity oldLift = ofy().load().type(LiftEntity.class).id(lift.getId()).now();
        oldContent = oldLift.getDrikers();
        ofy().save().entity(this.lift);
        this.lift = ofy().load().entity(this.lift).now();
        triggers.addAtEnd(new NotifyDrinkers_LiftContentChange(this.lift,oldContent));
        this.owner = ofy().load().entity(this.owner).now();
        this.owner.putMails();
    }

    @Override
    protected void setState() {
        media.stateType=_NavigationsRules.SN_MANAGE_LIFT;

    }

    @Override
    protected void setNededData() {
        media.owner= this.owner;
        media.lift = this.lift;

    }
}
