package com.example.Arnaud.myapplication.backend.service;

import com.example.Arnaud.myapplication.backend.DrinkerEntity;
import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.service.Media;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by Arnaud on 12.08.2016.
 */
class RequestManger {
    /*
    Table of possible path, left is initail state, up is next step

                               |ins|ct |ml |st |dil|
        -----------------------+---+---+---+---+---+
        inscription      (ins) | X | X |   | X |   |
        -----------------------+---+---+---+---+---+
        create tranport  (ct)  |   | X | X |   |   |
        -----------------------+---+---+---+---+---+
        manage Lift      (ml)  |   |   | X |   |   |
        -----------------------+---+---+---+---+---+
        search transport (st)  |   |   |   | X | X |
        -----------------------+---+---+---+---+---+
        drinker in lift  (dil) |   |   |   | X | X |
        -----------------------+---+---+---+---+---+

        command's key will be [initial state]+[CONNECTION_TO]+[wanted state]
     */
    private final String CONNECTION_TO=">>";

    public RequestManger(){
        prepareMapper();
    }

    public Media getInitialState(){
        return InscriptionHelper.getInscriptionState();
    }


    //____________[key : Methode] part______________________
    private HashMap<String, Manager> mapper;

    //------------use of the methode-------------------------

    public Media manage (Media media){
        Manager manager = mapper.get(media.stateType + CONNECTION_TO + media.wantedState);

        if (manager == null)
            return media;
        return manager.manage(media);
    }

    //------------setting up---------------------------------


    private void prepareMapper() {
        mapper = new HashMap<>(16);

        //from inscription
        mapper.put(Media.SN_INSCRIPTION_STATE + CONNECTION_TO + Media.SN_INSCRIPTION_STATE,
                new Inscription_to_inscription());
        mapper.put(Media.SN_INSCRIPTION_STATE + CONNECTION_TO + Media.SN_CREATE_TRANSPORT_STATE,
                new Inscription_to_CreateTransport());
        mapper.put(Media.SN_INSCRIPTION_STATE + CONNECTION_TO + Media.SN_SEARCH_TRANSPORT_STATE,
                new Inscription_to_SearchTransport());

        //from Create Transport
        mapper.put(Media.SN_CREATE_TRANSPORT_STATE + CONNECTION_TO + Media.SN_CREATE_TRANSPORT_STATE,
                new CreateTransport_to_CreateTransport());
        mapper.put(Media.SN_CREATE_TRANSPORT_STATE + CONNECTION_TO + Media.SN_MANAGE_LIFT,
                new CreateTransport_to_ManageLift());

        //from Manage Lift
        mapper.put(Media.SN_MANAGE_LIFT + CONNECTION_TO + Media.SN_MANAGE_LIFT,
                new ManageLift_to_ManageLift());

        //from search tranport
        mapper.put(Media.SN_SEARCH_TRANSPORT_STATE + CONNECTION_TO + Media.SN_SEARCH_TRANSPORT_STATE,
                new SearchTransport_to_SearchTransport());
        mapper.put(Media.SN_SEARCH_TRANSPORT_STATE + CONNECTION_TO + Media.SN_IN_LIFT_STATE,
                new SearchTransport_to_DrinkerInLift());

        //from drinker in lift
        mapper.put(Media.SN_IN_LIFT_STATE + CONNECTION_TO + Media.SN_SEARCH_TRANSPORT_STATE,
                new DrinkerInLift_to_SearchTransport());
        mapper.put(Media.SN_IN_LIFT_STATE + CONNECTION_TO + Media.SN_IN_LIFT_STATE,
                new DrinkerInLift_to_DrinkerInLift());

    }


    //------------the generic command------------------------
    private abstract class Manager{
        abstract Media manage (Media media);
    }

    //------------the concrete commands----------------------
        //---FROM INSCRIPTION
    private class Inscription_to_inscription extends Manager{
        @Override
        Media manage(Media media) {
            return InscriptionHelper.getInscriptionState();
        }
    }

    private class Inscription_to_CreateTransport extends Manager{
        @Override
        Media manage(Media media) {
            return InscriptionHelper.doInscriptionAsDriver(media);
        }
    }

    private class Inscription_to_SearchTransport extends Manager{
        @Override
        Media manage(Media media) {
            media = InscriptionHelper.doInscriptionAsDrinker(media);
            return LiftHelper.refreshLiftList(media);
        }
    }

    //---FROM CREATE TRANSPORT
    private class CreateTransport_to_CreateTransport extends Manager{
        @Override
        Media manage(Media media) {return null; }
    }
    private class CreateTransport_to_ManageLift extends Manager{
        @Override
        Media manage(Media media) {return LiftHelper.liftInscription(media); }
    }
    //---FROM MANAGE lIFT
    private class ManageLift_to_ManageLift extends Manager{
        @Override
        Media manage(Media media) {return null; }
    }

    //---FROM SEARCH TRANSPORT
    private class SearchTransport_to_SearchTransport extends Manager{
        @Override
        Media manage(Media media) {return LiftHelper.refreshLiftList(media); }
    }
    private class SearchTransport_to_DrinkerInLift extends Manager{
        @Override
        Media manage(Media media) {return null; }
    }

    //---FROM DRINKER IN LIFT
    private class DrinkerInLift_to_SearchTransport extends Manager{
        @Override
        Media manage(Media media) {return null; }
    }
    private class DrinkerInLift_to_DrinkerInLift extends Manager{
        @Override
        Media manage(Media media) {return null; }
    }

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


}
