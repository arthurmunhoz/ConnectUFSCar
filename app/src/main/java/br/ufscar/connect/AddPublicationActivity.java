package br.ufscar.connect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class AddPublicationActivity extends Activity {

    String USER_NAME, USER_LASTNAME, USER_EMAIL, USER_TYPE, USER_USERNAME, USER_PHOTO, USER_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpublication);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public void onButtonClick(View v) {

        //Se o usuario clicar no botao REPORTAR PROBLEMA, o app redireciona para a tela ReportActivity
        if (v.getId() == R.id.btn_reportar) {

            startActivity(new Intent(this, ReportActivity.class));
            finish();
        }

        //Se o usuario clicar no botao AVALIAR ESPACO, o app redireciona para a tela EvaluationActivity
        if (v.getId() == R.id.btn_avaliar) {

            startActivity(new Intent(this, EvaluationActivity.class));
            finish();
        }

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
        return;
    }

    public void backButtonHandler() {

        Intent i = new Intent(AddPublicationActivity.this, MenuActivity.class);
        startActivity(i);
        finish(); //termina a atividade liberando memória
    }


}
