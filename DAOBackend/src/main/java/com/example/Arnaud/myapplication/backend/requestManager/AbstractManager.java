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

    /**
     *
     * @param media the media who will be managed
     */
    AbstractManager(Media media){
        triggers = new NullTriger();
        this.media=media;
    }

    /**
     * perfom all action defined by the templae
     * @return the treated media
     */
    public Media permformeManagment(){
        if (ON_DEBUG)
            logger.info(" \n Getting media for managment whith: " + media.stateType);

        if(false == securityCheck()){
            if (ON_DEBUG)
                logger.info(" \n media fail security check: " + media.stateType);
            return media;
        }
        try {
            getData();
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return media;
        }

        cleanMedia();
        perfomeActions();
        setNavigation();
        setNededData();
        executeTriggers();
        if (ON_DEBUG)
            logger.info(" \n respons whith: " + media.stateType);

        return media;
    }

    /**
     *
     * @return true if security chexk pass or false if fail
     */
    protected boolean securityCheck(){
        return true;
    }

    /**
     * get all needed data from the media, can trow nullpointer exception if the needed data are not in the media
     * @throws NullPointerException
     */
    protected abstract void getData() throws NullPointerException;

    /**
     * clean all medaia data
     */
    private final void cleanMedia(){
        media.cleanAll();
    }

    /**
     * performe aditional cations if needed
     */
    protected void perfomeActions(){

    }

    /**
     * set the state and the list of available state
     */
    protected abstract void setNavigation();

    /**
     * set data needed for the current state
     */
    protected abstract void setNededData();

    /**
     * launch triggers if some are raised in the application
     */
    private final void executeTriggers(){
        triggers.lauchTriggerChain();
    }

}
