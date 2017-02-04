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

import java.util.ArrayList;
import java.util.List;

import br.ufscar.connect.adapters.FeedEvaluationListAdapter;
import br.ufscar.connect.adapters.FeedProblemListAdapter;
import br.ufscar.connect.models.FeedEvaluationPost;
import br.ufscar.connect.models.FeedProblemPost;
import br.ufscar.connect.models.Report;
import br.ufscar.connect.R;


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

    FeedEvaluationListAdapter evaluationListAdapter;
    FeedProblemListAdapter problemListAdapter;


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

            lv_all_publications.setAdapter(evaluationListAdapter);
        }

        if (v.getId() == R.id.btn_problems) {

            btnProblems.setBackgroundColor(Color.parseColor("#1fe3000a"));
            btnProblems.setTextColor(Color.parseColor("#2b2b2b"));
            btnProblems.setTypeface(null, Typeface.BOLD);

            btnEvaluations.setBackgroundResource(R.drawable.btnfundoallsides);
            btnEvaluations.setTextColor(Color.parseColor("#ffcacaca"));

            lv_all_publications.setAdapter(problemListAdapter);
        }

    }


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

        //Declarando arrays para dados das publicações
        //Publicações de problemas
        List<FeedProblemPost> feedProblemPostList = new ArrayList<>();

        feedProblemPostList.add(new FeedProblemPost("Arthur Regatieri Munhoz", "Aluno de Graduação", "27/11/2016", "Rua da Praça da Bandeira, 1500",
                "Danos ao Patrimônio", "Carros danificaram a grama da praça pois estavam estacionados em local indevido.",
                R.drawable.danosgramado, R.drawable.perfilphoto));
        feedProblemPostList.add(new FeedProblemPost("Breno Calixto", "Aluno de Graduação", "30/11/2016", "Rua Biblioteca Comunitária, 123",
                "Vazamento D'água", "Há um cano quebrado que está vazando muita água!",
                R.drawable.vazamentoagua, R.drawable.perfilphoto2));
        feedProblemPostList.add(new FeedProblemPost("Guiherme MMuniz Cardoso", "Aluno de Graduação", "01/12/2016", "Rua dos Ypês, 345",
                "Rede Eletrica", "Há um poste de energia danificado pela chuva que apresenta cabos desencapados e oferece perigo aos pedestres que ali passam.",
                R.drawable.redeeletrica, R.drawable.perfilphoto3));
        feedProblemPostList.add(new FeedProblemPost("Marco Alexandre Andrade", "Aluno de Graduação", "22/11/2016", "Rua Biblioteca Comunitária, 65",
                "Vazamento D'agua", "Tubulacao da caixa d'água se encontra estourada",
                R.drawable.caixadaguavazando, R.drawable.perfilphoto4));

        //Publicações de Avaliações
        List<FeedEvaluationPost> feedEvaluationPostList = new ArrayList<>();
        feedEvaluationPostList.add(new FeedEvaluationPost(R.drawable.perfilphoto, "Arthur Regatieri Munhoz", "Aluno de Graduação", "02/02/2017",
                "Departamento de Computação", (float)4.5, (float)4.0, (float)3.5, (float)4.0, (float)4.0));
        feedEvaluationPostList.add(new FeedEvaluationPost(R.drawable.perfilphoto2, "Breno Calixto", "Aluno de Graduação", "04/02/2017",
                "Departamento de Matemática", 3.0f, 3.0f, 4.5f, 5.0f, 2.0f));
        feedEvaluationPostList.add(new FeedEvaluationPost(R.drawable.perfilphoto4, "Marco Alexandre Andrade", "Aluno de Graduação", "04/02/2017",
                "Departamento de QualquerCoisa", 5.0f, 5.0f, 5.0f, 5.0f, 5.0f));


        this.evaluationListAdapter = new FeedEvaluationListAdapter(this, feedEvaluationPostList);
        this.problemListAdapter = new FeedProblemListAdapter(this, feedProblemPostList);

        lv_all_publications.setAdapter(problemListAdapter);
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
