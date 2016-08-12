package hevs.ch.fiesta.states;

import com.example.arnaud.myapplication.backend.userEntityApi.model.UserEntity;

/**
 * Created by darle on 12.08.2016.
 */
public class AppInscriptionState extends AppAbstractState {
   UserEntity tt;
    public AppInscriptionState(){
        this.type = this.getClass().getName();
        this.validNextStep = new  String[] {
            AppCreateTransportState.class.getName(),
            AppSearchATransportState.class.getName()};
    }
    
}
