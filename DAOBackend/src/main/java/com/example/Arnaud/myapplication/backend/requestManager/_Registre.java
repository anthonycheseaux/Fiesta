package com.example.Arnaud.myapplication.backend.requestManager;

import com.example.Arnaud.myapplication.backend.service.Media;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;

/**
 * Created by Arnaud on 02.09.2016.
 */
class _Registre {
    private final static boolean ON_DEBUG = true;

    private static final Logger logger = Logger.getLogger(_Registre.class.getName());


    private final static Class<? extends AbstractManager>[] registre = new Class[]{
            GetInitialState.class,      //A
            InscriptionAsDriver.class,  //B
            AddTransport.class,         //C
            ManageLiftRefresh.class,    //D
            InscriptionAsDrinker.class, //E
            LiftUnregistration.class,   //F
            DrinkerUnregistration.class,//G
            DrinkerLifeCyle.class       //H
    };
        /*
   Table of possible path, left is initail state, up is next step

                              |ins|ct |ml |st |dil|
       -----------------------+---+---+---+---+---+
       inscription      (ins) | A | B |   | E |   |
       -----------------------+---+---+---+---+---+
       create tranport  (ct)  |   | B | C |   |   |
       -----------------------+---+---+---+---+---+
       manage Lift      (ml)  | F |   | D |   |   |
       -----------------------+---+---+---+---+---+
       search transport (st)  | A |   |   | H |Hsc|
       -----------------------+---+---+---+---+---+
       drinker in lift  (dil) |   |   |   |G/H| H |
       -----------------------+---+---+---+---+---+
*/

    static void doRegostrationFor(_Factory fact)  {
        if (ON_DEBUG)
            logger.info(" \n Start Registrations");

        Class[] args = new Class[]{Media.class};
        for(int cpt =registre.length-1; cpt>= 0;--cpt){
            if (ON_DEBUG)
                logger.info(" \n registrate "+registre[cpt].getName());
            String[] missions = new String[]{};

            try {
                missions = (String[]) registre[cpt].getDeclaredField("misssions").get(missions);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            Constructor<? extends AbstractManager> constructor = null;

            try {
                constructor = registre[cpt].getDeclaredConstructor(args);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            for (int missionsIndex= 0; missionsIndex < missions.length;++ missionsIndex)
                fact.registration(missions[missionsIndex], constructor);

        }
    }
}
