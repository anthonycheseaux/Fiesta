package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;

import java.util.List;

/**
 * Created by Arnaud on 18.08.2016.
 */
public class ManageLiftState extends MediaAdapter {
    @Override
    public Class<?> getNeededActivity() {
        return null;
    }

    //-+-+-+-+-+-+ Constructor -+-+-+-+-+-+-+-+-
    public ManageLiftState(Media media) {
        super(media);
    }


    //-+-+-+-+-+-+ getters -+-+-+-+-+-+-+-+-


    //-+-+-+-+-+-+ setters -+-+-+-+-+-+-+-+-



    //+-+-+-+-+-+- Actions -+-+-+-+-+-+-+-+


    //+-+-+-+-+-+- Internal -+-+-+-+-+-+-+-+

}
