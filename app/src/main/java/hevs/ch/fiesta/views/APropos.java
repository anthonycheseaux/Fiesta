package hevs.ch.fiesta.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import hevs.ch.fiesta.R;

public class APropos extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apropos);


    }

    public void backHomeA (View view){

        super.onBackPressed();
    }
}
