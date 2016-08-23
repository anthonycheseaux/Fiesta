package hevs.ch.fiesta.media;


import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import hevs.ch.fiesta.states.MediaAdapter;


/**
 * Created by Arnaud on 11.08.2016.
 */
public class MediaManager implements Observable, MediaStack {

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

    private void setUpForMediaStack(){states = new Stack<Media>();}
    public void setState (Media media){
        if (media!= null){
            states.push(media);
            this.notifyStateChange();
        }

    }
    public Media getUpdateMedia(){return states.peek();}

    @Override
    public MediaAdapter getUpdatedState() {
        return MediaAdapter.adapt(states.peek());
    }

    public void undoLastChange(){
        if (states.size()>1){
            states.pop();
            notifyStateChange();
        }


    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//____________Observable part_____________________________
    private List<MediaDisplayer> registredDisplayer;

    private void setUpForObservable(){registredDisplayer = new ArrayList<MediaDisplayer>();}

    @Override
    public void register(MediaDisplayer mediaDisplayer) {registredDisplayer.add(mediaDisplayer);}

    @Override
    public void unRegister(MediaDisplayer mediaDisplayer) {registredDisplayer.remove(mediaDisplayer);}

    @Override
    public void notifyStateChange() {
        for (Iterator<MediaDisplayer> iterator = registredDisplayer.iterator() ; iterator.hasNext(); )
            iterator.next().notify();
    }
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
