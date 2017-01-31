package br.ufscar.connect;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


@SuppressWarnings("deprecation")
public class MenuActivity extends TabActivity {

    String USER_NAME, USER_LASTNAME, USER_EMAIL, USER_TYPE, USER_USERNAME, USER_PHOTO, USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Context c = this;


        //----------------------------------------------------------
        //Pega o id da tabhost da view
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setHorizontalScrollBarEnabled(true); //habilita scroll horizontal nas abas (?)

        //----------------------------------------------------------
        //Cria as abas da tabhost (ordem de implica na ordem de visualização no app)
        TabHost.TabSpec tabAdd = tabHost.newTabSpec("TabAdd");
        TabHost.TabSpec tabFeed = tabHost.newTabSpec("TabFeed");
        TabHost.TabSpec tabMapa = tabHost.newTabSpec("TabMapa");
        TabHost.TabSpec tabPerfil = tabHost.newTabSpec("TabPerfil");

        //---------------
        //Adicionando titulos nas TABS da tabHost
        // e definindo qual atividade realiza, nesse caso FEED, MAPA e PERFIL abrem
        // as respectivas activities, e + infla um menu que permite o usuario publicar
        tabAdd.setIndicator("+");
        tabAdd.setContent(new Intent(this, AddPublicationActivity.class));


        tabFeed.setIndicator("FEED");
        tabFeed.setContent(new Intent(this, FeedActivity.class));
        //tabFeed.setIndicator("", getResources().getDrawable(R.drawable.tabfeed));

        tabMapa.setIndicator("MAPA");
        tabMapa.setContent(new Intent(this, MapsActivity.class));

        tabPerfil.setIndicator("PERFIL");
        tabPerfil.setContent(new Intent(this, ProfileActivity.class));

        //---------------
        //Adicionando as tabs criadas
        tabHost.addTab(tabFeed);
        tabHost.addTab(tabMapa);
        tabHost.addTab(tabPerfil);
        tabHost.addTab(tabAdd);

        //---------------
        //define a HEIGHT da tabHost
        for (int i = 0; i < tabHost.getTabWidget().getTabCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (50 * this.getResources().getDisplayMetrics().density);
        }

        //---------------
        //define a cor do texto da tabHost
        for (int i = 0; i < tabHost.getTabWidget().getTabCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
        }

        //---------------
        //define o tamanho da fonte da tabHost
        final TabWidget tw = (TabWidget) tabHost.findViewById(android.R.id.tabs);
        for (int i = 0; i < tw.getChildCount(); ++i) {
            final View tabView = tw.getChildTabViewAt(i);
            final TextView tv = (TextView) tabView.findViewById(android.R.id.title);
            tv.setTextSize(16);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);
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
}
