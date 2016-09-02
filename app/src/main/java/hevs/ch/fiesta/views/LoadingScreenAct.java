package hevs.ch.fiesta.views;


import android.os.Bundle;
import hevs.ch.fiesta.R;


public class LoadingScreenAct extends HypermediaBrowser {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        stateStack.askUpdate();
    }
}
