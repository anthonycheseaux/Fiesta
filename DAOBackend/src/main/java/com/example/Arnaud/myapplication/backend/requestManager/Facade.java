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
    /*
   Table of possible path, left is initail state, up is next step

                              |ins|ct |ml |st |dil|
       -----------------------+---+---+---+---+---+
       inscription      (ins) | X | X |   | X |   |
       -----------------------+---+---+---+---+---+
       create tranport  (ct)  |   | O | X |   |   |
       -----------------------+---+---+---+---+---+
       manage Lift      (ml)  |   |   | O |   |   |
       -----------------------+---+---+---+---+---+
       search transport (st)  |   |   |   | O | O |
       -----------------------+---+---+---+---+---+
       drinker in lift  (dil) |   |   |   | O | O |
       -----------------------+---+---+---+---+---+

       X=> done
       O=> nedeed

       command's key will be [initial state]+[CONNECTION_TO]+[wanted state]
    */
    private final static GetInitialState defaultManager=new GetInitialState();
    private HashMap<String,Constructor<? extends AbstractManager>> constructors;
    final static String CONNECTION_TO=">>";

    public Facade(){
        constructors = new HashMap<>(16);

        Class[] argsForConstruct= new Class[]{Media.class};
        try {
            for (int cpt =0; cpt < AddTransport.misssions.length;++cpt )
                constructors.put(AddTransport.misssions[cpt],
                        AddTransport.class.getDeclaredConstructor(argsForConstruct));

            for (int cpt =0; cpt < GetInitialState.misssions.length;++cpt )
                constructors.put(GetInitialState.misssions[cpt],
                        GetInitialState.class.getDeclaredConstructor(argsForConstruct));

            for (int cpt =0; cpt < InscriptionAsDrinker.misssions.length;++cpt )
                constructors.put(InscriptionAsDrinker.misssions[cpt],
                        InscriptionAsDrinker.class.getDeclaredConstructor(argsForConstruct));

            for (int cpt =0; cpt < InscriptionAsDriver.misssions.length;++cpt )
                constructors.put(InscriptionAsDriver.misssions[cpt],
                        InscriptionAsDriver.class.getDeclaredConstructor(argsForConstruct));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Media manage(Media media) {
        if (media.wantedState== null)
            media.wantedState=media.stateType;

        AbstractManager manger= instanciateManager(media);

        return manger.permformeManagment();
    }

    @Override
    public Media getInitalState() {
        return defaultManager.permformeManagment();
    }

    private AbstractManager instanciateManager(Media media){
        AbstractManager manger= null;

        Constructor<? extends AbstractManager> constuctor = constructors.get(media.stateType+CONNECTION_TO+media.wantedState);
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
