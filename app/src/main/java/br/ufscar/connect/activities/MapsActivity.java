package br.ufscar.connect.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.ufscar.connect.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    //Declaring variables
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Spinner spinnerEspaco;
    ArrayAdapter<CharSequence> adapter;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Inicializando Spinner
        spinnerEspaco = (Spinner) findViewById(R.id.spinnerEspaco);

        adapter = ArrayAdapter.createFromResource(this, R.array.lista_espacos2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEspaco.setAdapter(adapter);
        spinnerEspaco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = spinnerEspaco.getSelectedItem().toString();

                //Quando o usuario seleciona um local, um MARKER é adicionado ao local, a camera foca no local e dá zoom in
                //Clicou no A.T. 01        OK
                if (item.matches("A.T. 01")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.987990, -47.879085)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.987990, -47.879085)).title("A.T. 01")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou no A.T. 02        OK
                if (item.matches("A.T. 02")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.987686, -47.879031)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.987686, -47.879031)).title("A.T. 02")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }


                //Clicou no A.T. 04     OK
                if (item.matches("A.T. 04")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9823059, -47.8839839)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9823059, -47.8839839)).title("A.T. 04")).showInfoWindow();
                    //Toast.makeText(getApplicationContext(), "Clique no marcador para mais opcoes", Toast.LENGTH_LONG).show();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no A.T. 05     OK
                if (item.matches("A.T. 05")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.982192, -47.879369)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.982192, -47.879369)).title("A.T. 05")).showInfoWindow();
                    //Toast.makeText(getApplicationContext(), "Clique no marcador para mais opcoes", Toast.LENGTH_LONG).show();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no A.T. 06     OK
                if (item.matches("A.T. 06")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9784358, -47.8811766)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9784358, -47.8811766)).title("A.T. 06")).showInfoWindow();
                    //Toast.makeText(getApplicationContext(), "Clique no marcador para mais opcoes", Toast.LENGTH_LONG).show();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no A.T. 07   OK
                if (item.matches("A.T. 07")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9819903, -47.878299)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9819903, -47.878299)).title("A.T. 07")).showInfoWindow();
                    //Toast.makeText(getApplicationContext(), "Clique no marcador para mais opcoes", Toast.LENGTH_LONG).show();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no A.T. 08     OK
                if (item.matches("A.T. 08")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.988423, -47.879261)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.988423, -47.879261)).title("A.T. 08")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no A.T. 09     OK
                if (item.matches("A.T. 09")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.978941, -47.8811861)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.978941, -47.8811861)).title("A.T. 09")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no A.T. 10     OK
                if (item.matches("A.T. 10")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9826062, -47.8845855)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9826062, -47.8845855)).title("A.T. 10")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no Depto. de Computacao      OK
                if (item.matches("Depto. de Computação")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.979720, -47.880566)); //define uma posicao central pro DEPTO DE COMPUTACAO
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.979720, -47.880566)).title("Depto. de Computação")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no Depto. de Fisica        OK
                if (item.matches("Depto. de Física")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.983936, -47.883541)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.983936, -47.883541)).title("Depto. de Física")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou no Depto. de Medicina        OK
                if (item.matches("Depto. de Medicina")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9782355, -47.8804691)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9782355, -47.8804691)).title("Depto. de Medicina")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no Depto. de Eng. Civil        OK
                if (item.matches("Depto. de Eng. Civil")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9816384, -47.8801107)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9816384, -47.8801107)).title("Depto. de Engenharia Civil")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }


                //Clicou no Depto. de Letras        OK
                if (item.matches("Depto. de Letras")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9888705, -47.8806152)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9888705, -47.8806152)).title("Depto. de Letras")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no Depto. de Educacao Fisica       OK
                if (item.matches("Depto. de Educação Física")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9877987, -47.8820589)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9877987, -47.8820589)).title("Depto. de Educação Física")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no Depto. de Ciencias Sociais      OK
                if (item.matches("Depto. de Ciências Sociais")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9888179, -47.8829204)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9888179, -47.8829204)).title("Depto. de Ciências Sociais")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou em Reitoria        OK
                if (item.matches("Reitoria")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9893248, -47.8829176)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9893248, -47.8829176)).title("Reitoria")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou em Pro-Reitoria de Graduacao - ProGrad       OK
                if (item.matches("Pró-Reitoria de Graduação - ProGrad")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.988710, -47.882728)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.988710, -47.882728)).title("Pró-Reitoria de Graduação")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou em Restaurante Universitario      OK
                if (item.matches("Restaurante Universitário - RU")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9848281, -47.8826761)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9848281, -47.8826761)).title("Restaurante Universitário - RU")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou em Depto. de Biotecnologia     OK
                if (item.matches("Depto. de Biotecnologia")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9830267, -47.8772156)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9830267, -47.8772156)).title("Depto. de Biotecnologia")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou em Depto. de Eng. Eletrica     OK
                if (item.matches("Depto. de Eng. Elétrica")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9812875, -47.8784241)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9812875, -47.8784241)).title("Depto. de Eng. Elétrica")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no Depto. de Eng. de Materiais         OK
                if ("Depto. de Eng. de Materiais".matches(item)) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9819239, -47.8807182)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9819239, -47.8807182)).title("Depto. de Eng. de Materiais")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no Depto. de Eng. de Producao         OK
                if (item.matches("Depto. de Eng. de Produção")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.982204, -47.883178)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.982204, -47.883178)).title("Depto. de Eng. de Produção")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no Depto. de Eng. Quimica         OK
                if (item.matches("Depto. de Eng. Química")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9818931, -47.8823087)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9818931, -47.8823087)).title("Departamento de Eng. Química")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou em Observatorio         OK
                if (item.matches("Observatório")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9798503, -47.8792199)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9798503, -47.8792199)).title("Observatório")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou no Depto. de Matematica        OK
                if (item.matches("Depto. de Matemática")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9796895, -47.8812445)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9796895, -47.8812445)).title("Departamento de Matemática")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou em Unidade SaudeEscola        OK
                if (item.matches("Unidade Saúde-Escola - USE")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9778922, -47.8823866)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9778922, -47.8823866)).title("Unidade Saúde-Escola - USE")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou em Depto. de Fisioterapia      OK
                if (item.matches("Depto. de Fisioterapia")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9775376, -47.8802781)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9775376, -47.8802781)).title("Departamento de Fisioterapia")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou em Depto. de Terapia Ocupacional       OK
                if (item.matches("Depto. de Terapia Ocupacional")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9774706, -47.880948)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9774706, -47.880948)).title("Depto. de Terapia Ocupacional")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Radio UFSCar        OK
                if (item.matches("Rádio UFSCar")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9809175, -47.8831147)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9809175, -47.8831147)).title("Rádio UFSCar")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Anfiteatro Bento Prado       OK
                if (item.matches("Anfiteatro Bento Prado")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9836441, -47.8816132)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9836441, -47.8816132)).title("Anfiteatro Bento Prado")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Museu de Historia Natural       OK
                if (item.matches("Museu de História Natural")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9831794, -47.8784438)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9831794, -47.8784438)).title("Museu de História Natural")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Depto. de Estatistica       OK
                if (item.matches("Depto. de Estatística")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9820321, -47.883813)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9820321, -47.883813)).title("Departamento de Estatística")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Depto. de Quimica       OK
                if (item.matches("Depto. de Química")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9841362, -47.8812505)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9841362, -47.8812505)).title("Departamento de Química")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Depto. de Botanica       OK
                if (item.matches("Depto. de Botânica")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.983312, -47.8795042)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.983312, -47.8795042)).title("Departamento de Botânica")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Biblioteca Comunitaria       OK
                if (item.matches("Biblioteca Comunitária - BCo")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.982984, -47.8828771)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.982984, -47.8828771)).title("Biblioteca Comunitária - BCo")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Depto. de Enfermagem       OK
                if (item.matches("Depto. de Enfermagem")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.978163, -47.8811054)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.978163, -47.8811054)).title("Departamento de Enfermagem")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Secretaria de Informatica       OK
                if (item.matches("Secretaria de Informática - SIn")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9839606, -47.8825919)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9839606, -47.8825919)).title("Secretaria de Informática - SIn")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Alojamento Estudantil       OK
                if (item.matches("Alojamento Estudantil")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9879036, -47.8778821)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9879036, -47.8778821)).title("Alojamento Estudantil")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Piscina       OK
                if (item.matches("Piscina")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9872738, -47.8826499)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9872738, -47.8826499)).title("Piscina")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Quadras Poliesportivas       OK
                if (item.matches("Quadras Poliesportivas")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9869598, -47.8804443)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9869598, -47.8804443)).title("Quadras Poliesportivas")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();
                }

                //Clicou em Ginasio Poliesportivo     OK
                if (item.matches("Ginásio Poliesportivo")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.987336, -47.880869)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.987336, -47.880869)).title("Ginásio Poliesportivo")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Palquinho do DCE       OK
                if (item.matches("Palquinho do DCE")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.987978, -47.881080)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.987978, -47.881080)).title("Palquinho do DCE")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Atletica UFSCar       OK
                if (item.matches("Atlética UFSCar")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9880037, -47.8813941)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9880037, -47.8813941)).title("Atlética UFSCar")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Unidade de Atendimento a Crianca       OK
                if (item.matches("Unidade de Atendimento a Criança")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.9897167, -47.8813113)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.9897167, -47.8813113)).title("Unidade de Atendimento a Criança")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Praca da Bandeira       OK
                if (item.matches("Praça da Bandeira")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.989393, -47.882107)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.989393, -47.882107)).title("Praça da Bandeira")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Portaria Norte
                if (item.matches("Portaria Norte")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.980251, -47.886049)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.980251, -47.886049)).title("Portaria Norte")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }

                //Clicou em Portaria Sul
                if (item.matches("Portaria Sul")) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.990901, -47.883414)); //define uma posicao central
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(18); //define o zoom para a posicao
                    mMap.moveCamera(center); //move a camera para a localizacao desejada
                    mMap.animateCamera(zoom); //zoom in
                    mMap.addMarker(new MarkerOptions().position(new LatLng(-21.990901, -47.883414)).title("Portaria Sul")).showInfoWindow();

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_maps_layout;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(view2);
                    toast.show();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;

        if (mMap != null) {

            //Initialize Google Play Services
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    //Location Permission already granted
                    buildGoogleApiClient();
                    setUpMap(mMap);

                } else {

                    //Request Location Permission
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        new AlertDialog.Builder(this)
                                .setTitle("Permissão necessária")
                                .setMessage("Habilite a permissão de LOCAL em:               Permissões > Seu local")
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Se o usuario cancelar a autorizacao de permissao, visualiza o mapa sem o botao MyLocation
                                        setUpMap(mMap);
                                    }
                                })

                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public static final int REQUEST_PERMISSION_SETTING = 1;

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Prompt the user once explanation has been shown
                                        ActivityCompat.requestPermissions(MapsActivity.this,
                                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                                MY_PERMISSIONS_REQUEST_LOCATION);

                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);

                                    }
                                })
                                .create()
                                .show();

                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                    }

                }
            } else {
                buildGoogleApiClient();
                setUpMap(mMap);
                mMap.setMyLocationEnabled(true);
            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    //Configurando TOAST PERSONALIZADO
                    LayoutInflater layoutInflater = getLayoutInflater();
                    int layout = R.layout.toast_marker_options;

                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.toast_layout_root);
                    View view2 = layoutInflater.inflate(layout, viewGroup);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(view2);
                    toast.show();

                    return false;
                }
            });

        }//endif map != null
    }

    public void setUpMap(final GoogleMap mMap) {

        //Setando a posicao inicial do mapa
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(-21.984647, -47.8814938)); //define uma posicao central pro mapa
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15); //define o zoom para a posicao
        mMap.moveCamera(center); //move a camera para a localizacao desejada
        mMap.animateCamera(zoom); //zoom in\
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        /*
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng loc = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                return true;
            }
        });
        */

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.

                    if (mGoogleApiClient == null) {
                        buildGoogleApiClient();
                    }

                    setUpMap(mMap);
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permissão negada", Toast.LENGTH_LONG).show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResume() {
        super.onResume();

        //Inicializando Mapa
        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();

    }

    public void backButtonHandler() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapsActivity.this);
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
        Intent i = new Intent(MapsActivity.this, MenuActivity.class);
        startActivity(i);
        finish(); //termina a atividade liberando memória
        */

    }


}
