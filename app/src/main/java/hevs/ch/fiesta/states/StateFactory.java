package hevs.ch.fiesta.states;


import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;

import java.util.HashMap;

/**
 * Created by Arnaud on 19.08.2016.
 *
 * the factory for states.
 * we use a hashmap to use "metode as object" pattern
 * simply, each Media wil be adapted in his correct state by using his StateType
 */
class StateFactory {

//____________constructor________________________________
    // package local so only States will have acces to it
    StateFactory(){
        prepareMapper();
    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


//____________[key : Methode] part______________________
    private HashMap<String, Instancier> mapper;

    //------------use of the methode-------------------------
    public MediaAdapter adapt(Media media){
        if(media == null)
            return null;
        Instancier instancier = mapper.get(media.getStateType());
        if (instancier == null)
            return null;
        return instancier.instanciate(media);
    }
    //------------setting up---------------------------------


    private void prepareMapper(){
        mapper= new HashMap<>();


        //we use state Type referenced in MediaAdapter as Keys
        mapper.put(MediaAdapter.SN_INSCRIPTION_STATE,
                new InsciptionInstancier());

        mapper.put(MediaAdapter.SN_CREATE_TRANSPORT_STATE,
                new CreateTransportInstancier());

        mapper.put(MediaAdapter.SN_IN_LIFT_STATE,
                new DrinkerInLiftInstancier());

        mapper.put(MediaAdapter.SN_MANAGE_LIFT,
                new ManageLiftInstancier());

        mapper.put(MediaAdapter.SN_SEARCH_TRANSPORT_STATE,
                new SearchLiftInstancier());

    }


    //------------the generic command------------------------
    private abstract class Instancier{
        abstract MediaAdapter instanciate(Media media);
    }
    //------------the concrete commands----------------------
    private class InsciptionInstancier extends Instancier{
        @Override
        MediaAdapter instanciate(Media media) {
            return new InscriptionState(media);
        }
    }

    private class CreateTransportInstancier extends Instancier{
        @Override
        MediaAdapter instanciate(Media media) {
            return new CreateTransportState(media);
        }
    }
    private class SearchLiftInstancier extends Instancier{
        MediaAdapter instanciate(Media media) {
            return new SearchTrasnportState(media);
        }
    }


    private class ManageLiftInstancier extends Instancier{
        @Override
        //TODO changer ici
        MediaAdapter instanciate(Media media) {
            return new InscriptionState(media);
        }
    }

    private class DrinkerInLiftInstancier extends Instancier{
        //TODO changer ici aussi
        @Override
        MediaAdapter instanciate(Media media) {
            return new CreateTransportState(media);
        }
    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
