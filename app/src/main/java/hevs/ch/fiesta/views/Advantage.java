package hevs.ch.fiesta.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hevs.ch.fiesta.R;

public class Advantage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advantage);


    }

    public void backHome(View view){
        setContentView(R.layout.choose_event);
    }


}
