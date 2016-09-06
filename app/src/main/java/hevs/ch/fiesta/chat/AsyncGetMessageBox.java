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
    // On a besoin du contexte pour replacer l'AsyncTask
   // private Context context;
    // On récupère l'activité d'appel, au cas où besoin dans le traitement
    //private Activity activity;

    /**
     * Constructeur de l'asyncTask.


    public AsyncGetMessageBox(Activity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /*
         * Cette fonction contiendra le code exécuté au préalable, par exemple:
         *  -Affichage d'une ProgressBar
         *      =rond qui tourne pour indiquer une attente
         *      =Barre de progression
         *  -...
         */
/*    }

    @Override
    protected Boolean doInBackground(Void... arg0) {
        return null;
        /*faire un service qui va voir les mailbox et fait une notification lors de nouveau messages.
Asynktask qui va chercher les données
tous les x temps creation d'une nouvelle asynktask et exécution

tu prends dans ton adapter
Adapter : mb_old tous les x temps tu vas remplacer l'actuelle mb_current
MB_LD (last display)
MB_SYNK elle set
aller dans media manager (meme principe) set state voir si la mailbox que tu as a plus de message que la dernière que
tu as afficher si y a plus c'est qu il y a au moins un nouveau message
quand ca arrive tu mets une alerte

on a un endpoint qui peut recevoir des messages il met dans les mailbox et fait rien
donc il faut le service qui va checker les mailbox afin de pouvoir relever les messages

quand on va appeler le service pour afficher les messages la MB_SYNC devient MB_LD et on va recevoir une
nouvelle SYNC
         */





}
