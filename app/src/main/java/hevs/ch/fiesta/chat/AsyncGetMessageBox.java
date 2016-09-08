package hevs.ch.fiesta.chat;

import android.os.AsyncTask;

import com.example.arnaud.myapplication.backend.chat.messageBoxEntityApi.MessageBoxEntityApi;
import com.example.arnaud.myapplication.backend.chat.messageBoxEntityApi.model.MessageBoxEntity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by darle on 05.09.2016.
 */

public class AsyncGetMessageBox extends AsyncTask<Void, Void, MessageBoxEntity> {
    private final static String BACKEND_URL = "https://projetfiesta-1372.appspot.com/_ah/api/";
    private final static String LOCAL_URL ="http://10.0.2.2:8080/_ah/api/";
    //private final static String LOCAL_URL = "http://localhost:8080/_ah/api/";
    private final static boolean USE_LOCAL = true;

    private MessageBoxEntityApi api;
    private MessageBoxCaller caller;




    protected AsyncGetMessageBox(){}



    public AsyncGetMessageBox(MessageBoxCaller caller) {
        this.caller = caller;

        if (api == null) {
            String rootUrl;
            if (USE_LOCAL)
                rootUrl = LOCAL_URL;
            else
                rootUrl = BACKEND_URL;

            MessageBoxEntityApi.Builder builder = new MessageBoxEntityApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    // if you deploy on the cloud backend, use your app name
                    // such as https://<your-app-id>.appspot.com
                    .setRootUrl(rootUrl)
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
    protected MessageBoxEntity doInBackground(Void... voids) {
        MessageBoxEntity messageBoxEntity = null;
        try {
            messageBoxEntity = api.get(caller.getMessageBoxId()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return messageBoxEntity;
    }

    @Override
    protected void onPostExecute(MessageBoxEntity messageBoxEntity) {
        super.onPostExecute(messageBoxEntity);
        if (messageBoxEntity!= null)
            caller.update(messageBoxEntity);
    }

}
