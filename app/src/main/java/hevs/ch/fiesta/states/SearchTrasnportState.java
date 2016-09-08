package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.LiftEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;

import java.util.ArrayList;
import java.util.List;

import hevs.ch.fiesta.views.SearchTransportAct;

/**
 * Created by Arnaud on 30.08.2016.
 */
public class SearchTrasnportState extends MediaAdapter {
    private List<LiftEntity> lifts;


    //-+-+-+-+-+-+ Constructor -+-+-+-+-+-+-+-+-
    public SearchTrasnportState(Media media) {
        super(media);
        lifts = adapted.getAvailableLifts();
        if (lifts == null)
            lifts = new ArrayList<>(0);
    }

    @Override
    public Class<?> getNeededActivity() {
        return SearchTransportAct.class;
    }

    @Override
    public void validateData() {

    }



    //-+-+-+-+-+-+ getters -+-+-+-+-+-+-+-+-
    public List<LiftEntity> getLifts(){
        return lifts;
    }


    //+-+-+-+-+-+- Actions -+-+-+-+-+-+-+-+
    public void doSearchTrsp(){
        adapted.setWantedState(MediaAdapter.SN_SEARCH_TRANSPORT_STATE);
    }

}
