package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;
import com.googlecode.objectify.NotFoundException;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 31.08.2016.
 */
public abstract class Inscription extends AbstractManager {

    protected EventEntity selectedEvent;

    Inscription(Media media) {
        super(media);
    }

    @Override
    protected final boolean checkDataConsistency() {
        boolean respons;
        if (respons= media.owner != null)
            if (respons= media.owner.getEmail() != null)
                if (respons= (false == media.owner.getEmail().equals("")))
                    if (respons = media.selectedEvent!= null)
                        ;
        return respons;
    }

    @Override
    protected final void getData() {
        try {
            owner = ofy().load().type(UserEntity.class).id(media.owner.getEmail()).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {

        }
        if( owner == null)
            owner = new UserEntity(
                    media.owner.getUserName(),
                    media.owner.getEmail(),
                    media.owner.getPhoneNumber());
        else {
            owner.setUserName(media.owner.getUserName());
            owner.setPhoneNumber(media.owner.getPhoneNumber());
        }

        ofy().save().entity(owner).now();
        selectedEvent = ofy().load().entity(media.selectedEvent).now();

    }

}
