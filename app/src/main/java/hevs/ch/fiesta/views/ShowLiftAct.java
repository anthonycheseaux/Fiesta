package hevs.ch.fiesta.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.UserEntity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hevs.ch.fiesta.R;
import hevs.ch.fiesta.states.ManageLiftState;
import hevs.ch.fiesta.states.MediaAdapter;
import hevs.ch.fiesta.states.ShowLiftState;

/**
 * Created by Arnaud on 07.09.2016.
 */
public class ShowLiftAct extends HypermediaBrowser {
    protected ShowLiftState state;
    private ArrayAdapter adapter;



    protected TextView destination;
    protected TextView drinverName;
    protected TextView capacity;
    protected TextView departure;

    protected ListView list;

    protected Button refreshBtn;
    protected Button goToChatBtn;


    private SimpleDateFormat timeFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lift);


        drinverName = (TextView) findViewById(R.id.show_lift_driver_name);
        destination = (TextView) findViewById(R.id.show_lift_destination);
        capacity = (TextView) findViewById(R.id.show_lift_Text_capacity);
        departure = (TextView) findViewById(R.id.show_lift_Text_heureDepart);

        list = (ListView) findViewById(R.id.show_lift_passagers_listView);

        refreshBtn = (Button) findViewById(R.id.show_lift_btn_refresh);
        goToChatBtn = (Button) findViewById(R.id.show_lift_btn_chat);


        timeFormat= new SimpleDateFormat("HH:mm");//TODO localiser la préférence de l'afichage (24 heure | am/pm)
    }

    @Override
    protected void onResume() {
        super.onResume();
        state=(ManageLiftState) MediaAdapter.adapt(stateStack.getUpdateMedia());

        drinverName.setText(state.getDriversName());
        destination.setText(state.getDestination());

        refreshCapcityField();

        refreshTimeField();


        if(state.getPassengers() == null || state.getPassengers().size()==0)
            adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,new String[]{getString(R.string.no_passenger)});
        else
            adapter = new DrinkersAdapter(this, state.getPassengers());

        list.setAdapter(adapter);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state.validateData();
                startActivity(new Intent(ShowLiftAct.this, LoadingScreenAct.class));
            }
        });
        goToChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowLiftAct.this, ChatListAct.class));
            }
        });


        list = (ListView) findViewById(R.id.show_lift_passagers_listView);

        refreshBtn = (Button) findViewById(R.id.show_lift_btn_refresh);
        goToChatBtn = (Button) findViewById(R.id.show_lift_btn_chat);



    }
    protected void refreshTimeField(){
        long difference = (state.getDeparture().getTime())- (new Date().getTime());
        String txt = timeFormat.format(state.getDeparture());


        Date differceDate;
        if(difference>0){
            txt = txt + ", soit dans ";
        }
        else{
            difference = 0- difference;
            txt = txt + ", soit il y a  ";
        }
        differceDate = new Date(difference);
        txt = txt + timeFormat.format(differceDate);
        departure.setText(txt);

    }
    protected  void refreshCapcityField(){
        int fullCapacity = state.getCapacity();
        int usedCapacity = state.getPassengers().size();
        capacity.setText(usedCapacity+"/"+fullCapacity+" - "+(fullCapacity-usedCapacity)+" places libre(s)");
    }

    private class DrinkersAdapter extends ArrayAdapter<UserEntity>{

        public DrinkersAdapter(Context context, List<UserEntity> drinkers) {
            super(context, 0, drinkers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            UserEntity drinker = getItem(position);
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.drinker_in_list, parent, false);


            ((TextView) convertView.findViewById(R.id.drinker_in_list_name)).setText(drinker.getUserName());
            ((TextView) convertView.findViewById(R.id.drinker_in_list_mail)).setText(drinker.getEmail());

            return convertView;
        }

    }
}

