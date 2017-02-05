package br.ufscar.connect.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import br.ufscar.connect.ConnectApplication;
import br.ufscar.connect.models.Evaluation;
import br.ufscar.connect.R;
import br.ufscar.connect.interfaces.ConnectUFSCarApi;


public class EvaluationActivity extends Activity {

    //DECLARANDO VARIAVEIS

    Spinner spinner_espacos;
    RatingBar notaInfra;
    RatingBar notaAcess;
    RatingBar notaLimp;
    RatingBar notaSeg;
    RatingBar notaGeral;
    String nomeLocal;
    String USER_ID;
    Button btn_concluido;
    Evaluation evaluation;

    ConnectUFSCarApi api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        //-------------------------------------------------------
        //Inicializando API
        api = ((ConnectApplication) getApplication()).getApi();

        //Referenciando objetos do xml
        spinner_espacos = (Spinner) findViewById(R.id.spinnerEspaco);
        nomeLocal = spinner_espacos.getSelectedItem().toString();
        notaInfra = (RatingBar) findViewById(R.id.rb_infra);
        notaAcess = (RatingBar) findViewById(R.id.rb_acess);
        notaLimp = (RatingBar) findViewById(R.id.rb_limp);
        notaSeg = (RatingBar) findViewById(R.id.rb_seg);
        notaGeral = (RatingBar) findViewById(R.id.rb_geral);
        btn_concluido = (Button) findViewById(R.id.btn_concluido);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Recebe os dados do usuario de USER_PREFERENCES
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        USER_ID = sharedPref.getString("user_id", "");

    }

    public void onButtonClick(View v) {

        //Se o usuario clicar no botao CONCLUIDO, exibe-se uma mensagem e retorna para a activity LoginActivity
        if (v.getId() == R.id.btn_concluido) {

            //Verificando erros:
            //Se o usuario nao selecionou nenhum espaço, exibe mensagem de erro
            if (spinner_espacos.getSelectedItem().toString().contentEquals("Selecione o espaço")) {
                Toast.makeText(getApplicationContext(), "Selecione o espaço que deseja avaliar", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(getApplicationContext(), "Avaliação realizada com sucesso!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_avaliar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();

    }

    public void backButtonHandler() {

        this.finish(); //termina a atividade liberando memória
    }

}
