package com.example.Arnaud.myapplication.backend.service;


/**
 * Created by Arnaud on 12.08.2016.
 */

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public abstract class BackAbstractState {
    @Id
    Long id =new Long(-1);
    String[] validNextStep = new String[0] ;
    String wantedNextStep = "";

    int getSize(){
        return 3+validNextStep.length;
    }
    void cloneAtrigute(StateWraper wraper){
       wraper.content.put("state_id", id.toString());
        wraper.content.put("validNextStepSize", validNextStep.length+"");
       for (int cpt =0 ; cpt< validNextStep.length;++cpt)
           wraper.content.put("validNextStep"+cpt, validNextStep[cpt]);
       wraper.content.put(StateWraper.MA_WANTED_STATE, wantedNextStep);
    }
    abstract void unWrap(StateWraper wraper);
}
