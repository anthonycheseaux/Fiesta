package com.example.Arnaud.myapplication.backend.service;

import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.googlecode.objectify.annotation.Subclass;

/**
 * Created by Arnaud on 12.08.2016.
 */
@Subclass
public class BackCreateTransportState extends BackAbstractState{
    public LiftEntity lift;

    @Override
    void unWrap(StateWraper wraper) {

    }
    @Override
    int getSize() {
        return 0;
        //return super.getSize()+4/*user Data*/+4/*selected event data */+1/*eventListSize*/+(eventList.length*4/* event data*/);

    }

    @Override
    void cloneAtrigute(StateWraper wraper) {/*
        super.cloneAtrigute(wraper);
        if (user != null) {
            if(user.getId()!= null)
                wraper.content.put(USER + UserEntity.ID, "" + user.getId());
            else
                wraper.content.put(USER + UserEntity.ID, "" + -1);
            wraper.content.put(USER + UserEntity.USERNAME, user.getUserName());
            wraper.content.put(USER + UserEntity.EMAIL, user.getEmail());
            wraper.content.put(USER + UserEntity.PHONE_NUMBER, user.getPhoneNumber());
        }

        if(selctedEvent != null){
            wraper.content.put(SELECTED_EVENT+ EventEntity.ID, selctedEvent.getId().toString());
            wraper.content.put(SELECTED_EVENT+EventEntity.NAME, selctedEvent.getName());

            if(selctedEvent.getBeginning()!= null)
                wraper.content.put(SELECTED_EVENT+EventEntity.BEGINNING,selctedEvent.getBeginning().toString());
            else
                wraper.content.put(SELECTED_EVENT+EventEntity.BEGINNING, "");

            if( selctedEvent.getEnd()!= null)
                wraper.content.put(SELECTED_EVENT+EventEntity.END, selctedEvent.getEnd().toString());
            else
                wraper.content.put(SELECTED_EVENT+EventEntity.END, "");
        }

        wraper.content.put(EVENT_LIST_SIZE, eventList.length+"");
        for (int cpt = 0 ; cpt < eventList.length; ++cpt){
            wraper.content.put(EventEntity.ID+cpt, eventList[cpt].getId().toString());
            wraper.content.put(EventEntity.NAME+cpt, eventList[cpt].getName());

            if(eventList[cpt].getBeginning()!= null)
                wraper.content.put(EventEntity.BEGINNING+cpt, eventList[cpt].getBeginning().toString());
            else
                wraper.content.put(EventEntity.BEGINNING+cpt, "");

            if( eventList[cpt].getEnd()!= null)
                wraper.content.put(EventEntity.END+cpt, eventList[cpt].getEnd().toString());
            else
                wraper.content.put(EventEntity.END+cpt, "");
        }*/
    }
}
