package hevs.ch.fiesta.views;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.example.arnaud.myapplication.backend.chat.messageBoxEntityApi.model.Message;

import hevs.ch.fiesta.R;
import hevs.ch.fiesta.chat.MessageBoxEntityAdapter;
import hevs.ch.fiesta.media.MediaManager;
import hevs.ch.fiesta.media.MediaStack;

/**
 * Created by Yannick on 02.09.2016.
 */
public class ChatAct extends AppCompatActivity {
    private static final String TAG = "ChatActivity";

    protected ListView listView;
    protected EditText chatText;
    protected Button buttonSend;
    protected Button addInMyLiftBtn;
    protected ArrayAdapter<Message> adapter;

    protected MessageBoxEntityAdapter messageBoxEntityAdapter;

    protected MediaStack mediaStack = MediaManager.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView) findViewById(R.id.msgview);
        chatText = (EditText) findViewById(R.id.msg);
        buttonSend = (Button) findViewById(R.id.send);
        addInMyLiftBtn = (Button) findViewById(R.id.add_in_lift);


        String owner =getIntent().getStringExtra("owner");
        String id = getIntent().getStringExtra("chatId");
        messageBoxEntityAdapter = new MessageBoxEntityAdapter(id,owner);
        //messageBoxEntityAdapter.askUpdate();
        messageBoxEntityAdapter.setContainer(listView);

        adapter =messageBoxEntityAdapter.getAdapter(this);

        listView.setAdapter(adapter);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        /* NON C'EST LE TRUC LE + PèTE COUILLES DE LA TERRE
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    sendChatMessage();
                }
                return false;
            }
        });*/
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                messageBoxEntityAdapter.addMessage(chatText.getText().toString());
                chatText.setText("");
            }
        });

/*
        //to scroll the list view to bottom on data change
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(adapter.getCount() - 1);
            }
        });*/
        messageBoxEntityAdapter.doPerpetualRun();
        messageBoxEntityAdapter.askUpdate();
    }

    @Override
    protected void onPause() {
        messageBoxEntityAdapter.stopPerpetualRun();
        super.onPause();
    }
}
