package br.ufscar.connect.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.ufscar.connect.models.Report;
import br.ufscar.connect.R;
import br.ufscar.connect.adapters.FeedActivityCustomAdapter;


public class FeedActivity extends Activity {

    //-------------------------------------------------
    //Declarando variaveis

    Report report;

    View cv_separator;

    Button btnProblems, btnEvaluations;

    ImageView iv_user_photo;
    ImageView iv_publ_photo;

    TextView tv_username;
    TextView tv_usertype;
    TextView tv_date;
    TextView tv_type;
    TextView tv_adrress;
    TextView tv_description;

    ListView lv_all_publications;

    Context context;


    //Declarando arrays para dados das publicações

    /*Array para Profile Pic.*/ public static int[] user_photo_list = {R.drawable.perfilphoto, R.drawable.perfilphoto2, R.drawable.perfilphoto3, R.drawable.perfilphoto4};
    /*Array de Publ. Photos*/   public static int[] all_publication_photo_list = {R.drawable.danosgramado, R.drawable.vazamentoagua, R.drawable.redeeletrica, R.drawable.caixadaguavazando};
    /*Array de Usernames*/      public String[] publ_usernames = {"Arthur Regatieri Munhoz", "Breno Calixto", "Guiherme MMuniz Cardoso", "Marco Alexandre Andrade"};
    /*Array de Usertypes*/      public String[] publ_usertypes = {"Aluno de Graduação", "Aluno de Graduação", "Aluno de Graduação", "Aluno de Graduação"};
    /*Array de Dates*/          public String[] publ_dates = {"27/11/2016", "30/11/2016", "01/12/2016", "22/11/2016"};
    /*Array de Types*/          public static String[] all_publication_type_list = {"Danos ao Patrimônio", "Vazamento D'água", "Rede Eletrica", "Vazamento D'agua"};
    /*Array de Addresses*/      public String[] all_publication_address_list = {"Rua da Praça da Bandeira, 1500", "Rua Biblioteca Comunitária, 123", "Rua dos Ypês, 345", "Rua Biblioteca Comunitária, 65"};
    /*Array de Descriptions*/   public String[] all_publication_description_list = {"Carros danificaram a grama da praça pois estavam " +
            "estacionados em local indevido.", "Há um cano quebrado que está vazando muita água!", "Há um poste de energia danificado pela chuva " +
            "que apresenta cabos desencapados e oferece perigo aos pedestres que ali passam.", "Tubulacao da caixa d'água se encontra estourada"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        context = this;

        //Referencia as variáveis aos objetos do XML
        cv_separator = (View) findViewById(R.id.cv_separator);

        btnEvaluations = (Button) findViewById(R.id.btn_evalutaions);
        btnProblems = (Button) findViewById(R.id.btn_problems);

        iv_user_photo = (ImageView) findViewById(R.id.iv_user_photo);
        iv_publ_photo = (ImageView) findViewById(R.id.iv_publ_photo);

        tv_username = (TextView) findViewById(R.id.tv_name);
        tv_usertype = (TextView) findViewById(R.id.tv_usertype);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_adrress = (TextView) findViewById(R.id.tv_address);
        tv_description = (TextView) findViewById(R.id.tv_description);

        lv_all_publications = (ListView) findViewById(R.id.lv_all_publications);

        //Completa os objtos da LISTA DE PUBLICACOES atraves do adapter personalizado e passa como parametro
        // os arrays declarados e inicializados anteriormente neste arquivo
        lv_all_publications.setAdapter(new FeedActivityCustomAdapter(this, user_photo_list,
                publ_usernames, publ_usertypes, publ_dates, all_publication_address_list,
                all_publication_type_list, all_publication_description_list, all_publication_photo_list));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    public void onACTION_UP(View v) {
        if (v.getId() == R.id.lv_all_publications) {

        }
    }

    public void onButtonClick(View v) {

        //Clicou no botão 'AVALIAÇÕES'
        if (v.getId() == R.id.btn_evalutaions) {

            btnEvaluations.setBackgroundColor(Color.parseColor("#1fe3000a"));
            btnEvaluations.setTextColor(Color.parseColor("#2b2b2b"));
            btnEvaluations.setTypeface(null, Typeface.BOLD);

            btnProblems.setBackgroundResource(R.drawable.btnfundoallsides);
            btnProblems.setTextColor(Color.parseColor("#ffcacaca"));
        }

        if (v.getId() == R.id.btn_problems) {

            btnProblems.setBackgroundColor(Color.parseColor("#1fe3000a"));
            btnProblems.setTextColor(Color.parseColor("#2b2b2b"));
            btnProblems.setTypeface(null, Typeface.BOLD);

            btnEvaluations.setBackgroundResource(R.drawable.btnfundoallsides);
            btnEvaluations.setTextColor(Color.parseColor("#ffcacaca"));

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //Se o usuario escolhe a opcao SAIR, o aplicativo o redireciona para a tela de LOGIN
        if (id == R.id.action_sair) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
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

    //Altera a função do botão "voltar" do Android
    //Se o usuario clicar no botao 'voltar' do Android (<) o usuario recebe um AlertDialog para confirmar a saida do app
    public void backButtonHandler() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FeedActivity.this);
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
    }

}
