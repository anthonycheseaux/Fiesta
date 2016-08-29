package hevs.ch.fiesta.views;


import hevs.ch.fiesta.states.InscriptionState;
import hevs.ch.fiesta.states.MediaAdapter;
import hevs.ch.fiesta.R;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.arnaud.myapplication.backend.service.mediaApi.model.EventEntity;

public final class ChooseEventActivity extends HypermediaBrowser {
    private InscriptionState state;
    private ArrayAdapter<EventEntity> adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_event);

        state=(InscriptionState) MediaAdapter.adapt(stateStack.getUpdateMedia());
        adapter = new ArrayAdapter<EventEntity>(this,R.layout.event_in_list,state.getEventList());
        list = (ListView) findViewById(R.id.choose_event_listView);

        list.setAdapter(adapter);

    }


    private class Listner implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            state.selectEvent(i);
            state.validateData();
            changeShowedMedia();
        }

    }
}
