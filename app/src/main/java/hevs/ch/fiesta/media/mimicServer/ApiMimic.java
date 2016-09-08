package hevs.ch.fiesta.media.mimicServer;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;
import com.google.api.client.util.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hevs.ch.fiesta.states.MediaAdapter;

/**
 * Created by Arnaud on 26.08.2016.
 */
public class ApiMimic {
    private Storage storage;

//____________Singleton part_____________________________

    private static ApiMimic singleton;

    public static ApiMimic getInstance(){
        if (singleton== null)
            singleton= new ApiMimic();
        return singleton;
    }
    private ApiMimic(){
        this.storage = new Storage();
        singleton= this;
    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~




    Media get(){
        return storage.getInscription();
        }
    Media update(Media question){
        if (question.getWantedState()== null)
            question.setWantedState(question.getStateType());
        switch (question.getWantedState()){
            case MediaAdapter.SN_INSCRIPTION_STATE:
                return storage.getInscription();
            case MediaAdapter.SN_CREATE_TRANSPORT_STATE:
                return storage.getCreateTrst(question);
            case MediaAdapter.SN_MANAGE_LIFT:
                return storage.getManageTrsp(question);
            case MediaAdapter.SN_SEARCH_TRANSPORT_STATE:
                return storage.getSearchTrsp(question);
            case MediaAdapter.SN_IN_LIFT_STATE:
                return storage.getDrinkerInTrsp(question);
            default:
                return storage.getInscription();
        }
    }

    private class Storage {

        List<EventEntity> eventList;
        List<UserEntity> userList;


        private Storage(){
            eventList = new ArrayList<EventEntity>(3);
            Calendar cld = Calendar.getInstance();
            cld.set(Calendar.YEAR, 2016);

            EventEntity evnt;

            evnt = new EventEntity();
            evnt.setId(new Long((long)(Math.random()*Long.MAX_VALUE)));
            evnt.setName("event 1");
            cld.set(Calendar.DAY_OF_YEAR,300);
            evnt.setBeginning(new DateTime(cld.getTime()));
            cld.set(Calendar.DAY_OF_YEAR,303);
            evnt.setEnd(new DateTime(cld.getTime()));
            eventList.add(evnt);

            evnt = new EventEntity();
            evnt.setId(new Long((long)(Math.random()*Long.MAX_VALUE)));
            evnt.setName("event 2");
            cld.set(Calendar.DAY_OF_YEAR,330);
            evnt.setBeginning(new DateTime(cld.getTime()));
            cld.set(Calendar.DAY_OF_YEAR,335);
            evnt.setEnd(new DateTime(cld.getTime()));
            eventList.add(evnt);

            evnt = new EventEntity();
            evnt.setId(new Long((long)(Math.random()*Long.MAX_VALUE)));
            evnt.setName("event 3");
            cld.set(Calendar.DAY_OF_YEAR,333);
            evnt.setBeginning(new DateTime(cld.getTime()));
            cld.set(Calendar.DAY_OF_YEAR,336);
            evnt.setEnd(new DateTime(cld.getTime()));
            eventList.add(evnt);


            userList= new ArrayList<>(3);
            UserEntity user;

            user=new UserEntity();
            user.setUserName("jean");
            user.setEmail("jean@none.ch");
            user.setPhoneNumber("079 635 89 74");
            userList.add(user);

            user=new UserEntity();
            user.setUserName("blapi");
            user.setEmail("blapi@none.ch");
            user.setPhoneNumber("078 472 89 74");
            userList.add(user);

            user=new UserEntity();
            user.setUserName("toto");
            user.setEmail("toto@none.ch");
            user.setPhoneNumber("076 589 89 74");
            userList.add(user);

        }

        private Media getInscription(){
            Media respons = new Media();

            //-+-+-+-+-+-+- set state type -+-+-+-+-+-+-
            respons.setStateType(MediaAdapter.SN_INSCRIPTION_STATE);


            //-+-+-+-+-+-+- set next step -+-+-+-+-+-+-
            respons.setAvailableStates(new ArrayList<String>());
            respons.getAvailableStates().add(MediaAdapter.SN_CREATE_TRANSPORT_STATE);
            respons.getAvailableStates().add(MediaAdapter.SN_SEARCH_TRANSPORT_STATE);

            //-+-+-+-+-+-+- set needed data -+-+-+-+-+-+-
            respons.setAvailableEvent(storage.eventList);

            return respons;
        }
        private Media getCreateTrst(Media question){
            Media respons = new Media();

            //-+-+-+-+-+-+- set state type -+-+-+-+-+-+-
            respons.setStateType(MediaAdapter.SN_CREATE_TRANSPORT_STATE);


            //-+-+-+-+-+-+- set next step -+-+-+-+-+-+-
            respons.setAvailableStates(new ArrayList<String>());
            respons.getAvailableStates().add(MediaAdapter.SN_MANAGE_LIFT);

            //-+-+-+-+-+-+- set needed data -+-+-+-+-+-+-
            if (question.getOwner()!= null)
                respons.setOwner(question.getOwner());
            else
                respons.setOwner(
                        userList.get((int)Math.random()*userList.size())
                );
            if (question.getSelectedEvent()!=  null)
                respons.setSelectedEvent(question.getSelectedEvent());
            else
                respons.setSelectedEvent(
                        eventList.get((int)Math.random()*eventList.size())
                );
            return respons;
        }
        private Media getManageTrsp(Media question){
            return null;
        }
        private Media getSearchTrsp(Media question){
            return null;
        }
        private Media getDrinkerInTrsp(Media question){
            return null;
        }

    }
}
