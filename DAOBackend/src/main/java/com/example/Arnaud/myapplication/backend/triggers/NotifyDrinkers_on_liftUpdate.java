package com.example.Arnaud.myapplication.backend.triggers;


import com.example.Arnaud.myapplication.backend.UserEntity;
import java.util.List;

/**
 * Created by Arnaud on 07.09.2016.
 */
public class NotifyDrinkers_on_liftUpdate extends AbstractTrigger {
    List<UserEntity> added;
    List<UserEntity> removed;

    public NotifyDrinkers_on_liftUpdate(List<UserEntity> added, List<UserEntity> removed){
        this.added = added;
        this.removed = removed;
    }
    @Override
    protected void performeAction() {

    }
}
