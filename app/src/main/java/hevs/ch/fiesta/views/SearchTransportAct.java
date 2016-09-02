package hevs.ch.fiesta.views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.LiftEntity;
import com.google.api.client.util.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hevs.ch.fiesta.R;
import hevs.ch.fiesta.states.InscriptionState;
import hevs.ch.fiesta.states.MediaAdapter;
import hevs.ch.fiesta.states.SearchTrasnportState;

public class SearchTransportAct extends HypermediaBrowser {
    private SearchTrasnportState state;
    private ArrayAdapter adapter;
    private ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_transport);
        state=(SearchTrasnportState) MediaAdapter.adapt(stateStack.getUpdateMedia());
        list = (ListView) findViewById(R.id.search_transport_lift_list);
        if(state.getLifts() == null || state.getLifts().size()==0)
            adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,new String[]{"il n'y a pas de conducteurs...\n...pour l'instant"});
        else
            adapter = new LiftAdapter(this, state.getLifts());

        list.setAdapter(adapter);
    }



    private class LiftAdapter extends ArrayAdapter<LiftEntity> {
        private SimpleDateFormat formater = new SimpleDateFormat("HH:mm");//TODO localiser la préférence de l'afichage (24 heure | am/pm);

        public LiftAdapter(Context context, List<LiftEntity> Lifts) {
            super(context, 0, Lifts);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LiftEntity liftEntity = getItem(position);
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.lift_in_list, parent, false);

            DateTime dtDeparture = liftEntity.getDeparture();

            Date departure;
            Date timeToDeparture;

            departure= new Date(dtDeparture.getValue());
            timeToDeparture = new Date(dtDeparture.getValue()-new Date().getTime());

            ((TextView) convertView.findViewById(R.id.lift_in_list_destination)).setText(liftEntity.getDestination());
            ((TextView) convertView.findViewById(R.id.lift_in_list_driver)).setText(liftEntity.getDriver().getUserName());
            ((TextView) convertView.findViewById(R.id.lift_in_list_departure)).setText(formater.format(departure));
            ((TextView) convertView.findViewById(R.id.lift_in_list_time_to_departure)).setText(formater.format(timeToDeparture));

            return convertView;
        }
    }
}
