package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;

import hevs.ch.fiesta.views.SearchTransportAct;

/**
 * Created by Arnaud on 30.08.2016.
 */
public class SearchTrasnportState extends MediaAdapter {

    public SearchTrasnportState(Media media) {
        super(media);
    }

    @Override
    public Class<?> getNeededActivity() {
        return SearchTransportAct.class;
    }

    @Override
    public void validateData() {

    }
}
