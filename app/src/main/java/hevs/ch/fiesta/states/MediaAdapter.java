package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;

/**
 * Created by Arnaud on 18.08.2016.
 *
 * abstract class who represent a generic state
 * each subclasses whould be a concrete state
 * the constructor is generic, the media to adapte in a state
 * we have to use the factory to adapt media to the correct concrete state
 */
public abstract class MediaAdapter {
    public final static String SN_INSCRIPTION_STATE = "inscription_state";
    public final static String SN_CREATE_TRANSPORT_STATE = "create_transport_state";
    public final static String SN_SEARCH_TRANSPORT_STATE = "search_transport_state";
    public final static String SN_IN_LIFT_STATE = "in_lift_sate";
    public final static String SN_MANAGE_LIFT = "manage_lift";

    protected final static StateFactory factory = new StateFactory();


    protected Media adapted;
    protected UserEntity owner;


    public static MediaAdapter adapt(Media media){
        return factory.adapt(media);
    }

    public abstract Class<?> getNeededActivity();

    public MediaAdapter(Media media){
        adapted=media;
        owner= media.getOwner();
    }


    public abstract void validateData();

    public UserEntity getOwner() {
        return owner;
    }
}
