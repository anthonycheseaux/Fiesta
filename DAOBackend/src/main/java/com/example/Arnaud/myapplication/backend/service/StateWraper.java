package com.example.Arnaud.myapplication.backend.service;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Arnaud on 12.08.2016.
 */
@Entity
public class StateWraper implements Serializable {
    public final static String SN_INSCRIPTION_STATE = "inscription_state";
    public final static String SN_CREATE_TRANSPORT_STATE = "create_transport_state";
    public final static String SN_SEARCH_TRANSPORT_STATE = "search_transport_state";

    public final static String MA_WANTED_STATE = "wanted_state";

    @Id
    public long id;
    public String stateType;

    public HashMap<String, String> content;



    StateWraper(BackInsciptionState insciptionState){
        stateType = SN_INSCRIPTION_STATE;
        content = new HashMap<String , String>((int)(insciptionState.getSize()*1.2));
        insciptionState.cloneAtrigute(this);
    }


}
