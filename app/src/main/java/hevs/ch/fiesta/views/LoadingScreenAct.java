package hevs.ch.fiesta.views;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import hevs.ch.fiesta.R;
import hevs.ch.fiesta.media.AsyncRestoration;


public class LoadingScreenAct extends HypermediaBrowser {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(stateStack.getUpdateMedia() == null) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            Long mediaId = sharedPref.getLong(MEDIA_ID_KEY, -1);
            Toast.makeText(
                    getApplicationContext(),
                    "serached  "+mediaId+ "as media id",
                    Toast.LENGTH_LONG)
                    .show();
            new AsyncRestoration(stateStack, mediaId).execute();
        }else
            stateStack.askUpdate();
    }
}
