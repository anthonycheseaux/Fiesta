package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.LiftEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;

import java.util.List;

/**
 * Created by Arnaud on 07.09.2016.
 */
public class ShowLiftState extends MediaAdapter{
    protected LiftEntity liftEntity;

    @Override
    public Class<?> getNeededActivity() {
        return ShowLiftState.class;
    }

    //-+-+-+-+-+-+ Constructor -+-+-+-+-+-+-+-+-
    public ShowLiftState(Media media) {
        super(media);
        this.liftEntity = media.getLift();
    }



    //-+-+-+-+-+-+ getters -+-+-+-+-+-+-+-+-
    public List<UserEntity> getPassengers(){
        return liftEntity.getDrikers();
    }
    public String getDestination(){
        return liftEntity.getDestination();
    }
    public String getDriversName(){
        return liftEntity.getDriver().getUserName();
    }

    @Override
    public void validateData() {
        adapted.setWantedState(adapted.getAvailableStates().get(0));
    }
}
