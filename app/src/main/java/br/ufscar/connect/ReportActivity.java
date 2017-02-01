package br.ufscar.connect;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.ufscar.connect.Models.Report;
import br.ufscar.connect.interfaces.ConnectUFSCarApi;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class ReportActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    String USER_ID, PROBLEM_PHOTO;

    //DECLARANDO VARIAVEIS
    //estaticas
    public static final int CAMERA_REQUEST = 0;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 2;
    public static final int REQUEST_PERMISSION_SETTING = 3;

    Bitmap cameraImage, cameraImageResized;

    private ImageView iv_foto;
    ImageButton btn_mycoordinates;
    EditText et_address;
    Spinner et_category;
    EditText et_description;

    String imagePath, imageURL;
    Uri imageUri;

    GoogleApiClient mGoogleApiClient;
    ConnectUFSCarApi api;
    Cloudinary mobileCloudinary;

    Map resultMap = new Map() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object o) {
            return false;
        }

        @Override
        public boolean containsValue(Object o) {
            return false;
        }

        @Override
        public Object get(Object o) {
            return null;
        }

        @Override
        public Object put(Object o, Object o2) {
            return null;
        }

        @Override
        public Object remove(Object o) {
            return null;
        }

        @Override
        public void putAll(Map map) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public Set keySet() {
            return null;
        }

        @NonNull
        @Override
        public Collection values() {
            return null;
        }

        @NonNull
        @Override
        public Set<Entry> entrySet() {
            return null;
        }
    }, config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

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

    public void onResume() {
        super.onResume();

        //Recebe os dados do usuario de USER_PREFERENCES
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        PROBLEM_PHOTO = sharedPref.getString("problem_photo", "");

        //Completa os objtos do XML com o conteudo adequado
        assert PROBLEM_PHOTO != null;
        //Toast.makeText(getApplicationContext(), USER_PHOTO, Toast.LENGTH_SHORT).show();
        if (PROBLEM_PHOTO.contentEquals("")) {
            //Do nothing
        } else {
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(PROBLEM_PHOTO)
                    .fit()
                    .centerInside()
                    .into(iv_foto);
            iv_foto.setVisibility(View.VISIBLE);
        }

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

            //Recebendo informacoes e textos digitados pelo usuario
            Report report = new Report();
            report.setProblemAddress(et_address.getText() + "");
            int position = et_category.getSelectedItemPosition(); //recebe a posiçao do item selecionado no spinner (int)
            report.setProblemCategory(et_category.getItemAtPosition(position).toString()); //recebe o item selecionado (string)
            report.setProblemDescription(et_description.getText() + "");
            report.setProblemPhoto(imageURL);
            String date = new Date(System.currentTimeMillis()).toString();
            report.setDate(date);

            //Recebe os dados do usuario de USER_PREFERENCES
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
            USER_ID = sharedPref.getString("user_id", "");

            //--------------------------------------------------------------------------------------------
            //Tratando erros
            if (report.getProblemCategory().contentEquals("Selecione a categoria")) {
                et_category.setPrompt("Por favor, selecione a CATEGORIA do problema.");
                return;
            }

            if (report.getProblemDescription().contentEquals("")) {
                et_description.setError("Por favor, preencha a DESCRIÇÃO do problema.");
                return;
            }

            if (report.getProblemAddress().length() == 0) {
                et_description.setError("Por favor, informe o ENDEREÇO do problema.");
                return;
            }

            if (iv_foto.getVisibility() == View.INVISIBLE) {
                Toast.makeText(getApplicationContext(), "Por favor, tire uma FOTO para anexar ao problema.", Toast.LENGTH_LONG).show();
                return;
            }

            //-------------------------------------------------------------------------------------
            //Enviando textos ao servidor utilizando Retrofit
            api.reportCreate(report).enqueue(new Callback<Report>() {

                @Override
                public void onResponse(Response<Report> response, Retrofit retrofit) {
                    //Se o servidor retornou com sucesso
                    if (response.isSuccess()) {

                        // Exibe mensagem de sucesso
                        Toast.makeText(getApplicationContext(), "Problema reportado com sucesso!", Toast.LENGTH_LONG).show();

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
                imageUri = data.getData();

                //Checks for STORAGE PERMISSION, if the app doesn't have permission, asks the user for it
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        //Storage Permission already granted
                        //Imagem capturada salva na variavel imageUri(Uri)
                        imageUri = data.getData();
                        cameraImage = (Bitmap) data.getExtras().get("data");
                        //Fazendo upload da imagem para Cloudinary
                        new upToCloud().execute();

                        Picasso.Builder builder = new Picasso.Builder(this);
                        builder.listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                exception.printStackTrace();
                            }
                        });
                        builder.build().load(imageUri)
                                .fit()
                                .centerInside()
                                .into(iv_foto);
                        iv_foto.setVisibility(View.VISIBLE);


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
                    builder.build().load(imageUri)
                            .fit()
                            .centerInside()
                            .into(iv_foto);

                }

                //Enviando a foto para o servidor Cloudinary e salvando o URL na variavel imageURL para salvar no BD
                new upToCloud().execute();

                Picasso.Builder builder = new Picasso.Builder(this);
                builder.listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        exception.printStackTrace();
                    }
                });
                builder.build().load(imageURL)
                        .fit()
                        .centerInside()
                        .into(iv_foto);
                iv_foto.setVisibility(View.VISIBLE);

            }
        }

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        finish();
    }

    //Envia as fotos no formato Uri para o servidor Cloudinary
    private class upToCloud extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(ReportActivity.this);

        @Override
        protected Void doInBackground(Void... params) {

            //Primeiro reduzimos o tamanho da imagem obtida da camera
            cameraImageResized = getResizedBitmap(cameraImage, 600);
            //Depois, pegamos o 'path' dessa imagem Bitmap
            imageUri = getImageUri(ReportActivity.this, cameraImageResized);
            //Por ultimo, pegamos o 'path' real da imagem Uri para passar ao CLoudinary
            imagePath = getPath(imageUri);


            //Iniciando upload da imagem usando Couldinary
            config = new HashMap();
            config.put("cloud_name", "cloud-connectufscar");
            config.put("api_key", "726282638648912");
            config.put("api_secret", "eLEY62xvmZIgIXeBZYGLdLXKFgE");
            mobileCloudinary = new Cloudinary(config);

            try {
                resultMap = mobileCloudinary.uploader().upload(imagePath, ObjectUtils.emptyMap());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            //set message of the dialog
            asyncDialog.setMessage("Carregando imagem...");
            asyncDialog.setCancelable(false);
            asyncDialog.show();

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {

            imageURL = (String) resultMap.get("url");

            //hide the dialog
            if (asyncDialog.isShowing())
                asyncDialog.dismiss();

            //Salva os dados do usuario em SharedPreferences para uso em onResume()
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("problem_photo", imageURL).apply();

            super.onPostExecute(result);
        }
    }


    //---------------------------------------------------------------------------------------------
    // FUNCOES PARA MANIPULACAO DE IMAGENS   ------------------------------------------------------

    //Retorna o 'path' da imagem Uri em String
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //Retorna o 'path' de uma imagem Bitmap em Uri
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 0, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //Reduz o tamanho de uma imagem Bitmap
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }



}
