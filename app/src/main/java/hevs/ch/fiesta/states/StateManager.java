package hevs.ch.fiesta.states;


import android.os.AsyncTask;



import com.example.arnaud.myapplication.backend.eventEntityApi.model.EventEntity;
import com.example.arnaud.myapplication.backend.userEntityApi.model.UserEntity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


/**
 * Created by Arnaud on 11.08.2016.
 */
public class StateManager {/*
    private AbstractState currentState;

    private static AbstractStateApi api;
    private static StateManager singleton;

    static {
        AbstractStateApi.Builder builder = new AbstractStateApi.Builder(AndroidHttp.newCompatibleTransport(),
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
        StateManager.singleton = new StateManager();


    }


    public static StateManager getInstance(){
        return singleton;
    }

    public void becomeDriver(EventEntity event, UserEntity user){
        /*AbstractState nextPhase = new BecomeDriverlState();
        nextPhase.setId(currentState.getId());
        nextPhase.set = event.getId();
        nextPhase.event_name = event.getName();

        nextPhase.driver_Id = user.getId();
        nextPhase.driver_name = user.getUserName();
        nextPhase.driver_email = user.getEmail();
        nextPhase.driver_phoneNumber = user.getPhoneNumber();

        nextPhase.ownerId = user.getId();

        nextPhase.wantedState = InLiftState.STATE_TYPE;

        currentState = nextPhase;B

    }


    private class StateUpdater extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

                try {
                    if (currentState == null)
                        currentState = api.get().execute();
                    else
                       currentState = api.update(currentState).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return null;
        }
    }

*/

}
