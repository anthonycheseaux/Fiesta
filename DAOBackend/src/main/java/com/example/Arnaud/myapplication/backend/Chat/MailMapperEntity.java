package com.example.Arnaud.myapplication.backend.Chat;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.impl.ref.LiveRef;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Arnaud on 02.09.2016.
 */
@Entity
public class MailMapperEntity {
    @Id
    String userMail;

    private LiveRef<MessageBoxEntity> ownMail;
    public MessageBoxEntity getOwnMail(){
        return ownMail.get();
    }

    private TreeSet<LiveRef<MessageBoxEntity>> myMails;
    public List<MessageBoxEntity> getMyMails(){
        List<MessageBoxEntity> respons = new ArrayList<>(myMails.size());
        for (Iterator<LiveRef<MessageBoxEntity>> iterator = myMails.iterator(); iterator.hasNext();)
            respons.add(iterator.next().get());
        respons.remove(ownMail.get());
        return respons;
    }
    public void addMailBox(MessageBoxEntity mailBox){
        if (false == mailBox.getId().equals(userMail + userMail))
            myMails.add((LiveRef) Ref.create(mailBox));
    }
    MailMapperEntity(String userMail){
        myMails = new TreeSet<>();
        this.userMail = userMail;
    }

    MailMapperEntity(){}
}
