package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;

import java.util.List;

/**
 * Created by Arnaud on 18.08.2016.
 */
public final class InscriptionState extends MediaAdapter {

    @Override
    public Class<?> getNeededActivity() {
        return null;
    }

    //-+-+-+-+-+-+ Constructor -+-+-+-+-+-+-+-+-
    public InscriptionState(Media media) {
        super(media);
    }

//-+-+-+-+-+-+ getters -+-+-+-+-+-+-+-+-
    public List<EventEntity> getEventList(){
        return adapted.getAvailableEvent();
    }

//-+-+-+-+-+-+ setters -+-+-+-+-+-+-+-+-
    public boolean selectEvent(EventEntity event){
        if (adapted.getAvailableEvent().contains(event)){
            adapted.setSelectedEvent(event);
            return true;
        }
        else
            return false;
    }
    public void setUserName(String name){
        checkOwner();
        adapted.getOwner().setUserName(name);
    }
    public void setUserEmail(String email){
        checkOwner();
        adapted.getOwner().setEmail(email);
    }
    public void setUserPhone(String phone){
        checkOwner();
        adapted.getOwner().setUserName(phone);
    }


//+-+-+-+-+-+- Actions -+-+-+-+-+-+-+-+
    public void doInscriptionAsDriver(){
        adapted.setWantedState(MediaAdapter.SN_CREATE_TRANSPORT_STATE);
    }
    public void doInscriptionAsDrinker(){
        adapted.setWantedState(MediaAdapter.SN_SEARCH_TRANSPORT_STATE);

    }

//+-+-+-+-+-+- Internal -+-+-+-+-+-+-+-+
    private void checkOwner(){
        if(adapted.getOwner() == null)
            adapted.setOwner(new UserEntity());
    }
}
