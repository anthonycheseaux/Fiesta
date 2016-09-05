package hevs.ch.fiesta.chat;

import android.os.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by darle on 05.09.2016.
 */

public class AsyncGetMessage extends AsyncTask<Void, Void, Boolean> {

    // On a besoin du contexte pour replacer l'AsyncTask
    private Context context;
    // On récupère l'activité d'appel, au cas où besoin dans le traitement
    private Activity activity;

    /**
     * Constructeur de l'asyncTask.

     */
    public AsyncGetMessage(Activity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /*
         * Cette fonction contiendra le code exécuté au préalable, par exemple:
         *  -Affichage d'une ProgressBar
         *      =rond qui tourne pour indiquer une attente
         *      =Barre de progression
         *  -...
         */
    }

    @Override
    protected Boolean doInBackground(Void... arg0) {
        return null;
        /*faire un service qui va voir les mailbox et fait une notification lors de nouveau messages.
Asynktask qui va chercher les données
tous les x temps creation d'une nouvelle asynktask et exécution

tu prends dans ton adapter
Adapter : mb_old tous les x temps tu vas remplacer l'actuelle mb_current
MB_LD (last display)
MB_SYNK elle set
aller dans media manager (meme principe) set state voir si la mailbox que tu as a plus de message que la dernière que
tu as afficher si y a plus c'est qu il y a au moins un nouveau message
quand ca arrive tu mets une alerte

on a un endpoint qui peut recevoir des messages il met dans les mailbox et fait rien
donc il faut le service qui va checker les mailbox afin de pouvoir relever les messages

quand on va appeler le service pour afficher les messages la MB_SYNC devient MB_LD et on va recevoir une
nouvelle SYNC
         */
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        /*
         * Ici, le code exécuté une fois le traitement terminé, par exemple:
         *  -Mise à jour de l'affichage
         *  -Affichage d'une pop-up indiquant la fin du traitement
         *  -Désactivation de la ProgressBar
         *  -...
         */
    }

    @Override
    protected void onCancelled() {

    }

}
