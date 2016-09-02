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

    private LiveRef<MailBoxEntity> ownMail;
    public MailBoxEntity getOwnMail(){
        return ownMail.get();
    }

    private TreeSet<LiveRef<MailBoxEntity>> myMails;
    public List<MailBoxEntity> getMyMails(){
        List<MailBoxEntity> respons = new ArrayList<>(myMails.size());
        for (Iterator<LiveRef<MailBoxEntity>> iterator = myMails.iterator(); iterator.hasNext();)
            respons.add(iterator.next().get());
        respons.remove(ownMail.get());
        return respons;
    }
    public void addMailBox(MailBoxEntity mailBox){
        if (false == mailBox.getId().equals(userMail + userMail))
            myMails.add((LiveRef) Ref.create(mailBox));
    }
    MailMapperEntity(String userMail){
        myMails = new TreeSet<>();
    }
}
