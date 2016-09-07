package hevs.ch.fiesta.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.arnaud.myapplication.backend.chat.messageBoxEntityApi.model.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import hevs.ch.fiesta.R;
import hevs.ch.fiesta.chat.MessageBoxEntityAdapter;
import hevs.ch.fiesta.media.MediaManager;
import hevs.ch.fiesta.media.MediaStack;

/**
 * Created by Anthony on 06/09/2016.
 */
public class ChatListAct extends HypermediaBrowser implements AdapterView.OnItemClickListener{
    private static final String TAG = "ChatActivity";

    private MessageBoxEntityAdapter messageBoxEntityAdapter;
    private ListView listView;

    private MediaStack mediaStack = MediaManager.getInstance();

    private List<String> interlocutors;
    private List<String> mbeIds;

    private List<MessageBoxEntityAdapter> messageBoxEntityAdapters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_list);

        listView = (ListView) findViewById(R.id.chatList);

        listView.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, ChatAct.class);
        intent.putExtra("chatId", messageBoxEntityAdapters.get(i).getMessageBoxId());
        intent.putExtra("owner", messageBoxEntityAdapters.get(i).getOwner());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mbeIds = mediaStack.getUpdateMedia().getOwner().getMyMails();

        if(mbeIds == null)
            mbeIds = new ArrayList<>();

        messageBoxEntityAdapters = new ArrayList<>(mbeIds.size());
        interlocutors = new ArrayList<>(mbeIds.size());

        for (Iterator<String> iterator = mbeIds.iterator(); iterator.hasNext();) {
            MessageBoxEntityAdapter tmp =new MessageBoxEntityAdapter(iterator.next(), mediaStack.getUpdateMedia().getOwner().getEmail());
            messageBoxEntityAdapters.add(tmp);
            interlocutors.add(tmp.getInterlocutor());
        }


        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, interlocutors));
    }
}
