package hevs.ch.fiesta.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.arnaud.myapplication.backend.chat.messageBoxEntityApi.model.Message;

import java.util.List;

import hevs.ch.fiesta.R;

/**
 * Created by Yannick on 02.09.2016.
 */
@Deprecated
public class ChatArrayAdapter extends ArrayAdapter<Message> {

    //private TextView chatText;
    //private List<Message> chatMessageList;
    private String owner;
/*
    @Override
    public void add(Message object) {
        chatMessageList.add(object);
    }
*/
    public ChatArrayAdapter(Context context, List<Message> messageList, String owner) {
        super(context, 0, messageList);
        this.owner = owner;
    }
/*
    public ChatArrayAdapter(Context context, int textViewResourceId, String owner) {
        super(context, textViewResourceId);
        this.owner = owner;
    }
    */
/*
    @Override
    public int getCount() {
        return this.chatMessageList.size();
    }

    @Override
    public Message getItem(int index) {
        return this.chatMessageList.get(index);
    }
*/
    public View getView(int position, View convertView, ViewGroup parent) {
        Message msg = getItem(position);

        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (msg.getSender().equals(owner)) {
            row = inflater.inflate(R.layout.message_in_list_right, parent, false);
        }else{
            row = inflater.inflate(R.layout.message_in_list_left, parent, false);
        }
        /*
        chatText = (TextView) row.findViewById(R.id.msgr);
        chatText.setText(chatMessageObj.message);
*/
    ((TextView) row.findViewById(R.id.msgr)).setText(msg.getText());

        return row;
    }
}
