package hevs.ch.fiesta.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arnaud.myapplication.backend.chat.messageBoxEntityApi.model.Message;
import com.example.arnaud.myapplication.backend.chat.messageBoxEntityApi.model.MessageBoxEntity;
import com.google.api.client.util.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

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
    private ListView container;
    private Message lastAdded;
    private boolean doPerpetualRun;
    private int durationBeforeReUpdate = 1*1000;
    private ArrayAdapter adapter;


    public MessageBoxEntityAdapter(String messageBoxId, String owner) {
        doPerpetualRun = false;
        this.owner = owner;
        this.messageBoxId = messageBoxId;
        if(messageBoxId.startsWith(owner)) {
            this.interlocutor = messageBoxId.substring(owner.length());
        }
        else {
            this.interlocutor = messageBoxId.substring(0, messageBoxId.length()-owner.length());
        }
        displayed = new LinkedList<>();

    }
    public void setContainer(ListView container){
        this.container = container;
    }
    public void stopPerpetualRun(){
        doPerpetualRun = false;
    }
    public void doPerpetualRun(){
        doPerpetualRun = true;
    }

    public void askUpdate(){
        AsyncGetMessageBox updater = new AsyncGetMessageBox(this);
        updater.execute();
        if (doPerpetualRun){
            new  PerpetualUpdater().execute();
        }
    }
    @Override
    public void update(MessageBoxEntity messageBoxEntity) {
        List<Message> addedMessages = messageBoxEntity.getContent();
        Message[] current = new Message[addedMessages.size()];
        current = addedMessages.toArray(current);
        int lAI=current.length -1 ; // last added index
        if (lastAdded != null){

            while (lAI>=0 && false == (current[lAI].getDateMessage().getValue()== lastAdded.getDateMessage().getValue()) )
                --lAI;
        }
        else{
            lAI = -1;
        }

        for (++lAI; lAI < current.length;++lAI)
            addMessage(current[lAI]);

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
        addMessage(message);

    }
    public void addMessage(Message msg){
        lastAdded =msg;
        displayed.add(msg);
        if (container != null)
            container.setSelection(displayed.size() - 1);

    }

    public ArrayAdapter<Message> getAdapter(Context context){
        adapter = new ChatArrayAdapter(context, displayed, owner);
        return adapter;
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
    private class PerpetualUpdater extends AsyncTask<Void,Void,Void>{
        private final Logger logger = Logger.getLogger(PerpetualUpdater.class.getName());
        @Override
        protected Void doInBackground(Void... voids) {
           if (doPerpetualRun){
               try {
                   logger.info("\n start whaiting");
                   Thread.sleep(durationBeforeReUpdate);
                   logger.info("\n askUpdate");
                   askUpdate();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
            return null;
        }
    }
}
