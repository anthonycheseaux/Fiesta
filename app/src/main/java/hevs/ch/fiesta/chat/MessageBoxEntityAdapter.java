package hevs.ch.fiesta.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.arnaud.myapplication.backend.chat.messageBoxEntityApi.model.Message;
import com.example.arnaud.myapplication.backend.chat.messageBoxEntityApi.model.MessageBoxEntity;
import com.google.api.client.util.DateTime;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import hevs.ch.fiesta.R;

/**
 * Created by Anthony on 06/09/2016.
 */
public class MessageBoxEntityAdapter implements MessageBoxCaller {
    private String owner;
    private String interlocutor;
    private String messageBoxId;
    private List<Message> displayed;
    private AsyncGetMessageBox updater;


    public MessageBoxEntityAdapter(String messageBoxId, String owner) {
        this.owner = owner;
        this.messageBoxId = messageBoxId;
        if(messageBoxId.startsWith(owner)) {
            this.interlocutor = messageBoxId.substring(owner.length());
        }
        else {
            this.interlocutor = messageBoxId.substring(0, messageBoxId.length()-owner.length());
        }
        displayed = new LinkedList<>();
        askUpdate();
    }
    public void askUpdate(){
        AsyncGetMessageBox updater = new AsyncGetMessageBox(this);
        updater.execute();

    }
    @Override
    public void update(MessageBoxEntity messageBoxEntity) {
        List<Message> addedMessages = messageBoxEntity.getContent();
        addedMessages.remove(displayed);
        for (Iterator<Message> iterator = addedMessages.iterator(); iterator.hasNext();) {
            displayed.add(iterator.next());
        }
    }

    @Override
    public String getMessageBoxId() {
        return messageBoxId;
    }

    public void addMessage(String text){
        Message message = new Message();
        message.setSender(owner);
        message.setReceiver(interlocutor);
        message.setText(text);
        message.setDateMessage(new DateTime(new Date()));

        new AsyncSendMessage(message).execute();

        displayed.add(message);
    }

    public ArrayAdapter<Message> getAdapter(Context context){
        return new ChatArrayAdapter(context, displayed, owner);
    }

    public String getInterlocutor() {
        return this.interlocutor;
    }

    public String getOwner() {
        return owner;
    }

    private class ChatArrayAdapter extends ArrayAdapter<Message> {

        private String owner;

        private ChatArrayAdapter(Context context, List<Message> messageList, String owner) {
            super(context, 0, messageList);
            this.owner = owner;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Message msg = getItem(position);

            View row = convertView;
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (msg.getSender().equals(owner)) {
                row = inflater.inflate(R.layout.message_in_list_right, parent, false);
            }else{
                row = inflater.inflate(R.layout.message_in_list_left, parent, false);
            }

            ((TextView) row.findViewById(R.id.msgr)).setText(msg.getText());

            return row;
        }
    }
}
