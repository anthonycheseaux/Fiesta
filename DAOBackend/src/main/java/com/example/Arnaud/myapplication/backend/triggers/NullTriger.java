package com.example.Arnaud.myapplication.backend.triggers;

/**
 * Created by Arnaud on 31.08.2016.
 */
public class NullTriger extends AbstractTrigger {
    @Override
    protected void performeAction() {

    }

    @Override
    public void lauchTriggerChain() {
        if (nextTrigger == null && beforeTrigger == null)
            return;
        else
            super.lauchTriggerChain();
    }
}
