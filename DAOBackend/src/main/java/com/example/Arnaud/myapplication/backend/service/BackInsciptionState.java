package com.example.Arnaud.myapplication.backend.service;

import com.example.Arnaud.myapplication.backend.DrinkerEntity;
import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.repackaged.com.google.common.base.Equivalence;
import com.google.appengine.repackaged.com.google.common.base.Flag;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.cmd.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.soap.SOAPBinding;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 12.08.2016.
 */
@Subclass
public class BackInsciptionState extends BackAbstractState {
    public final static String CLASS_PREFIX = "InsciptionState_";

    EventEntity[] eventList = new EventEntity[0];
    private static final String EVENT_LIST_SIZE =CLASS_PREFIX+ "eventListSize";

    private static final String USER =CLASS_PREFIX+ "user_";
    public UserEntity user;


    private static final String SELECTED_EVENT =CLASS_PREFIX+ "selctedEvent_";
    public EventEntity selctedEvent;



    BackInsciptionState(){
        Query<EventEntity> query = ofy().load().type(EventEntity.class);
         QueryResultIterator<EventEntity> queryIterator = query.iterator();
        List<EventEntity> eventEntityList = new ArrayList<EventEntity>();
        while (queryIterator.hasNext()) {
            eventEntityList.add(queryIterator.next());
        }
        eventList = new EventEntity[eventEntityList.size()];
        eventEntityList.toArray(eventList);
        this.validNextStep = new String[]{
                StateWraper.SN_SEARCH_TRANSPORT_STATE,
                StateWraper.SN_CREATE_TRANSPORT_STATE
        };
    }



    @Override
    int getSize() {
        return super.getSize()+4/*user Data*/+4/*selected event data */+1/*eventListSize*/+(eventList.length*4/* event data*/);

    }

    @Override
    void cloneAtrigute(StateWraper wraper) {
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
            wraper.content.put(SELECTED_EVENT+EventEntity.ID, selctedEvent.getId().toString());
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
        }
    }

    @Override
    void unWrap(StateWraper wraper) {

        if(wraper.content.containsKey(USER+UserEntity.ID)){
            Long id;
            String name;
            String email;
            String phone;

            id = Long.parseLong(wraper.content.get(USER+UserEntity.ID));
            name = wraper.content.get(USER+UserEntity.USERNAME);
            email =wraper.content.get(USER+UserEntity.EMAIL);
            phone = wraper.content.get(user+UserEntity.PHONE_NUMBER);

            if(id.longValue()==-1)
                id=null;
            if(wraper.content.get(StateWraper.MA_WANTED_STATE)==StateWraper.SN_CREATE_TRANSPORT_STATE)
                this.user = new DriverEntity(id, name, email, phone);
            else
                this.user = new DrinkerEntity(id, name,email,phone);
        }
        if(wraper.content.containsKey(SELECTED_EVENT+EventEntity.ID)){
            Long id = Long.parseLong(wraper.content.get(SELECTED_EVENT+EventEntity.ID));
            String name = wraper.content.get(SELECTED_EVENT+EventEntity.NAME);
            Date begining = new Date (Date.parse(wraper.content.get(SELECTED_EVENT+EventEntity.BEGINNING)));
            Date end = new Date (Date.parse(wraper.content.get(SELECTED_EVENT+EventEntity.END)));
            selctedEvent= new EventEntity(id,name,begining,end);
        }

        eventList = new EventEntity[Integer.parseInt(wraper.content.get(EVENT_LIST_SIZE))];
        for (int cpt =0 ; cpt<eventList.length;++cpt){
            Long id = Long.parseLong(wraper.content.get(EventEntity.ID+cpt));
            String name = wraper.content.get(EventEntity.NAME+cpt);
            Date begining = new Date (Date.parse(wraper.content.get(EventEntity.BEGINNING+cpt)));
            Date end = new Date (Date.parse(wraper.content.get(EventEntity.END+cpt)));
            eventList[cpt]= new EventEntity(id,name,begining,end);
        }
    }

}
