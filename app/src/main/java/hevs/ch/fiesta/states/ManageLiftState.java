package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.LiftEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;

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
        this.liftEntity = media.getLift();
    }



    //-+-+-+-+-+-+ getters -+-+-+-+-+-+-+-+-
        // showLift manage this

    //-+-+-+-+-+-+ setters -+-+-+-+-+-+-+-+-
    public void deletePseenger(int index){
        liftEntity.getDrikers().get(index).setUserName("removed");
    }
    public void addUser(String userMail){
        UserEntity added = new UserEntity();
        added.setEmail(userMail);
        added.setUserName("added");
        liftEntity.getDrikers().add(added);
    }


    //+-+-+-+-+-+- Actions -+-+-+-+-+-+-+-+

    @Override
    public void validateData() {
        adapted.setLift(liftEntity);
    }

    //+-+-+-+-+-+- Internal -+-+-+-+-+-+-+-+

}
