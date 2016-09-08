package com.example.Arnaud.myapplication.backend.triggers;

import com.example.Arnaud.myapplication.backend.Chat.Message;
import com.example.Arnaud.myapplication.backend.Chat.MessageBoxEntityEndpoint;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Arnaud on 04.09.2016.
 */
@Deprecated
public class NotifyDrinkers_LiftContentChange extends AbstractTrigger {
    private LiftEntity lift;
    private List<UserEntity> oldContent;
    private MessageBoxEntityEndpoint msgEnpoint;

    public NotifyDrinkers_LiftContentChange(LiftEntity liftEntity, List<UserEntity> oldContent){
        super();
        this.lift = liftEntity;
        this.oldContent = oldContent;
    }

    @Override
    protected void performeAction() {


        List<UserEntity> newContent = this.lift.getDrinkers();
        List<UserEntity> added = new ArrayList<>();
        List<UserEntity> removed = new ArrayList<>();


        if (oldContent!= null)
            for (Iterator<UserEntity> iterator = oldContent.iterator(); iterator.hasNext();)
                removed.add(iterator.next());
        if (newContent != null)
            for (Iterator<UserEntity> iterator = newContent.iterator(); iterator.hasNext();)
                added.add(iterator.next());

        for (Iterator<UserEntity> iterator = oldContent.iterator(); iterator.hasNext();)
            added.remove(iterator.next());

        for (Iterator<UserEntity> iterator = newContent.iterator(); iterator.hasNext();)
            removed.remove(iterator.next());

        if (added.size() == 0 && removed.size()== 0)
            return;
        msgEnpoint = new MessageBoxEntityEndpoint();

        for (Iterator<UserEntity> iterator = added.iterator(); iterator.hasNext();)
            msgEnpoint.insert(new Message("vous avez été ajouté au trasnport de "+lift.getDriver().getUserName()+" a destination de "+lift.getDestination(),iterator.next().getEmail(),"System",new Date()));
        for (Iterator<UserEntity> iterator = removed.iterator(); iterator.hasNext();)
            msgEnpoint.insert(new Message("vous avez été supprimé du trasnport de "+lift.getDriver().getUserName()+" a destination de "+lift.getDestination(),iterator.next().getEmail(),"System",new Date()));;
    }
}
