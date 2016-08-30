package hevs.ch.fiesta.views;


import android.os.Bundle;
import hevs.ch.fiesta.R;


public class LoadingScreen extends HypermediaBrowser {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        stateStack.askUpdate();
    }


}
