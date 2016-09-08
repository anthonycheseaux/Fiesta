package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;

/**
 * Created by Arnaud on 18.08.2016.
 */
public class DrinkerInLiftState extends MediaAdapter {
    @Override
    public Class<?> getNeededActivity() {
        return null;
    }

    //-+-+-+-+-+-+ Constructor -+-+-+-+-+-+-+-+-
    public DrinkerInLiftState(Media media) {
        super(media);
    }

    @Override
    public void validateData() {

    }

}
