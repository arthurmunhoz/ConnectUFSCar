package br.ufscar.connect;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.ufscar.connect.Models.Report;
import br.ufscar.connect.interfaces.ConnectUFSCarApi;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class ReportActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    String USER_ID;

    //DECLARANDO VARIAVEIS
    //estaticas
    public static final int CAMERA_REQUEST = 0;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 2;
    public static final int REQUEST_PERMISSION_SETTING = 3;
    Bitmap cameraImage;

    private ImageView iv_foto;
    ImageButton btn_mycoordinates;
    EditText et_address;
    Spinner et_category;
    EditText et_description;

    Timestamp ts;
    String created_at;
    String address;
    String category;
    String description;
    String image_url;
    Uri picUri;

    GoogleApiClient mGoogleApiClient;
    ConnectUFSCarApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //-------------------------------------------------------
        //Initializing fragments from the XML
        iv_foto = (ImageView) findViewById(R.id.iv_foto);
        btn_mycoordinates = (ImageButton) findViewById(R.id.btn_mycoordinates);
        et_address = (EditText) findViewById(R.id.et_address);
        et_category = (Spinner) findViewById(R.id.spinnerCategoria);
        et_description = (EditText) findViewById(R.id.et_descricaoproblema);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //-------------------------------------------------------
        //Configuracoes do spinner para selecao de curso
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista_categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et_category.setAdapter(adapter);

        //-------------------------------------------------------
        //Inicializando API
        api = ConnectUFSCarApi.RETROFIT.create(ConnectUFSCarApi.class);

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void onButtonClick(View v) {

        //Se o usuario clicar no botao CONCLUIDO, exibe-se uma mensagem e retorna para a activity MenuActivity(que redireciona o usuario para FeedActivity)
        if (v.getId() == R.id.btn_concluido) {

            Intent i = new Intent(this, MenuActivity.class);

            //Recebendo informacoes e textos digitados pelo usuario
            address = et_address.getText() + "";
            int position = et_category.getSelectedItemPosition(); //recebe a posiçao do item selecionado no spinner (int)
            category = et_category.getItemAtPosition(position).toString(); //recebe o item selecionado (string)
            description = et_description.getText() + "";
            image_url = "";
            Date date = new Date(System.currentTimeMillis());
            created_at = date.toString();


            //Recebe os dados do usuario de USER_PREFERENCES
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
            USER_ID = sharedPref.getString("user_id", "");

            //--------------------------------------------------------------------------------------------
            //Tratando erros
            if (category.contentEquals("Selecione a categoria")) {
                et_category.setPrompt("Por favor, selecione a CATEGORIA do problema.");
                return;
            }

            if (description.contentEquals("")) {
                et_description.setError("Por favor, preencha a DESCRIÇÃO do problema.");
                return;
            }

            if (address.length() == 0) {
                et_description.setError("Por favor, informe o ENDEREÇO do problema.");
                return;
            }

            if (iv_foto.getVisibility() == View.INVISIBLE) {
                Toast.makeText(getApplicationContext(), "Por favor, tire uma FOTO para anexar ao problema.", Toast.LENGTH_LONG).show();
                return;
            }

            //-------------------------------------------------------------------------------------
            //Enviando textos ao servidor utilizando Retrofit
            api.reportCreate(address, category, description, USER_ID, image_url, created_at).enqueue(new Callback<Report>() {

                @Override
                public void onResponse(Response<Report> response, Retrofit retrofit) {
                    //Se o servidor retornou com sucesso
                    if (response.isSuccess()) {

                        // Exibe mensagem de sucesso
                        Toast.makeText(getApplicationContext(), "DENÚNCIA REALIZADA COM SUCESSO", Toast.LENGTH_LONG).show();

                        //-------------------------------------------------------------------------------------
                        //Enviando a foto tirada ao servidor Cloudinary
                        Map config = new HashMap();
                        config.put("cloud_name", "cloud-connectufscar");
                        config.put("api_key", "726282638648912");
                        config.put("api_secret", "eLEY62xvmZIgIXeBZYGLdLXKFgE");
                        Cloudinary mobileCloudinary = new Cloudinary(config);

                        try {
                            Map resultMap = mobileCloudinary.uploader().upload(response.body().getProblemPhoto(), ObjectUtils.emptyMap());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        //Volta a tela de Feed
                        Intent i = new Intent(ReportActivity.this, MenuActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        try {
                            String error = response.errorBody().string();
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            Log.e("ERROR TAG", e.getMessage(), e);
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ERROR_FAILURE", t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }

        //Se o usuario clicar no botao GET MY COORDINATES, coloca o valor das coordenadas da localizacao do usuario no TextView Endereço
        //Mas antes verifica se o sistema tem acesso a localizacao do usuario, se não tiver, pede atrves de um AlertDialog.
        if (v.getId() == R.id.btn_mycoordinates) {

            //Request Location Permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permissão de local necessária")
                        .setMessage("Habilite a permissão de local em               Permissões > Seu local")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ReportActivity.this,
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

            }

            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                et_address.setText(String.valueOf((mLastLocation.getLatitude())));
                et_address.append(", " + String.valueOf(mLastLocation.getLongitude()));
            }

        }

        //Clicando no botao TIRAR FOTO, o aplicativo abre a camera e permite ao usuario visualiza-la em miniatura SE ACEITA.

        if(v.getId() == R.id.btn_tirarfoto) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reportar, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //O usuario escolheu OK depois de tirar a foto? Se sim, armazena a foto e a coloca no ImageView invisivel e o torna visivel
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST){

                //Imagem capturada salva na variavel cameraImage (bitmap)
                cameraImage = (Bitmap) data.getExtras().get("data");  //agora ja temos a imagem da camera guardada em cameraImage
                picUri = data.getData();

                //Convertendo cameraImage em um InputStream
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                cameraImage.compress(Bitmap.CompressFormat.JPEG, 0, stream);
                InputStream is = new ByteArrayInputStream(stream.toByteArray());

                //Checks for STORAGE PERMISSION, if the app doesn't have permission, asks the user for it
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        //Storage Permission already granted
                        //Colocando e ajustando a imagem na UI
                        iv_foto.setBackground(null);
                        //iv_profile_pic.setImageBitmap(cameraImage);
                        iv_foto.setVisibility(View.VISIBLE);
                        Picasso.Builder builder = new Picasso.Builder(this);
                        builder.listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                exception.printStackTrace();
                            }
                        });
                        builder.build().load(picUri).resize(iv_foto.getMaxWidth(), iv_foto.getMaxHeight()).into(iv_foto);


                    } else {

                        //Request Location Permission
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

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
                                            Toast.makeText(getApplicationContext(), "Permissão de armazenamento negada", Toast.LENGTH_SHORT).show();
                                        }
                                    })

                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                        public static final int REQUEST_PERMISSION_SETTING = 1;

                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //Prompt the user once explanation has been shown
                                            ActivityCompat.requestPermissions(ReportActivity.this,
                                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                    MY_PERMISSIONS_READ_EXTERNAL_STORAGE);

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
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
                        }

                    }
                } else {
                    //Storage Permission already granted
                    //Colocando e ajustando a imagem na UI
                    iv_foto.setBackground(null);
                    //iv_profile_pic.setImageBitmap(cameraImage);
                    iv_foto.setVisibility(View.VISIBLE);
                    Picasso.Builder builder = new Picasso.Builder(this);
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                    builder.build().load(picUri).resize(iv_foto.getMaxWidth(), iv_foto.getMaxHeight()).into(iv_foto);

                }

            }
        }

    }

    //Transforma a imagem capturada pela câmera(bitmap) em um InpputStream para ser enviado
    //ao servido Cloudinary
    public static InputStream bitmapToInputStream(Bitmap bitmap) {
        int size = bitmap.getHeight() * bitmap.getRowBytes();
        ByteBuffer buffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(buffer);
        return new ByteArrayInputStream(buffer.array());
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        finish();
    }



}
