package hevs.ch.fiesta.media.mimicServer;

import android.os.AsyncTask;

import com.example.arnaud.myapplication.backend.service.mediaApi.MediaApi;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import hevs.ch.fiesta.media.AsyncUpdater;
import hevs.ch.fiesta.media.MediaStack;

/**
 * Created by Arnaud on 19.08.2016.
 */
public class MimicAsyncUpdater extends AsyncUpdater {

    private static ApiMimic api;

    public MimicAsyncUpdater(MediaStack stack) {
        super();
        this.stack= stack;
        this.api = ApiMimic.getInstance();
    }


    @Override
    protected Media doInBackground(Void... voids) {
        Media question = stack.getUpdateMedia();
        Media anser= null;


        if (question == null)
            anser = api.get();
        else
            anser = api.update(question);


        return anser;
    }

    @Override
    protected void onPostExecute(Media media) {
        super.onPostExecute(media);
        stack.setState(media);
    }

}
