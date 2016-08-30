package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.DriverEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;
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
    private DriverEntity castedOwner;
    private EventEntity selectedEvent;


    @Override
    public Class<?> getNeededActivity() {
        return CreateTransportAct.class;

    }

    //-+-+-+-+-+-+ Constructor -+-+-+-+-+-+-+-+-
    public CreateTransportState(Media media) {
        super(media);
        castedOwner = new DriverEntity();
        castedOwner.setId(owner.getId());
        castedOwner.setUserName(owner.getUserName());
        castedOwner.setPhoneNumber(owner.getPhoneNumber());
        castedOwner.setEmail(owner.getEmail());

        selectedEvent = adapted.getSelectedEvent();

        this.lift = new LiftEntity();
        this.lift.setDriver(this.castedOwner);
        this.lift.setEvent(selectedEvent);
    }

    @Override
    public void validateData() {
        adapted.setLift(lift);
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
