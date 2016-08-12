package hevs.ch.fiesta.views;

import android.app.Activity;
import android.content.pm.ActivityInfo;

/**
 * Created by Arnaud on 11.08.2016.
 */
public class CustomActivity extends Activity {
    //protected AbstractState currentState;

    protected void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
