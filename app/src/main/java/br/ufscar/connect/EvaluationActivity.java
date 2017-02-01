package br.ufscar.connect;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.security.Timestamp;
import java.util.Date;

import br.ufscar.connect.Models.Evaluation;
import br.ufscar.connect.Models.Report;
import br.ufscar.connect.interfaces.ConnectUFSCarApi;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class EvaluationActivity extends Activity {

    //DECLARANDO VARIAVEIS
    public static final int CAMERA_REQUEST = 10;

    ImageView iv_foto;
    Spinner spinner_espacos;
    RatingBar notaInfra;
    RatingBar notaAcess;
    RatingBar notaLimp;
    RatingBar notaSeg;
    RatingBar notaGeral;
    String nomeLocal;
    String USER_ID;
    String created_at;
    Timestamp ts;

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


            //Recebe os dados do usuario de USER_PREFERENCES
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
            USER_ID = sharedPref.getString("user_id", "");

            //-------------------------------------------------------------------------------------
            //Enviando infos ao BD atraves das chamadas definidas na interface ConnectUFSCarApi
            api.evaluationCreate(evaluation).enqueue(new Callback<Report>() {

                @Override
                public void onResponse(Response<Report> response, Retrofit retrofit) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //O usuario escolheu OK depois de tirar a foto? Se sim, armazena a foto e a coloca no ImageView invisivel e o torna visivel
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST){

                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");  //agora ja temos a imagem da camera guardada em cameraIamgem

                iv_foto.setImageBitmap(cameraImage);
                iv_foto.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {

        finish(); //termina a atividade liberando memória
    }

}
