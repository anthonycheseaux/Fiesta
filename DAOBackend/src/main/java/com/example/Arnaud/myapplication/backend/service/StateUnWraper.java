package com.example.Arnaud.myapplication.backend.service;

/**
 * Created by Arnaud on 12.08.2016.
 */
class StateUnWraper {


    static BackAbstractState unwrap (StateWraper wraper){
        BackAbstractState respons = null;
        switch (wraper.stateType) {
            case StateWraper.SN_INSCRIPTION_STATE:
                respons = new BackInsciptionState();
                respons.unWrap(wraper);
                break;
            case StateWraper.SN_CREATE_TRANSPORT_STATE:
                break;

            case StateWraper.SN_SEARCH_TRANSPORT_STATE:
            default:
        }
        return respons;
    }


}
