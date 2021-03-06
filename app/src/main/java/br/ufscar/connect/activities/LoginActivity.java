package br.ufscar.connect.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufscar.connect.ConnectApplication;
import br.ufscar.connect.models.User;
import br.ufscar.connect.R;
import br.ufscar.connect.interfaces.ConnectUFSCarApi;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends Activity {

    //Declarando variaveis
    EditText et_username;
    EditText et_password;
    Button btn_login;
    String username;
    String password;
    Context c;
    private ConnectUFSCarApi api;
    public final static String USER_PREFERENCES = "USER_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        c = this;
        et_username = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        api = ((ConnectApplication) getApplication()).getApi();
    }

    public void onButtonClick(View v) {

        //------------------------------------------------------------------------------------------
        //Se o usuario clicar no botao Cadastrar, abre-se a activity SignUpActivity
        if (v.getId() == R.id.tv_cadastrese) {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        }

        //------------------------------------------------------------------------------------------
        //Se o usuario clicar no botao ENTRAR, abre-se a activity MenuActivity iniciada em FEED
        if (v.getId() == R.id.btn_login) {

            username = et_username.getText() + "";
            password = et_password.getText() + "";

            //-------------------- VERIFICACOES DE ERROS ----------------------
            //Se o usuario nao digitar nada em Nome de Usuario, recebe uma mensagem de erro
            if (username.length() == 0) {

                et_username.setError("Digite seu nome de usuário!");
                //Toast.makeText(c, "Nome de Usuário ou Senha inválidos.", Toast.LENGTH_LONG).show();
                return;
            }

            //Se o usuario nao digitar nada em Senha, recebe uma mensagem de erro
            if (password.length() == 0) {

                et_password.setError("Digite sua senha!");
                return;
            }

            new upToDB().execute();

        }// end of btn_entrar click


    }// end of onClick


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void onResume() {
        super.onResume();


    }

    private class upToDB extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }


        @Override
        protected Void doInBackground(Void... voids) {

            //--------------- REALIZANDO A POST REQUEST PARA SALVAR OS DADOS DO USUARIO NO BD ---------------

            // Prepare the HTTP request
            api.login(username, password).enqueue(new Callback<User>() {

                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {
                    //Se o servidor retornou com sucesso
                    if (response.isSuccess() && response.code() == 200) {
                        // Exibe mensagem de sucesso
                        Toast.makeText(c, "BEM VINDO(A), " + response.body().getName() + "!", Toast.LENGTH_LONG).show();

                        //Salva os dados do usuario em SharedPreferences para uso em outras activites
                        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("user_id", response.body().getUser_id()).apply();
                        editor.putString("name", response.body().getName()).apply();
                        editor.putString("lastname", response.body().getLast_name()).apply();
                        editor.putString("email", response.body().getEmail()).apply();
                        editor.putString("usertype", response.body().getUser_type()).apply();
                        editor.putString("image_url", response.body().getUser_photo()).apply();
                        editor.putString("username", response.body().getUsername()).apply();
                        editor.putString("problem_photo", "").apply();
                        editor.putBoolean("imagesLoaded", false).apply(); //controle do feed

                        Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        String error = "Não foi possível fazer login";
                        Toast.makeText(c, error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ERROR_FAILURE", t.getMessage());
                    Toast.makeText(c, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


            return null;
        }
    }
}
