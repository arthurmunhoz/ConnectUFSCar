package br.ufscar.connect.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.ufscar.connect.ConnectApplication;
import br.ufscar.connect.adapters.FeedEvaluationListAdapter;
import br.ufscar.connect.adapters.FeedProblemListAdapter;
import br.ufscar.connect.interfaces.ConnectUFSCarApi;
import br.ufscar.connect.models.Evaluation;
import br.ufscar.connect.models.FeedEvaluationPost;
import br.ufscar.connect.models.FeedProblemPost;
import br.ufscar.connect.models.Report;
import br.ufscar.connect.R;
import br.ufscar.connect.models.User;


public class FeedActivity extends Activity {
    private static List<FeedProblemPost> feedProblemPostList;
    private static List<FeedEvaluationPost> feedEvaluationPostList;

    //-------------------------------------------------
    //Declarando variaveis

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

    ConnectUFSCarApi api;

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

        api = ((ConnectApplication) getApplication()).getApi();

        AsyncTask task = getLoadingPublTask();
        try {
            task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


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


        // FIXME: péssima gambiarra abaixo. Fica aguardando a task carregar as listas
        while (this.feedEvaluationPostList == null || this.feedProblemPostList == null){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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


    public AsyncTask getLoadingPublTask() {
        final ConnectUFSCarApi api = ((ConnectApplication) getApplication()).getApi();

        return new AsyncTask() {
            List<FeedProblemPost> feedProblemPostList = new ArrayList<>();
            List<FeedEvaluationPost> feedEvaluationPost = new ArrayList<>();
            public boolean finished = false;

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    List<Report> reportList = api.reportList().execute().body();

                    for (Report r : reportList) {
                        User user = api.getUser(r.getUser_id()).execute().body();
                        if (user != null) {
                            FeedProblemPost post = new FeedProblemPost(user.getName(), user.getUser_type(),
                                    r.getDate(), r.getProblemAddress(), r.getProblemCategory(), r.getProblemDescription(),
                                    r.getProblemPhoto(), user.getUser_photo());
                            feedProblemPostList.add(post);
                        }
                    }

                    List<Evaluation> evaluationList = api.evaluationList().execute().body();

                    for (Evaluation e : evaluationList) {
                        User user = api.getUser(e.getUserId()).execute().body();
                        if (user != null) {
                            FeedEvaluationPost post = new FeedEvaluationPost(user.getUser_photo(), user.getName(),
                                    user.getUser_type(), e.getDate(), e.getEspaco(), e.getInfra(), e.getAcess(),
                                    e.getLimp(), e.getSeg(), e.getGeral());

                            feedEvaluationPost.add(post);
                        }
                    }
                    // FIXME: as duas linhas a baixo deviam estar no onPostExecute, mas por algum motivo ele nao tá sendo chamado nunca...
                    FeedActivity.feedProblemPostList = feedProblemPostList;
                    FeedActivity.feedEvaluationPostList = feedEvaluationPost;
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
            }
        };
    }
}
