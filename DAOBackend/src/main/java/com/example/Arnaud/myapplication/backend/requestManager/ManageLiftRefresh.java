package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;
import com.example.Arnaud.myapplication.backend.triggers.NotifyDrinkers_LiftContentChange;
import com.example.Arnaud.myapplication.backend.triggers.NotifyDrinkers_on_liftUpdate;

import java.util.Iterator;
import java.util.LinkedList;
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
        List<UserEntity> added = new LinkedList<>();
        List<UserEntity> removed = new LinkedList<>();
        List<UserEntity> sended = lift.getDrinkers();
        List<UserEntity> current = new LinkedList<>();
        if (sended != null){
            for (Iterator<UserEntity> iterator = sended.iterator(); iterator.hasNext();){
                UserEntity tmp = iterator.next();
                if (tmp.getUserName().equals("added")){
                    tmp = ofy().load().type(UserEntity.class).id(tmp.getEmail()).now();
                    added.add(tmp);
                    current.add(tmp);
                }else if (tmp.getUserName().equals("removed")){
                    removed.add(tmp);
                }
                else {
                    current.add(tmp);
                }

            }

        }
        this.lift.setDrinkers(current);
        ofy().save().entity(this.lift);
        this.lift = ofy().load().entity(lift).now();
        triggers.addAtEnd(new NotifyDrinkers_on_liftUpdate( added, removed, this.lift));
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
