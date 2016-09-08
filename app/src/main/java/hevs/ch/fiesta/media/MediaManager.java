package hevs.ch.fiesta.media;


import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import hevs.ch.fiesta.media.mimicServer.MimicAsyncUpdater;
import hevs.ch.fiesta.states.MediaAdapter;


/**
 * Created by Arnaud on 11.08.2016.
 */
public class MediaManager implements ByOneObservable, MediaStack {
    private final static boolean USE_MIMIC_API = false;

//____________Singleton part_____________________________

    private static MediaManager singleton;

    public static MediaManager getInstance(){
        if (singleton== null)
            singleton= new MediaManager();
        return singleton;
    }
    private MediaManager(){
        setUpForMediaStack();
        setUpForObservable();
        singleton= this;
    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


//_____________media stack part___________________________
    private Stack<Media> states;
    private MediaAdapter currentState;

    private void setUpForMediaStack(){
        states = new Stack<Media>();

    }
    @Override
    public void setState (Media media){
        if (media!= null) {
            states.push(media);
            currentState= MediaAdapter.adapt(media);
        }
        this.notifyStateChange();


    }
    @Override
    public Media getUpdateMedia(){
        Media respons = null;
        try {
            respons = states.peek();
        }catch ( EmptyStackException e){

        }
        return respons;
    }

    @Override
    public MediaAdapter getCurrentState() {
        return currentState;
    }

    @Override
    public void askUpdate() {
        AsyncUpdater asyncUpdater;
        if (USE_MIMIC_API)
            asyncUpdater= new MimicAsyncUpdater(this);
        else
            asyncUpdater = new AsyncUpdater(this);
        asyncUpdater.execute();
    }


    public void undoLastChange(){
        if (states.size()>1){
            states.pop();

        }
        notifyStateChange();

    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//____________Observable part_____________________________
    private MediaDisplayer registredDisplayer;

    private void setUpForObservable(){registredDisplayer = null;}

    @Override
    public void register(MediaDisplayer mediaDisplayer) {registredDisplayer= mediaDisplayer;}

    @Override
    public void unRegister(MediaDisplayer mediaDisplayer) {registredDisplayer= null;}

    @Override
    public void notifyStateChange() {
        if (registredDisplayer!= null)
            registredDisplayer.changeShowedMedia();
    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
