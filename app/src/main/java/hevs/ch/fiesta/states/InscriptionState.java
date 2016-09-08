package hevs.ch.fiesta.states;

import android.content.SharedPreferences;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

import hevs.ch.fiesta.views.ChooseEventAct;
import hevs.ch.fiesta.views.InscriptionAct;

/**
 * Created by Arnaud on 18.08.2016.
 */
public final class InscriptionState extends MediaAdapter {

    private List<EventEntity> availableEvents;
    private EventEntity selectedEvent;

    @Override
    public Class<?> getNeededActivity() {
        if(adapted.getSelectedEvent()!=null)
            return InscriptionAct.class;
        else
            return ChooseEventAct.class;
    }

    //-+-+-+-+-+-+ Constructor -+-+-+-+-+-+-+-+-
    public InscriptionState(Media media) {
        super(media);
        availableEvents = adapted.getAvailableEvent();
        if (availableEvents == null)
            availableEvents = new ArrayList<EventEntity>(2);

        selectedEvent = adapted.getSelectedEvent();

        if (owner == null)
            owner = new UserEntity();
    }

    @Override
    public void validateData() {
        adapted.setOwner(owner);
        adapted.setSelectedEvent(selectedEvent);
    }

    //-+-+-+-+-+-+ getters -+-+-+-+-+-+-+-+-
    public List<EventEntity> getEventList(){
        return availableEvents;
    }

//-+-+-+-+-+-+ setters -+-+-+-+-+-+-+-+-
    public void selectEvent(int index){
        selectedEvent = availableEvents.get(index%availableEvents.size());
    }
    public void setUserName(String name){
        owner.setUserName(name);
    }
    public void setUserEmail(String email){
       owner.setEmail(email);
    }
    public void setUserPhone(String phone){
        owner.setPhoneNumber(phone);
    }


//+-+-+-+-+-+- Actions -+-+-+-+-+-+-+-+
    public void doInscriptionAsDriver(){
        adapted.setWantedState(MediaAdapter.SN_CREATE_TRANSPORT_STATE);
    }
    public void doInscriptionAsDrinker(){
        adapted.setWantedState(MediaAdapter.SN_SEARCH_TRANSPORT_STATE);

    }


}
