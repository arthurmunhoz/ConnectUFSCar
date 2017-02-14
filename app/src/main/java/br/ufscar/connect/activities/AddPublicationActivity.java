package br.ufscar.connect.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.ufscar.connect.R;


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

        }

        //Se o usuario clicar no botao AVALIAR ESPACO, o app redireciona para a tela EvaluationActivity
        if (v.getId() == R.id.btn_avaliar) {

            startActivity(new Intent(this, EvaluationActivity.class));

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

    }

    public void backButtonHandler() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddPublicationActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("SAINDO...");
        // Setting Dialog Message
        alertDialog.setMessage("Tem certeza que deseja sair?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.doorcolored);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("SIM",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "ATÉ LOGO!", Toast.LENGTH_SHORT).show();

                        try {
                            finish();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("CANCELAR",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
        /*
        Intent i = new Intent(AddPublicationActivity.this, MenuActivity.class);
        startActivity(i);
        finish(); //termina a atividade liberando memória
        */
    }


}
