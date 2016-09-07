package hevs.ch.fiesta.media;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;

import hevs.ch.fiesta.states.MediaAdapter;

/**
 * Created by Arnaud on 19.08.2016.
 */
public interface MediaStack {
    void setState (Media media);
    Media getUpdateMedia();
    MediaAdapter getCurrentState();
    void askUpdate();
    void undoLastChange();
}
