package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.service.Manager;
import com.example.Arnaud.myapplication.backend.service.Media;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by Arnaud on 30.08.2016.
 */
public class Facade implements Manager {


    private final static _Factory factory = new _Factory();

    public Facade(){

    }

    @Override
    public Media manage(Media media) {
        if (media.wantedState== null)
            media.wantedState=media.stateType;

        AbstractManager manger= factory.instanciateManager(media);

        return manger.permformeManagment();
    }

    @Override
    public Media getInitalState() {
        return new GetInitialState().permformeManagment();
    }

}
