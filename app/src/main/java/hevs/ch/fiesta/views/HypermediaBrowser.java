package hevs.ch.fiesta.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import hevs.ch.fiesta.media.AsyncRestoration;
import hevs.ch.fiesta.media.MediaDisplayer;
import hevs.ch.fiesta.media.MediaManager;
import hevs.ch.fiesta.media.MediaStack;
import hevs.ch.fiesta.media.ByOneObservable;


public class HypermediaBrowser extends AppCompatActivity implements MediaDisplayer {

    protected static MediaStack stateStack = MediaManager.getInstance();
    protected static ByOneObservable stateUpdater = MediaManager.getInstance();
    protected final static String MEDIA_ID_KEY = "mediaId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        stateUpdater.register(this);



    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void changeShowedMedia() {
        if(stateStack.getUpdateMedia() != null) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Long mediaId =stateStack.getUpdateMedia().getId();
            editor.putLong(MEDIA_ID_KEY,  mediaId);
            editor.commit();
        }
        startActivity(new Intent(this, stateStack.getCurrentState().getNeededActivity()));
    }

}
