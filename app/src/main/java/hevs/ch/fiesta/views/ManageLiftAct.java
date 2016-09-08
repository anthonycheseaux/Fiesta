package hevs.ch.fiesta.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;

import hevs.ch.fiesta.states.ManageLiftState;

/**
 * Created by Arnaud on 07.09.2016.
 */
public class ManageLiftAct extends ShowLiftAct implements AdapterView.OnItemClickListener{
    protected ManageLiftState state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        UserEntity concerned = state.getPassengers().get(i);
        String title = "supprimer "+concerned.getUserName();
        String message = "ètes vous sure de vouloir supprimer "+concerned.getUserName();

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(title)
                .setTitle(title);
        builder.setPositiveButton("oui", new Remover(i));
        builder.setNegativeButton("non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });


// 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        }
    private class Remover implements DialogInterface.OnClickListener{
        int index;
        Remover(int index){
            this.index = index;
        }
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            state.deletePseenger(index);
        }
    }

}
