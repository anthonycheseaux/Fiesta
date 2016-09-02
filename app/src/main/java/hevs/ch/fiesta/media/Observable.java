package hevs.ch.fiesta.media;

/**
 * Created by Arnaud on 19.08.2016.
 */
public interface Observable {
    void register(MediaDisplayer mediaDisplayer);
    void unRegister (MediaDisplayer mediaDisplayer);
    void notifyStateChange();


}
