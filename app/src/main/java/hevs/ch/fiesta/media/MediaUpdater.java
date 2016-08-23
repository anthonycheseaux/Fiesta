package hevs.ch.fiesta.media;

/**
 * Created by Arnaud on 19.08.2016.
 */
public interface MediaUpdater {
    void setMediaStack (MediaStack mediaStack);
    void setSubject (Observable observable);
    void doUpdate();

}
