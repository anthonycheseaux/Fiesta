package hevs.ch.fiesta.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hevs.ch.fiesta.R;

public class TranspAct extends HypermediaBrowser {

    //TODO
    //COncerne le code si l'utilisateur est un driver
    //Liste de drivers à supprimer
    ArrayList<String> selectedItems= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transp);
        ListView chl=(ListView)findViewById(R.id.passengerList);
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.row_trasp);
        chl.setAdapter(adapter);
        //Ce qui se passe lorsqu'on choisit un item dans la checklist
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectediItem=((TextView)view).getText().toString();

                //S'il existe déjà on retire
                if(selectedItems.contains(selectediItem)){
                    selectedItems.remove(selectediItem);
                }
                // Sinon on l'ajoute à la liste de suppression
                else{
                    selectedItems.add(selectediItem);
                }

            }
        });
    }

    public  void deleteDrinker(View view){
        String items="";
        for(String item:selectedItems){
            items+="-"+item+"\n";
        }
        Toast.makeText(this,"Vous avez supprimé \n"+items,Toast.LENGTH_LONG);

        //TODO
        //Ajouter le code de suppression avec l'hyper media

    }
}
