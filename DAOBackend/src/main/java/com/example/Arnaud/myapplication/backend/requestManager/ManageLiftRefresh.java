package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;
import com.example.Arnaud.myapplication.backend.triggers.NotifyDrinkers_LiftContentChange;
import com.example.Arnaud.myapplication.backend.triggers.NotifyDrinkers_on_liftUpdate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

    /**
     * how to manage:
     * first put all drinkers who was in the old version of lif in a basket.
     * then add all added-drinkers in the basquet
     * finaly remove all removed-drinkers from th basket
     * launch triggers and TADAAAAA
     */
    @Override
    protected void perfomeActions() {
        List<UserEntity> added = new LinkedList<>();
        List<UserEntity> removed = new LinkedList<>();
        List<UserEntity> sended = lift.getDrinkers();
        List<UserEntity> old = ofy().load().type(LiftEntity.class).id(lift.getId()).now().getDrinkers();
        HashMap<String, UserEntity> basket = new HashMap<>();


        if (old != null && old.size()>0)
            for (Iterator<UserEntity> iterator = old.iterator(); iterator.hasNext();){
                UserEntity tmp = iterator.next();
                basket.put(tmp.getEmail(), tmp);
            }
        boolean hasChange = false;
        if (sended!= null)
            for (Iterator<UserEntity> iterator = sended.iterator(); iterator.hasNext();){
                UserEntity tmp = iterator.next();
                if (tmp.getUserName().equals("added")){
                    tmp = ofy().load().type(UserEntity.class).id(tmp.getEmail()).now();
                    basket.put(tmp.getEmail(),tmp);
                    added.add(tmp);
                    hasChange = true;
                }else if (tmp.getUserName().equals("removed")){
                    basket.remove(tmp.getEmail());
                    removed.add(tmp);
                    hasChange = false;
                }
            }
        if (hasChange)
            triggers.addAtEnd(new NotifyDrinkers_on_liftUpdate( added, removed, this.lift));

        lift.setDrinkers(new ArrayList<>(basket.values()));

        ofy().save().entity(this.lift);


        this.lift = ofy().load().entity(lift).now();

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
