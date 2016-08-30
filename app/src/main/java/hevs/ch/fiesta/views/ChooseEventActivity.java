package hevs.ch.fiesta.views;


import hevs.ch.fiesta.BuildConfig;
import hevs.ch.fiesta.states.InscriptionState;
import hevs.ch.fiesta.states.MediaAdapter;
import hevs.ch.fiesta.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class ChooseEventActivity extends HypermediaBrowser {
    private InscriptionState state;
    private EventsAdapter adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_event);

        state=(InscriptionState) MediaAdapter.adapt(stateStack.getUpdateMedia());
        adapter = new EventsAdapter(this, state.getEventList());
        list = (ListView) findViewById(R.id.choose_event_listView);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new Listner());
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
        private SimpleDateFormat formater = new SimpleDateFormat("dd/MM");

        public EventsAdapter(Context context, List<EventEntity> Events) {
            super(context, 0, Events);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            EventEntity eventEntity = getItem(position);
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_in_list, parent, false);

            ((TextView) convertView.findViewById(R.id.event_in_list_name)).setText(eventEntity.getName());
            ((TextView) convertView.findViewById(R.id.event_in_list_start_content)).setText(formater.format(new Date(eventEntity.getBeginning().getValue())));
            ((TextView) convertView.findViewById(R.id.event_in_list_end_content)).setText(formater.format(new Date(eventEntity.getEnd().getValue())));

            return convertView;
        }
    }
}
