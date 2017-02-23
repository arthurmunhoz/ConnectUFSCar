package br.ufscar.connect.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.connect.ConnectApplication;
import br.ufscar.connect.R;
import br.ufscar.connect.adapters.FeedProblemListAdapter;
import br.ufscar.connect.interfaces.ConnectUFSCarApi;
import br.ufscar.connect.models.FeedProblemPost;
import br.ufscar.connect.models.Report;
import br.ufscar.connect.models.User;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class ProfileActivity extends Activity {

    //Declarando variaveis
    RelativeLayout infosProfile;
    TextView tv_name;
    TextView tv_usertype;
    TextView tv_useremail;
    ImageView iv_profile_pic;
    ListView lv_my_publications;
    Context context;
    TextView tv_my_publications;
    ImageButton btn_edit_profile;
    TextView tv_username;
    String USER_NAME, USER_LASTNAME, USER_EMAIL, USER_TYPE, USER_USERNAME, USER_PHOTO, USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this;

        //----------------------------------------------------------------------------------------
        //Referenciando os objetos do XML
        iv_profile_pic = (ImageView) findViewById(R.id.iv_profile_picture);
        tv_username = (TextView) findViewById(R.id.et_username);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_usertype = (TextView) findViewById(R.id.tv_usertype);
        tv_useremail = (TextView) findViewById(R.id.tv_useremail);
        lv_my_publications = (ListView) findViewById(R.id.lv_my_publications);
        tv_my_publications = (TextView) findViewById(R.id.tv_titlemypublications);
        btn_edit_profile = (ImageButton) findViewById(R.id.btn_edit_profile);
        infosProfile = (RelativeLayout) findViewById(R.id.InfosProfile);

        //----------------------------------------------------------------------------------------
        //Recebe os dados do usuario de USER_PREFERENCES
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        USER_NAME = sharedPref.getString("name", "");  //"" is the default value.0
        USER_ID = sharedPref.getString("user_id", "");
        USER_EMAIL = sharedPref.getString("email", "");
        USER_LASTNAME = sharedPref.getString("lastname", "");
        USER_USERNAME = sharedPref.getString("username", "");
        USER_TYPE = sharedPref.getString("usertype", "");
        USER_PHOTO = sharedPref.getString("image_url", "");

        //----------------------------------------------------------------------------------------
        //Completa os objtos do XML com o conteudo adequado
        iv_profile_pic.setVisibility(View.VISIBLE);

        if (USER_PHOTO != null && !USER_PHOTO.equals("")) {
            Picasso.Builder builder = new Picasso.Builder(ProfileActivity.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(USER_PHOTO).transform(new CropCircleTransformation()).into(iv_profile_pic);
        }
        else
            Picasso.with(this).load(R.drawable.usericon2).into(iv_profile_pic);

        tv_name.setText(USER_NAME + " " + USER_LASTNAME); //completa o TextView com o nome COMPLETO do usuario
        tv_usertype.setText(USER_TYPE); //completa o TextView com o tipo do usuario
        tv_useremail.setText(USER_EMAIL); //completa o TextView com o email do usuario
        tv_username.setText(USER_USERNAME);

        final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Carregando minhas publicações");
        progressDialog.setCancelable(false);
        progressDialog.show();

        AsyncTask task = new AsyncTask() {
            final ConnectUFSCarApi api = ((ConnectApplication) getApplication()).getApi();
            List<FeedProblemPost> feedProblemPostList = new ArrayList<>();

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    List<Report> reportList = api.reportList().execute().body();
                    User user = api.getUser(USER_ID).execute().body();

                    for (Report r : reportList) {
                        if (r.getUser_id().equals(USER_ID)) {
                            FeedProblemPost post = new FeedProblemPost(user.getName(), user.getUser_type(),
                                    r.getDate(), r.getProblemAddress(), r.getProblemCategory(), r.getProblemDescription(),
                                    r.getProblemPhoto(), user.getUser_photo());
                            feedProblemPostList.add(post);
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
                lv_my_publications.setAdapter(new FeedProblemListAdapter(ProfileActivity.this, feedProblemPostList));
                progressDialog.hide();
            }
        };

        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    public void onButtonClick(View v) {

        //Clicou em EDIT PROFILE  -  redireciona para tela de edicao de perfil
        if (v.getId() == R.id.btn_edit_profile) {

            Intent i = new Intent(this, EditProfileActivity.class);
            startActivity(i);
        }

        //Clicou em /\
        if (v.getId() == R.id.btn_expand) {

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_my_publications.getLayoutParams(); //Pega os parametros do objeto XML
            RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) lv_my_publications.getLayoutParams(); //Pega os parametros do objeto XML
            RelativeLayout.MarginLayoutParams margins = (RelativeLayout.MarginLayoutParams) tv_my_publications.getLayoutParams();

            //Se as INFOS do USUARIO estiverem VISIVEIS, as torna INVISIVEIS(GONE), caso contrario, torna-as VISIVEIS
            if (tv_name.getVisibility() == View.VISIBLE) {
                v.setBackgroundResource(R.drawable.expanddown);
                iv_profile_pic.setVisibility(View.GONE);
                tv_useremail.setVisibility(View.GONE);
                tv_name.setVisibility(View.GONE);
                tv_usertype.setVisibility(View.GONE);
                btn_edit_profile.setVisibility(View.GONE);
                tv_username.setVisibility(View.GONE);
                infosProfile.setVisibility(View.GONE);

            } else {
                if (tv_name.getVisibility() == View.GONE) {
                    v.setBackgroundResource(R.drawable.expandup);
                    iv_profile_pic.setVisibility(View.VISIBLE);
                    tv_useremail.setVisibility(View.VISIBLE);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_usertype.setVisibility(View.VISIBLE);
                    btn_edit_profile.setVisibility(View.VISIBLE);
                    tv_username.setVisibility(View.VISIBLE);
                    infosProfile.setVisibility(View.VISIBLE);
                    margins.setMargins(0, 5, 0, 0);
                    params.addRule(RelativeLayout.BELOW, R.id.InfosProfile);
                    params2.addRule(RelativeLayout.BELOW, R.id.tv_titlemypublications);
                }
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

    public void backButtonHandler() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
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
        Intent i = new Intent(ProfileActivity.this, getParent().getClass());
        startActivity(i);
        this.finish(); //termina a atividade liberando memória
        */
    }
}
