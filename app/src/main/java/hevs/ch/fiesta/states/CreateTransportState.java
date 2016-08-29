package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.LiftEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;

import java.util.List;

/**
 * Created by Arnaud on 18.08.2016.
 */
public class CreateTransportState extends MediaAdapter {

    @Override
    public Class<?> getNeededActivity() {
        return null;
    }

    //-+-+-+-+-+-+ Constructor -+-+-+-+-+-+-+-+-
    public CreateTransportState(Media media) {
        super(media);
    }

    @Override
    public void validateData() {

    }

    //-+-+-+-+-+-+ getters -+-+-+-+-+-+-+-+-


    //-+-+-+-+-+-+ setters -+-+-+-+-+-+-+-+-
   public void setDestination(String destination){
       checkLift();
       adapted.getLift().setDestination(destination);
   }
    public void setCapacity(int capacity){
        checkLift();
        adapted.getLift().setCapacity(capacity);
    }


    //+-+-+-+-+-+- Actions -+-+-+-+-+-+-+-+
    public void doLisftInscription(){
        adapted.setWantedState(MediaAdapter.SN_IN_LIFT_STATE);
    }

    //+-+-+-+-+-+- Internal -+-+-+-+-+-+-+-+
    private void checkLift(){
        if (adapted.getLift()==null)
            adapted.setLift(new LiftEntity());
    }


}
