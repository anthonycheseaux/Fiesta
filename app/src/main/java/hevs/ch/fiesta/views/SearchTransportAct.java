package hevs.ch.fiesta.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arnaud.myapplication.backend.chat.messageBoxEntityApi.model.Message;
import com.example.arnaud.myapplication.backend.service.mediaApi.model.LiftEntity;
import com.google.api.client.util.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hevs.ch.fiesta.R;
import hevs.ch.fiesta.chat.AsyncSendMessage;
import hevs.ch.fiesta.media.MediaManager;
import hevs.ch.fiesta.states.MediaAdapter;
import hevs.ch.fiesta.states.SearchTrasnportState;

public class SearchTransportAct extends HypermediaBrowser implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {
    private SearchTrasnportState state;
    private ArrayAdapter adapter;
    private ListView list;
    private Button chatButton;
    private Button refreshButton;
    private LiftEntity selectedLift;

    private static boolean doPerpetualRun = false;
    private static int durationBeforeReUpdate = 3*1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_transport);
        list = (ListView) findViewById(R.id.search_transport_lift_list);
        chatButton = (Button) findViewById(R.id.search_transport_chat_button);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchTransportAct.this,ChatListAct.class));
            }
        });
        refreshButton =(Button) findViewById(R.id.search_transport_refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchTransportAct.this,LoadingScreenAct.class));
            }
        });

        list.setOnItemClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        state=(SearchTrasnportState) MediaAdapter.adapt(stateStack.getUpdateMedia());

        if(state.getLifts() == null || state.getLifts().size()==0)
            adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,new String[]{"il n'y a pas de transport disponibles...\n...pour l'instant"});
        else
            adapter = new LiftAdapter(this, state.getLifts());

        list.setAdapter(adapter);

        doPerpetualRun = true;
        new PerpetualUpdater().execute();
    }
    @Override
    protected void onPause() {
        doPerpetualRun = false;
        super.onPause();
    }
    @Override
    public void changeShowedMedia() {
        if(stateStack.getCurrentState().getClass().equals(state.getClass()))
            onResume();
        else super.changeShowedMedia();

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String title = "S'inscrire ";
        String message = "Manifester son interet pour ce trasnport?";
        this.selectedLift = state.getLifts().get(i);

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message)
                .setTitle(title);
        builder.setPositiveButton("oui", this);
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


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Message msg = new Message();
        msg.setSender(state.getOwner().getEmail());
        msg.setReceiver(selectedLift.getDriver().getEmail());
        msg.setText(state.getOwner().getUserName()+getString(R.string.ask_add_lift));
        msg.setDateMessage(new DateTime(new Date()));
        new AsyncSendMessage(msg).execute();
        Toast.makeText(
                getApplicationContext(),
                "Votre Demande a été envoyée, vous pouvez désormais discuter avec son conduceur dans le chat",
                Toast.LENGTH_LONG)
                .show();
        startActivity(new Intent(this, LoadingScreenAct.class));
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
    private class PerpetualUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (doPerpetualRun){
                try {
                    Thread.sleep(durationBeforeReUpdate);
                    if(doPerpetualRun)
                        MediaManager.getInstance().askUpdate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
