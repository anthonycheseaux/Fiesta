package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.service.Media;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by Arnaud on 02.09.2016.
 */
class _Factory {
    private HashMap<String, Constructor<? extends AbstractManager>> constructors;
    private AbstractManager defaultManager = new GetInitialState();

    _Factory(){
        constructors = new HashMap<>();
        _Registre.doRegostrationFor(this);
    }
    void registration(String key, Constructor<? extends AbstractManager> constructor){
        constructors.put(key,constructor);
    }
    AbstractManager instanciateManager( Media media){
        AbstractManager manger= null;

        Constructor<? extends AbstractManager> constuctor = constructors.get(media.stateType+_NavigationsRules.CONNECTION_TO+media.wantedState);
        if (constuctor!= null)
            try {
                manger=constuctor.newInstance(media);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        if (manger==null)
            manger=defaultManager;

        return manger;
    }
}
