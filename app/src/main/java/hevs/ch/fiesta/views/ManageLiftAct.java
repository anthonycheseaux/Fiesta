package hevs.ch.fiesta.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;

import java.util.Calendar;
import java.util.Date;

import hevs.ch.fiesta.R;
import hevs.ch.fiesta.states.ManageLiftState;

/**
 * Created by Arnaud on 07.09.2016.
 */
public class ManageLiftAct extends ShowLiftAct implements AdapterView.OnItemClickListener, NumberPicker.OnValueChangeListener{

    private Calendar liftStartCalendar;
    private Calendar referenceCal;

    private Button unregistreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.setOnItemClickListener(this);

        unregistreButton = (Button) findViewById(R.id.show_lift_btn_unsubcribe);
        unregistreButton.setVisibility(View.VISIBLE);
        unregistreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ManageLiftState)state).unRegistration();
                startActivity(new Intent(ManageLiftAct.this, LoadingScreenAct.class));
            }
        });


        liftStartCalendar=Calendar.getInstance();
        referenceCal=Calendar.getInstance();
        referenceCal.setTime(new Date());

        departure.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(View v) {
                DialogFragment newFragment = new TimeDialog();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });
        capacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(ManageLiftAct.this);
                d.setTitle("NumberPicker");
                d.setContentView(R.layout.dialogue_number_piker);
                Button b1 = (Button) d.findViewById(R.id.dialogue_number_piker_button1);
                Button b2 = (Button) d.findViewById(R.id.dialogue_number_piker_button2);
                final NumberPicker np = (NumberPicker) d.findViewById(R.id.dialogue_number_piker_numberPicker1);
                np.setMaxValue(15);
                np.setMinValue(0);
                np.setValue(state.getCapacity());
                np.setWrapSelectorWheel(false);
                np.setOnValueChangedListener(ManageLiftAct.this);
                b1.setOnClickListener(new View.OnClickListener()
                {
                        @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                b2.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                d.setCancelable(false);
                d.show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        liftStartCalendar.setTime(state.getDeparture());
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
        dialog.setCancelable(false);
        dialog.show();
        }


    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        ((ManageLiftState) state).setCapacity(i1);
        refreshCapcityField();
    }

    private class Remover implements DialogInterface.OnClickListener{
        int index;
        Remover(int index){
            this.index = index;
        }
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            ((ManageLiftState)state).deletePseenger(index);
            state.validateData();
            startActivity(new Intent(ManageLiftAct.this, LoadingScreenAct.class));

        }
    }
    //function for timepicker

    @SuppressLint({"NewApi", "ValidFragment"})
    public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


        private TimeDialog(){
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int hour = -1;
            int min= -1;

            //change time end/start

            hour = liftStartCalendar.get(Calendar.HOUR_OF_DAY);
            min = liftStartCalendar.get(Calendar.MINUTE);

            TimePickerDialog dpd = new TimePickerDialog(ManageLiftAct.this, this, hour, min, true);
            return dpd;
        }

        @Override
        public void onTimeSet(TimePicker view, int hour, int min) {

            referenceCal.setTime(new Date());

            liftStartCalendar.set(Calendar.HOUR_OF_DAY, hour);
            liftStartCalendar.set(Calendar.MINUTE, min);

            liftStartCalendar.set(Calendar.DAY_OF_YEAR, referenceCal.get(Calendar.DAY_OF_YEAR));

            if(liftStartCalendar.getTime().getTime() <(referenceCal.getTime().getTime()))
                liftStartCalendar.set(Calendar.DAY_OF_YEAR, referenceCal.get(Calendar.DAY_OF_YEAR)+1);

            ((ManageLiftState)state).setDeparture(liftStartCalendar.getTime());
            refreshTimeField();
        }
    }


}
