package hevs.ch.fiesta.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import hevs.ch.fiesta.media.MediaManager;
import hevs.ch.fiesta.states.ManageLiftState;

/**
 * Created by Arnaud on 07.09.2016.
 */
public class AdderChat extends ChatAct implements View.OnClickListener {
    private ManageLiftState state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addInMyLiftBtn.setVisibility(View.VISIBLE);
        addInMyLiftBtn.setOnClickListener(this);
        state =(ManageLiftState) MediaManager.getInstance().getCurrentState();
    }

    @Override
    public void onClick(View view) {
        String title = "Ajouter "+messageBoxEntityAdapter.getInterlocutor();
        String message = "voulez-vous ajouter "+messageBoxEntityAdapter.getInterlocutor()+" à votre transport?";

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(title)
                .setTitle(title);
        builder.setPositiveButton("oui", new Adder(messageBoxEntityAdapter.getInterlocutor()));
        builder.setNegativeButton("non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }
    private class Adder implements DialogInterface.OnClickListener{
        String interlocutor;
        Adder(String interlocutor){
            this.interlocutor = interlocutor;
        }
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            state.addUser(interlocutor);
            state.validateData();
            startActivity(new Intent(AdderChat.this, LoadingScreenAct.class));
        }
    }

}

