package hevs.ch.fiesta.states;


import com.example.arnaud.myapplication.backend.service.mediaApi.model.LiftEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.google.api.client.util.DateTime;

import java.util.Date;

import hevs.ch.fiesta.views.CreateTransportAct;


/**
 * Created by Arnaud on 18.08.2016.
 */
public class CreateTransportState extends MediaAdapter {
    private LiftEntity lift;



    @Override
    public Class<?> getNeededActivity() {
        return CreateTransportAct.class;

    }

    //-+-+-+-+-+-+ Constructor -+-+-+-+-+-+-+-+-
    public CreateTransportState(Media media) {
        super(media);
        this.lift = adapted .getLift();
    }

    @Override
    public void validateData() {
        adapted.setLiftId(lift.getId());
        adapted.setLiftDeparture(lift.getDeparture());
        adapted.setLiftDestination(lift.getDestination());
        adapted.setLiftCapacity(lift.getCapacity());
        adapted.setLiftOwner(lift.getDriver().getEmail());
        adapted.setLiftEventId(lift.getEvent().getId());

        adapted.setLift(null);
    }

    //-+-+-+-+-+-+ getters -+-+-+-+-+-+-+-+-


    //-+-+-+-+-+-+ setters -+-+-+-+-+-+-+-+-
   public void setDestination(String destination){
        lift.setDestination(destination);
   }
    public void setCapacity(int capacity){
        lift.setCapacity(capacity);
    }
    public void setStart(Date liftStart){lift.setDeparture(new DateTime(liftStart));

    }


    //+-+-+-+-+-+- Actions -+-+-+-+-+-+-+-+
    public void doLisftInscription(){
        adapted.setWantedState(MediaAdapter.SN_IN_LIFT_STATE);
    }




}
