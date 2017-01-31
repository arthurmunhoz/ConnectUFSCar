package br.ufscar.connect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class ProfileActivity extends Activity {

    //Declarando variaveis
    View cv_separator;
    TextView tv_name;
    TextView tv_usertype;
    TextView tv_useremail;
    ImageView iv_profile_pic;
    ImageView iv_photo;
    ListView lv_my_publications;
    Context context;
    TextView tv_my_publications;
    Button btn_logout;
    ImageButton btn_edit_profile;
    TextView tv_username;
    String USER_NAME, USER_LASTNAME, USER_EMAIL, USER_TYPE, USER_USERNAME, USER_PHOTO, USER_ID;

    //Definindo arrays com os dados das publicações
    /*Var. para Profile Pic.*/  //public int profile_picture = ;
    /*Array de Photos*/         public static int[] my_publication_photo_list = {R.drawable.danosgramado, R.drawable.vazamentoagua, R.drawable.redeeletrica};
    /*Array de Types*/          public static String[] my_publication_type_list = {"Danos ao Patrimônio", "Vazamento D'água", "Rede Eletrica"};
    /*Array de Addresses*/      public String[] my_publication_address_list = {"Rua da Praça da Bandeira, 1500", "Rua Biblioteca Comunitária, 123", "Rua dos Ypês, 345"};
    /*Array de Descriptions*/   public String[] my_publication_description_list = {"Carros danificaram a grama da praça pois estavam estacionados em local indevido.", "Há um cano quebrado que está vazando muita água!", "Há um poste de energia danificado pela chuva que apresenta cabos desencapados e oferece perigo aos pedestres que ali passam."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this;

        //----------------------------------------------------------------------------------------
        //Referenciando os objetos do XML
        iv_profile_pic = (ImageView) findViewById(R.id.iv_profile_picture);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_usertype = (TextView) findViewById(R.id.tv_usertype);
        tv_useremail = (TextView) findViewById(R.id.tv_useremail);
        lv_my_publications = (ListView) findViewById(R.id.lv_my_publications);
        tv_my_publications = (TextView) findViewById(R.id.tv_titlemypublications);
        btn_edit_profile = (ImageButton) findViewById(R.id.btn_edit_profile);

        //----------------------------------------------------------------------------------------
        //Recebe os dados do usuario de USER_PREFERENCES
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        USER_NAME = sharedPref.getString("user_name", "");  //"" is the default value.0
        USER_ID = sharedPref.getString("user_id", "");
        USER_EMAIL = sharedPref.getString("user_email", "");
        USER_LASTNAME = sharedPref.getString("user_lastname", "");
        USER_USERNAME = sharedPref.getString("user_username", "");
        USER_TYPE = sharedPref.getString("usertype", "");
        USER_PHOTO = sharedPref.getString("image_url", "");

        //----------------------------------------------------------------------------------------
        //Completa os objtos do XML com o conteudo adequado
        assert USER_PHOTO != null;
        if (USER_PHOTO.contentEquals("")) {
            iv_profile_pic.setBackgroundResource(R.drawable.usericon2);
        } else {
            Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(iv_profile_pic);
        }

        tv_name.setText(USER_NAME + " " + USER_LASTNAME); //completa o TextView com o nome COMPLETO do usuario
        tv_usertype.setText(USER_TYPE); //completa o TextView com o tipo do usuario
        tv_useremail.setText(USER_EMAIL); //completa o TextView com o email do usuario
        tv_username.setText(USER_USERNAME);
        lv_my_publications.setAdapter(new ProfileActivityCustomAdapter(this, my_publication_type_list,
                my_publication_address_list, my_publication_description_list, my_publication_photo_list));

    }

    public void onResume() {
        super.onResume();

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
        assert USER_PHOTO != null;
        if (USER_PHOTO.contentEquals("")) {
            iv_profile_pic.setBackgroundResource(R.drawable.usericon2);
        } else {
            iv_profile_pic.setBackground(null);
            Picasso.with(this).load(Uri.parse(USER_PHOTO)).into(iv_profile_pic);
        }

        tv_name.setText(USER_NAME + " " + USER_LASTNAME); //completa o TextView com o nome COMPLETO do usuario
        tv_usertype.setText(USER_TYPE); //completa o TextView com o tipo do usuario
        tv_useremail.setText(USER_EMAIL); //completa o TextView com o email do usuario
        tv_username.setText(USER_USERNAME);
        lv_my_publications.setAdapter(new ProfileActivityCustomAdapter(this, my_publication_type_list,
                my_publication_address_list, my_publication_description_list, my_publication_photo_list));

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

            } else {
                if (tv_name.getVisibility() == View.GONE) {
                    v.setBackgroundResource(R.drawable.expandup);
                    iv_profile_pic.setVisibility(View.VISIBLE);
                    tv_useremail.setVisibility(View.VISIBLE);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_usertype.setVisibility(View.VISIBLE);
                    btn_edit_profile.setVisibility(View.VISIBLE);
                    tv_username.setVisibility(View.VISIBLE);
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
        return;
    }

    public void backButtonHandler() {

        Intent i = new Intent(ProfileActivity.this, MenuActivity.class);
        startActivity(i);
        finish(); //termina a atividade liberando memória
    }


}
