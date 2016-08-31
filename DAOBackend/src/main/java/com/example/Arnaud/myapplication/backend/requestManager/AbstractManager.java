package com.example.Arnaud.myapplication.backend.requestManager;


import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;
import com.example.Arnaud.myapplication.backend.triggers.AbstractTrigger;
import com.example.Arnaud.myapplication.backend.triggers.NullTriger;

/**
 * Created by Arnaud on 30.08.2016.
 */
abstract class AbstractManager {
    static String[] missions;
    protected Media media;
    protected AbstractTrigger triggers;
    protected UserEntity owner;

    AbstractManager(Media media){
        triggers = new NullTriger();
        this.media=media;
    }

    public static String getDD(){return "dd";}

    public Media permformeManagment(){
        securityCheck();
        if (false ==checkDataConsistency())
            return media;
        getData();
        cleanMedia();
        setStateType();
        setNextStep();
        setNededData();
        executeTriggers();
        return media;
    }
    protected void securityCheck(){

    }
    protected abstract boolean checkDataConsistency();
    protected abstract void getData();
    private final void cleanMedia(){
        media.cleanAll();
    }
    protected abstract void setStateType();
    protected abstract void setNextStep();
    protected abstract void setNededData();
    private final void executeTriggers(){
        triggers.lauchTriggerChain();
    }

}
