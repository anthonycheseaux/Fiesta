package com.example.Arnaud.myapplication.backend.triggers;

import com.example.Arnaud.myapplication.backend.service.Media;
import static com.googlecode.objectify.ObjectifyService.ofy;
/**
 * Created by Arnaud on 08.09.2016.
 */
public class MediaSaver extends AbstractTrigger {
    Media media;
    MediaSaver(Media media){
        this.media = media;
    }
    @Override
    protected void performeAction() {
        ofy().save().entity(media);

    }
}
