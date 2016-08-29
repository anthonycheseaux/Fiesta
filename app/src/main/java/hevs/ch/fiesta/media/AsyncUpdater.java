package hevs.ch.fiesta.media;

import android.os.AsyncTask;

import com.example.arnaud.myapplication.backend.service.mediaApi.MediaApi;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by Arnaud on 19.08.2016.
 */
public class AsyncUpdater extends AsyncTask<Void , Void, Media> {
    protected MediaStack stack;
    private static MediaApi api;

    protected AsyncUpdater(){

    }

    public AsyncUpdater(MediaStack stack){
        this.stack= stack;
        if (api== null){
            MediaApi.Builder builder = new MediaApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    // if you deploy on the cloud backend, use your app name
                    // such as https://<your-app-id>.appspot.com
                    .setRootUrl("https://projetfiesta-1372.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            api = builder.build();
        }
    }


    @Override
    protected Media doInBackground(Void... voids) {
        Media question = stack.getUpdateMedia();
        Media anser= null;

        try {
            if (question == null)
                anser = api.get().execute();
            else
                anser = api.update(question).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return anser;
    }

    @Override
    protected void onPostExecute(Media media) {
        super.onPostExecute(media);
        stack.setState(media);
    }
}
