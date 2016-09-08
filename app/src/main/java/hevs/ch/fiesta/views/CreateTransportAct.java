package hevs.ch.fiesta.views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hevs.ch.fiesta.R;
import hevs.ch.fiesta.states.CreateTransportState;
import hevs.ch.fiesta.states.MediaAdapter;

public class CreateTransportAct extends HypermediaBrowser implements View.OnClickListener{
    private CreateTransportState state;

    private EditText destinationTxt;
    private EditText placepikerTxt;
    private TextView liftStartTxt;
    private CheckBox engagementChxBx;
    private Button createTrspBtn;

    private Calendar liftStartCalendar;
    private Calendar referenceCal;
    private SimpleDateFormat timeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_trasnport);

        //instanciation
        state = (CreateTransportState) MediaAdapter.adapt(stateStack.getUpdateMedia());

        destinationTxt = (EditText) findViewById(R.id.create_trsp_EditText_destination);
        placepikerTxt = (EditText) findViewById(R.id.create_trsp_editText_capacity);
        liftStartTxt = (TextView) findViewById(R.id.create_trsp_EditText_heureDepart);
        engagementChxBx = (CheckBox) findViewById(R.id.create_trsp_chxBox_AccepteRegle);
        createTrspBtn = (Button) findViewById(R.id.create_trsp_button_creerTransport);


        liftStartCalendar=Calendar.getInstance();
        liftStartCalendar.setTime(new Date());
        liftStartCalendar.add(Calendar.MINUTE, 15);
        referenceCal=Calendar.getInstance();
        referenceCal.setTime(new Date());
        timeFormat= new SimpleDateFormat("HH:mm");//TODO localiser la préférence de l'afichage (24 heure | am/pm)


        //put listner
        createTrspBtn.setOnClickListener(this);

        //function call timepicker
        liftStartTxt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(View v) {
                DialogFragment newFragment = new TimeDialog();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        refreshTimeField();
    }


    @Override
    public void onClick(View view) {
        //form validation
        String destination= destinationTxt.getText().toString();
        int capacity=0;
        try {
            capacity =Integer.parseInt(placepikerTxt.getText().toString());
        }catch (NumberFormatException e){

        }

        Date liftstart= liftStartCalendar.getTime();
        boolean hasAccepted = engagementChxBx.isChecked();

        String validationException="";
        //// TODO: 30.11.2015 loclaiser
        if(destination.equals(""))
            validationException += "renseigner la destination \n";
        if(capacity < 1)
            validationException += "il faut qu'il y ait au moins 1 place \n";
        if(liftstart.getTime()<new Date().getTime())
            validationException += "le départ n'est pas correctemnt rensigné";
        if(false == hasAccepted)
            validationException += "vous devez vous engager à rester sobre et responsable";
        if(false ==(validationException.equals("")) ){
            Toast.makeText(
                    getApplicationContext(),
                    validationException,
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        // set data in State
        state.setDestination(destination);
        state.setCapacity(capacity);
        state.setStart(liftstart);
        state.doLisftInscription();

        // validate State
        state.validateData();

        //lanch loading screen
        startActivity(new Intent(this, LoadingScreenAct.class));
    }



    private void refreshTimeField(){
        long difference = (liftStartCalendar.getTime().getTime())- (new Date().getTime());
        Date differceDate = new Date(difference);
        String txt = timeFormat.format(liftStartCalendar.getTime());
        txt = txt + ", soit dans ";
        txt = txt + timeFormat.format(differceDate);
        liftStartTxt.setText(txt);

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

            TimePickerDialog dpd = new TimePickerDialog(CreateTransportAct.this, this, hour, min, true);
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

            refreshTimeField();
        }
    }

}
