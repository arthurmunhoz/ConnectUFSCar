package br.ufscar.connect;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

import br.ufscar.connect.Models.Evaluation;
import br.ufscar.connect.interfaces.ConnectUFSCarApi;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


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

    ConnectUFSCarApi api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        //-------------------------------------------------------
        //Inicializando API
        api = ConnectUFSCarApi.RETROFIT.create(ConnectUFSCarApi.class);

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
            if (spinner_espacos.getSelectedItem().toString().contentEquals("")) {
                spinner_espacos.setPrompt("Escolha o local a avaliar.");
            }

            //------------------------------------------------
            //Pegando notas das categorias e local
            Evaluation evaluation = new Evaluation();
            evaluation.setEspaco(spinner_espacos.getSelectedItem().toString() + "");
            evaluation.setInfra(notaInfra.getRating());
            evaluation.setAcess(notaAcess.getRating());
            evaluation.setLimp(notaLimp.getRating());
            evaluation.setSeg(notaSeg.getRating());
            evaluation.setGeral(notaGeral.getRating());
            Date date = new Date(System.currentTimeMillis());
            evaluation.setDate(date.toString());


            //-------------------------------------------------------------------------------------
            //Enviando infos ao BD atraves das chamadas definidas na interface ConnectUFSCarApi
            api.evaluationCreate(evaluation).enqueue(new Callback<Evaluation>() {

                @Override
                public void onResponse(Response<Evaluation> response, Retrofit retrofit) {
                    //Se o servidor retornou com sucesso
                    if (response.isSuccess()) {

                        // Exibe mensagem de sucesso
                        Toast.makeText(getApplicationContext(), "AVALIAÇÃO REALIZADA COM SUCESSO", Toast.LENGTH_LONG).show();

                        //Volta a tela de Feed
                        Intent i = new Intent(EvaluationActivity.this, MenuActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        try {
                            String error = response.errorBody().string();
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            Log.e("ERROR TAG", e.getMessage(), e);

                            // Exibe mensagem de sucesso
                            Toast.makeText(getApplicationContext(), "AVALIAÇÃO REALIZADA COM SUCESSO*", Toast.LENGTH_LONG).show();

                            //Volta a tela de Feed
                            Intent i = new Intent(EvaluationActivity.this, MenuActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ERROR_FAILURE", t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
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
