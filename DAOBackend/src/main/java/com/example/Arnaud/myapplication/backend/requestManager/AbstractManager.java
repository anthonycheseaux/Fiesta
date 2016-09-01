package com.example.Arnaud.myapplication.backend.requestManager;


import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;
import com.example.Arnaud.myapplication.backend.triggers.AbstractTrigger;
import com.example.Arnaud.myapplication.backend.triggers.NullTriger;

import java.util.logging.Logger;

/**
 * Created by Arnaud on 30.08.2016.
 */
abstract class AbstractManager {
    protected static final Logger logger = Logger.getLogger(AbstractManager.class.getName());
    protected static final boolean ON_DEBUG = true;

    protected Media media;
    protected AbstractTrigger triggers;
    protected UserEntity owner;

    AbstractManager(Media media){
        triggers = new NullTriger();
        this.media=media;
    }


    public Media permformeManagment(){
        if (ON_DEBUG)
            logger.info(" \n Getting media for managment whith: " + media.stateType);

        if(false == securityCheck())
            return media;
        if (false ==checkDataConsistency()){
            logger.info(" \n media DOES NOT succesfully consistency Check.");
            return media;
        }
        if (ON_DEBUG)
            logger.info(" \n media does succesfully consistency Check.");


        getData();
        cleanMedia();
        setStateType();
        setNextStep();
        setNededData();
        executeTriggers();
        if (ON_DEBUG)
            logger.info(" \n respons whith: " + media.stateType);

        return media;
    }
    protected boolean securityCheck(){
        return true;
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
