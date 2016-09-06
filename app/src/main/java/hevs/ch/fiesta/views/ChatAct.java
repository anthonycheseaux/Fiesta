package hevs.ch.fiesta.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import hevs.ch.fiesta.R;
import hevs.ch.fiesta.chat.MessageBoxEntityAdapter;
import hevs.ch.fiesta.media.MediaManager;
import hevs.ch.fiesta.media.MediaStack;

/**
 * Created by Yannick on 02.09.2016.
 */
public class ChatAct extends HypermediaBrowser {
    private static final String TAG = "ChatActivity";

    private ListView listView;
    private EditText chatText;
    private Button buttonSend;

    private MessageBoxEntityAdapter messageBoxEntityAdapter;

    private MediaStack mediaStack = MediaManager.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView) findViewById(R.id.msgview);
        chatText = (EditText) findViewById(R.id.msg);
        buttonSend = (Button) findViewById(R.id.send);

        String owner =getIntent().getStringExtra("owner");
        String id = getIntent().getStringExtra("chatId");
        messageBoxEntityAdapter = new MessageBoxEntityAdapter(id,owner);
        messageBoxEntityAdapter.askUpdate();


        listView.setAdapter(messageBoxEntityAdapter.getAdapter(this));
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
            }
        });


        //to scroll the list view to bottom on data change
/*        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });*/
    }
}
