package br.ufscar.connect.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import java.util.Collections;
import java.util.List;

import br.ufscar.connect.ConnectApplication;
import br.ufscar.connect.adapters.CustomComparatorEvaluationsPosts;
import br.ufscar.connect.adapters.CustomComparatorProblemsPosts;
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

    //-------------------------------------------------
    //Declarando variaveis

    private static List<FeedEvaluationPost> feedEvaluationPostList;

    public final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;

    View cv_separator;

    boolean imagesLoaded;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        context = this;

    }


    @Override
    public void onResume(){
        super.onResume();


        //Recebe os dados do usuario de USER_PREFERENCES
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        imagesLoaded = sharedPref.getBoolean("imagesLoaded", false);


        if (!imagesLoaded) {
            checksForStoragePermission();
        }

    }

    public void onButtonClick(View v) {

        //Clicou no botão 'AVALIAÇÕES'
        if (v.getId() == R.id.btn_evaluations) {

            //se nao tem permissao de armazenamento..
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                checksForStoragePermission();
            }
            else {

                btnEvaluations.setBackgroundColor(Color.parseColor("#1fe3000a"));
                btnEvaluations.setTextColor(Color.parseColor("#2b2b2b"));
                btnEvaluations.setTypeface(null, Typeface.BOLD);

                btnProblems.setBackgroundResource(R.drawable.btnfundoallsides);
                btnProblems.setTextColor(Color.parseColor("#ffcacaca"));

                lv_all_publications.setAdapter(evaluationListAdapter);
            }
        }

        if (v.getId() == R.id.btn_problems) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                checksForStoragePermission();
            }
            else {
                btnProblems.setBackgroundColor(Color.parseColor("#1fe3000a"));
                btnProblems.setTextColor(Color.parseColor("#2b2b2b"));
                btnProblems.setTypeface(null, Typeface.BOLD);

                btnEvaluations.setBackgroundResource(R.drawable.btnfundoallsides);
                btnEvaluations.setTextColor(Color.parseColor("#ffcacaca"));

                lv_all_publications.setAdapter(problemListAdapter);
            }
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

    public void checksForStoragePermission(){

        //Checks for STORAGE PERMISSION, if the app doesn't have permission, asks the user for it
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Do not have STORAGE permission yet, so we ask for it
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permissão necessária")
                        .setMessage("Habilite a permissão de ARMAZENAMENTO em:                 Permissões > Armazenamento")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Se o usuario cancelar a autorizacao de permissao, visualiza o mapa sem o botao MyLocation
                                Toast.makeText(getApplicationContext(), "ConnectUFSCar necessita da permisão:        ARMAZENAMENTO", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public static final int REQUEST_PERMISSION_SETTING = 1;

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(FeedActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);

                            }
                        })
                        .create()
                        .show();
            }else{
                //We have the permission, so we continue...
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Carregando publicações...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                AsyncTask task = new AsyncTask() {

                    final ConnectUFSCarApi api = ((ConnectApplication) getApplication()).getApi();

                    List<FeedProblemPost> feedProblemPostList = new ArrayList<>();
                    List<FeedEvaluationPost> feedEvaluationPost = new ArrayList<>();

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

                            return null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {

                        FeedActivity.feedProblemPostList = feedProblemPostList;
                        FeedActivity.feedEvaluationPostList = feedEvaluationPost;

                        //Referencia as variáveis aos objetos do XML
                        cv_separator =       findViewById(R.id.cv_separator);

                        btnEvaluations =    (Button) findViewById(R.id.btn_evaluations);
                        btnProblems =       (Button) findViewById(R.id.btn_problems);

                        iv_user_photo =     (ImageView) findViewById(R.id.iv_user_photo);
                        iv_publ_photo =     (ImageView) findViewById(R.id.iv_publ_photo);

                        tv_username =       (TextView) findViewById(R.id.tv_name);
                        tv_usertype =       (TextView) findViewById(R.id.tv_usertype);
                        tv_date =           (TextView) findViewById(R.id.tv_date);
                        tv_type =           (TextView) findViewById(R.id.tv_type);
                        tv_adrress =        (TextView) findViewById(R.id.tv_address);
                        tv_description =    (TextView) findViewById(R.id.tv_description);

                        lv_all_publications = (ListView) findViewById(R.id.lv_all_publications);

                        FeedActivity.this.evaluationListAdapter = new FeedEvaluationListAdapter(FeedActivity.this, feedEvaluationPostList);

                        Collections.sort(feedProblemPostList, new CustomComparatorProblemsPosts());
                        Collections.sort(feedEvaluationPost, new CustomComparatorEvaluationsPosts());
                        FeedActivity.this.problemListAdapter = new FeedProblemListAdapter(FeedActivity.this, feedProblemPostList);
                        lv_all_publications.setAdapter(problemListAdapter);

                        //Deixa registrado que as imagens do feed ja foram carregadas uma vez
                        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("imagesLoaded", true).apply(); //controle do feed

                        progressDialog.dismiss();

                    }
                };

                task.execute();

            }
        }
        else{
            //Here we are runnning in older versions of Android SDK, so we do not need to ask permission
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Carregando publicações...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            AsyncTask task = new AsyncTask() {

                final ConnectUFSCarApi api = ((ConnectApplication) getApplication()).getApi();

                List<FeedProblemPost> feedProblemPostList = new ArrayList<>();
                List<FeedEvaluationPost> feedEvaluationPost = new ArrayList<>();

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

                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {

                    FeedActivity.feedProblemPostList = feedProblemPostList;
                    FeedActivity.feedEvaluationPostList = feedEvaluationPost;

                    //Referencia as variáveis aos objetos do XML
                    cv_separator =       findViewById(R.id.cv_separator);

                    btnEvaluations =    (Button) findViewById(R.id.btn_evaluations);
                    btnProblems =       (Button) findViewById(R.id.btn_problems);

                    iv_user_photo =     (ImageView) findViewById(R.id.iv_user_photo);
                    iv_publ_photo =     (ImageView) findViewById(R.id.iv_publ_photo);

                    tv_username =       (TextView) findViewById(R.id.tv_name);
                    tv_usertype =       (TextView) findViewById(R.id.tv_usertype);
                    tv_date =           (TextView) findViewById(R.id.tv_date);
                    tv_type =           (TextView) findViewById(R.id.tv_type);
                    tv_adrress =        (TextView) findViewById(R.id.tv_address);
                    tv_description =    (TextView) findViewById(R.id.tv_description);

                    lv_all_publications = (ListView) findViewById(R.id.lv_all_publications);

                    FeedActivity.this.evaluationListAdapter = new FeedEvaluationListAdapter(FeedActivity.this, feedEvaluationPostList);

                    Collections.sort(feedProblemPostList, new CustomComparatorProblemsPosts());
                    Collections.sort(feedEvaluationPost, new CustomComparatorEvaluationsPosts());
                    FeedActivity.this.problemListAdapter = new FeedProblemListAdapter(FeedActivity.this, feedProblemPostList);
                    lv_all_publications.setAdapter(problemListAdapter);

                    //Deixa registrado que as imagens do feed ja foram carregadas uma vez
                    SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("imagesLoaded", true).apply(); //controle do feed

                    progressDialog.dismiss();

                }
            };

            task.execute();
        }

    }


}

