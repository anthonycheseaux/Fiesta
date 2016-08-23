package hevs.ch.fiesta.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.Media;

import hevs.ch.fiesta.media.MediaDisplayer;
import hevs.ch.fiesta.media.MediaManager;
import hevs.ch.fiesta.media.MediaStack;
import hevs.ch.fiesta.media.MediaUpdater;
import hevs.ch.fiesta.media.Observable;
import hevs.ch.fiesta.states.MediaAdapter;

public class HypermediaBrowser extends AppCompatActivity implements MediaDisplayer {
    protected static MediaStack stateStack = MediaManager.getInstance();
    protected static Observable stateUpdater = MediaManager.getInstance();

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
        startActivity(new Intent(this, stateStack.getUpdatedState().getNeededActivity()));
    }

}
