package hevs.ch.fiesta.states;

/**
 * Created by darle on 12.08.2016.
 */
public class AppInscriptionState extends AppAbstractState {

    public AppInscriptionState(){
        this.type = this.getClass().getName();
        this.validNextStep = new  String[] {
            AppCreateTransportState.class.getName(),
            AppSearchATransportState.class.getName()};
    }
    
}
