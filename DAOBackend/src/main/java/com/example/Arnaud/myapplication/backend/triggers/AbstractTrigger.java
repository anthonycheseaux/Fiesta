package com.example.Arnaud.myapplication.backend.triggers;

/**
 * Created by Arnaud on 31.08.2016.
 * AbsractTriggers represent side effect of actions performed by managers but who do not need to be done before sendind output. il work a little like asyncTask, but simply.
 * it work in an ananched decorator pattern, who works in both directions.
 * any part of the chain can lauch the entire trigger line.
 */
public abstract class AbstractTrigger implements Runnable {
    protected AbstractTrigger beforeTrigger;
    protected AbstractTrigger nextTrigger;


    /**
     * we use a Template pattern to rewrite the natural behaior of Runanable.
     * run is final and the "new Run()" is performeAction. the dobjective of all of that is to execute the all chain in 1 thread.
     */
    @Override
    public final void run() {
        performeAction();
        nextTrigger.run();
    }
    protected abstract void performeAction();

    public void lauchTriggerChain(){
        if (beforeTrigger == null)
            new Thread(this).run();
        else
            beforeTrigger.lauchTriggerChain();
    }

    /**
     *add the trigger in argument chain's top
     * @param trigger
     */
    public void addAtTop(AbstractTrigger trigger){
        if (beforeTrigger == null)
            buildChain(trigger, this);
        else
            beforeTrigger.addAtTop(trigger);

    }
    /**
     * add the trigger in argument just before the current one
     * @param trigger
     */
    public void addBefore(AbstractTrigger trigger){
        if (beforeTrigger == null)
            buildChain(trigger, this);
        else
            buildChain(beforeTrigger, trigger, this);
    }

    /**
     * add the trigger in argument juste after the current one
     * @param trigger
     */
    public void addAfter(AbstractTrigger trigger){
        if(nextTrigger == null)
            buildChain(this, trigger);
        else
            buildChain(this, trigger, nextTrigger);
    }

    /**
     * add the trigger in argument on chain's bottom
     * @param trigger
     */
    public void addAtEnd(AbstractTrigger trigger){
        if (nextTrigger == null)
            buildChain(this, trigger);
        else
            nextTrigger.addAtEnd(trigger);
    }

    /**
     * simple static methode to make chain building easier to read.
     * @param head
     * @param end
     */
    private static final void buildChain(AbstractTrigger head, AbstractTrigger end){
        head.nextTrigger= end;
        end.beforeTrigger = head;
    }
    /**
     * simple static methode to make chain building easier to read.
     * @param head
     * @param end
     */
    private static final void buildChain(AbstractTrigger head, AbstractTrigger middle, AbstractTrigger end){
        head.nextTrigger= middle;
        middle.beforeTrigger = head;

        middle.nextTrigger = end;
        end.beforeTrigger = middle;

    }

}
