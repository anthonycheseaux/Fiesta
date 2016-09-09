package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.LiftEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hevs.ch.fiesta.views.ShowLiftAct;

/**
 * Created by Arnaud on 07.09.2016.
 */
public class ShowLiftState extends MediaAdapter{
    protected LiftEntity liftEntity;
    protected List<UserEntity> drinkers;

    @Override
    public Class<?> getNeededActivity() {
        return ShowLiftAct.class;
    }

    //-+-+-+-+-+-+ Constructor -+-+-+-+-+-+-+-+-
    public ShowLiftState(Media media) {
        super(media);
        this.liftEntity = media.getLift();
        drinkers = liftEntity.getDrinkers();
        if (drinkers == null)
            drinkers = new ArrayList<>();
    }



    //-+-+-+-+-+-+ getters -+-+-+-+-+-+-+-+-
    public List<UserEntity> getPassengers(){
        return drinkers;
    }
    public String getDestination(){
        return liftEntity.getDestination();
    }
    public String getDriversName(){
        return liftEntity.getDriver().getUserName();
    }
    public int getCapacity(){return liftEntity.getCapacity();}
    public Date getDeparture(){return new Date(liftEntity.getDeparture().getValue());}

    public void unRegistration(){
        adapted.setWantedState(adapted.getAvailableStates().get(1));
    }


    @Override
    public void validateData() {
        adapted.setWantedState(adapted.getAvailableStates().get(0));
    }
}
