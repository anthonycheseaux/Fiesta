package hevs.ch.fiesta.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hevs.ch.fiesta.R;
import hevs.ch.fiesta.media.AsyncRestoration;
import hevs.ch.fiesta.states.InscriptionState;
import hevs.ch.fiesta.states.MediaAdapter;


public class InscriptionAct extends HypermediaBrowser implements View.OnClickListener {

    private InscriptionState state;

    private EditText editText_username;
    private EditText editText_mail;
    private EditText editText_phone;
    private Button btn_driver;
    private Button btn_drinker;
    private final static String USER_NAME = "username";
    private final static String USER_MAIL = "usermail";
    private final static String USER_PHONE = "userPhone";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        //Instanciation
        state = (InscriptionState) MediaAdapter.adapt(stateStack.getUpdateMedia());

        editText_username=(EditText)findViewById(R.id.inscription_edtxt_Username);
        editText_mail = (EditText)findViewById(R.id.inscription_edtxt_UserMail);
        editText_phone = (EditText)findViewById(R.id.inscription_edtxt_UserPhone);
        btn_driver = (Button)findViewById(R.id.inscription_btn_driver);
        btn_drinker = (Button)findViewById(R.id.inscription_btn_drinker);

        // put action listner
        btn_driver.setOnClickListener(this);
        btn_drinker.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editText_username.setText(sharedPref.getString(USER_NAME,""));
        editText_mail.setText(sharedPref.getString(USER_MAIL,""));
        editText_phone.setText(sharedPref.getString(USER_PHONE,""));
    }

    @Override
    public void onClick(View view) {
        //form validation
        String username= editText_username.getText().toString();
        String userMail= editText_mail.getText().toString();
        String userPhone= editText_phone.getText().toString();

        String validationException="";

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(username.equals(""))
            validationException += "renseigner votre nom \n";
        else
            editor.putString(USER_NAME,username);
        if(userMail.equals(""))
            validationException += "renseigner votre adresse mail \n";
        else
            editor.putString(USER_MAIL,userMail);
        if(userPhone.equals(""))
            validationException += "renseigner votre n° de telephone";
        else
            editor.putString(USER_PHONE, userPhone);

        editor.commit();

        if(false==(validationException.equals("")) ){
            Toast.makeText(
                    getApplicationContext(),
                    validationException,
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        // set data in State
        state.setUserName(username);
        state.setUserEmail(userMail);
        state.setUserPhone(userPhone);

        if(view.getId()==btn_driver.getId())
            state.doInscriptionAsDriver();
        if(view.getId()==btn_drinker.getId())
            state.doInscriptionAsDrinker();

        // validate State
        state.validateData();

        //lanch loading screen
        startActivity(new Intent(this, LoadingScreenAct.class));
    }
}
