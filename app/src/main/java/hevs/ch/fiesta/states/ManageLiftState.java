package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.LiftEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;
import com.google.api.client.util.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hevs.ch.fiesta.views.ManageLiftAct;

/**
 * Created by Arnaud on 18.08.2016.
 */
public class ManageLiftState extends ShowLiftState {

    @Override
    public Class<?> getNeededActivity() {
        return ManageLiftAct.class;
    }

    //-+-+-+-+-+-+ Constructor -+-+-+-+-+-+-+-+-
    public ManageLiftState(Media media) {
        super(media);
    }



    //-+-+-+-+-+-+ getters -+-+-+-+-+-+-+-+-
        // showLift manage this

    //-+-+-+-+-+-+ setters -+-+-+-+-+-+-+-+-
    public void setCapacity(int capacity){
        liftEntity.setCapacity(capacity);
    }
    public void setDeparture(Date departure){
        liftEntity.setDeparture(new DateTime(departure));
    }


    public void deletePseenger(int index){
        drinkers.get(index).setUserName("removed");
    }
    public void addUser(String userMail){
        UserEntity added = new UserEntity();
        added.setEmail(userMail);
        added.setUserName("added");

        drinkers.add(added);
    }


    //+-+-+-+-+-+- Actions -+-+-+-+-+-+-+-+

    @Override
    public void validateData() {
        liftEntity.setDrinkers(drinkers);
        adapted.setLift(liftEntity);
        adapted.setWantedState(adapted.getAvailableStates().get(0));
    }
    @Override
    public void unRegistration(){
        liftEntity.setDrinkers(drinkers);
        adapted.setLift(liftEntity);
        adapted.setWantedState(adapted.getAvailableStates().get(1));
    }

    //+-+-+-+-+-+- Internal -+-+-+-+-+-+-+-+

}
