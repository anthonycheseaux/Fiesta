package hevs.ch.fiesta.views;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;
import com.google.api.client.util.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hevs.ch.fiesta.R;
import hevs.ch.fiesta.states.InscriptionState;
import hevs.ch.fiesta.states.MediaAdapter;

public final class ChooseEventAct extends HypermediaBrowser {
    private InscriptionState state;
    private ArrayAdapter adapter;
    private ListView list;
    private Button advantage;
    private Button apropos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_event);

        state=(InscriptionState) MediaAdapter.adapt(stateStack.getUpdateMedia());
        list = (ListView) findViewById(R.id.choose_event_listView);
        if(state.getEventList() == null || state.getEventList().size()==0)
            adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,new String[]{"il n'y a pas d'évenement\npour l'instant"});
        else {
            adapter = new EventsAdapter(this, state.getEventList());
            list.setOnItemClickListener(new Listner());
        }

        list.setAdapter(adapter);
        apropos = (Button) findViewById(R.id.button_apropos);
        apropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_apropos);
            }
        });

        advantage = (Button) findViewById(R.id.button_avantages);
        advantage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_advantage);
            }
        });



    }


    private class Listner implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            state.selectEvent(i);
            state.validateData();
            changeShowedMedia();
        }

    }
    private class EventsAdapter extends ArrayAdapter<EventEntity>{
        private SimpleDateFormat formater = new SimpleDateFormat("MM/dd");

        public EventsAdapter(Context context, List<EventEntity> Events) {
            super(context, 0, Events);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            EventEntity eventEntity = getItem(position);
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_in_list, parent, false);

            DateTime dtEventStart = eventEntity.getBeginning();
            DateTime dtEventEnd = eventEntity.getEnd();

            Date start;
            Date end;

            if(dtEventStart == null)
                start=new Date();
            else
                start= new Date(dtEventStart.getValue());
            if(dtEventEnd == null)
                end = start;
            else
                end = new Date(dtEventEnd.getValue());

            ((TextView) convertView.findViewById(R.id.event_in_list_name)).setText(eventEntity.getName());
            ((TextView) convertView.findViewById(R.id.event_in_list_start_content)).setText(formater.format(start));
            ((TextView) convertView.findViewById(R.id.event_in_list_end_content)).setText(formater.format(end));

            return convertView;
        }


    }

}
