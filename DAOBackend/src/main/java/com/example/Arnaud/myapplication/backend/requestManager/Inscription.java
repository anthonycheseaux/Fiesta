package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 31.08.2016.
 */
abstract class Inscription extends AbstractManager {

    protected EventEntity selectedEvent;

    Inscription(Media media) {
        super(media);
    }

    @Override
    protected final void getData() throws NullPointerException {
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
        selectedEvent = media.selectedEvent;
    }

}
