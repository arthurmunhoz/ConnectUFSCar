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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.ufscar.connect.Models.User;
import br.ufscar.connect.interfaces.ConnectUFSCarApi;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


@SuppressWarnings("ALL")
public class SignUpActivity extends Activity {

    //---------------------------------------------
    // Declarando variaveis
    Spinner et_user_type, spinner; //spinner para selecao de curso
    User user = new User();
    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 0;
    static final int REQUEST_CAMERA = 1;
    static final int SELECT_FILE = 2;
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
    Cloudinary mobileCloudinary;
    Bitmap cameraImage, cameraImageResized;
    String user_type, username, name, last_name, email, password, password_conf, image_url, imageURL, imagePath;
    ImageView iv_profile_pic;
    TextView tv_changePicture;
    EditText et_username, et_name, et_last_name, et_email, et_password, et_password_conf;
    ArrayAdapter<CharSequence> adapter; //adapter para spinner
    Uri imageUri = null;
    private ConnectUFSCarApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Inicializando API
        api = ConnectUFSCarApi.RETROFIT.create(ConnectUFSCarApi.class);

        //Configuracoes do spinner para selecao de curso
        et_user_type = (Spinner) findViewById(R.id.spinnerTipos);
        adapter = ArrayAdapter.createFromResource(this, R.array.tipos_usuario, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et_user_type.setAdapter(adapter);

        //---------------------------------------------------------
        //Refereciando os objetos do XML
        et_username = (EditText) findViewById(R.id.tv_username);

        et_name = (EditText) findViewById(R.id.et_name);

        et_last_name = (EditText) findViewById(R.id.et_last_name);

        et_email = (EditText) findViewById(R.id.et_username);

        et_password = (EditText) findViewById(R.id.et_password);

        et_password_conf = (EditText) findViewById(R.id.et_password_conf);

        iv_profile_pic = (ImageView) findViewById(R.id.iv_profile_picture);
        iv_profile_pic.setImageResource(R.drawable.usericon2);

    }

    public void onButtonClick(View v) {

        //--------------------------------------------------------------------
        //Clicou no botão ALTERAR FOTO ou na imagem de perfil

        if (v.getId() == R.id.iv_profile_picture || v.getId() == R.id.tv_changePicture) {

            //Checks for STORAGE PERMISSION, if the app doesn't have permission, asks the user for it
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    //Storage Permission already granted, do what we need
                    final CharSequence[] items = {"Tire uma foto", "Escolha da galeria", "Cancelar"};
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setTitle("Altere sua foto!");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {

                            if (items[item].equals("Tire uma foto")) {

                                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(i, REQUEST_CAMERA);

                            } else if (items[item].equals("Escolha da galeria")) {

                                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, SELECT_FILE);

                            } else if (items[item].equals("Cancelar")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();

                } else {

                    //Request STORAGE Permission
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
                                        ActivityCompat.requestPermissions(SignUpActivity.this,
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
                final CharSequence[] items = {"Tire uma foto", "Escolha da galeria", "Cancelar"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("Altere sua foto!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Tire uma foto")) {

                            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(i, REQUEST_CAMERA);

                        } else if (items[item].equals("Escolha da galeria")) {

                            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, SELECT_FILE);

                        } else if (items[item].equals("Cancelar")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }

            return;
        }

        //Se o usuario clicar no botao CONCLUIDO, exibe-se uma mensagem e retorna para a activity LoginActivity
        if (v.getId() == R.id.btn_concluido) {

            //Recebendo texto inserido pelo usuario nos campos xml
            user.setUser_type(et_user_type.getSelectedItem().toString());
            user.setUsername(et_username.getText() + "");
            user.setLast_name(et_last_name.getText() + "");
            user.setName(et_name.getText() + "");
            user.setEmail(et_email.getText() + "");
            user.setPassword(et_password.getText() + "");
            password_conf = et_password_conf.getText() + "";
            user.setUser_photo(imageURL);

            //-------------------- VERIFICACOES DE ERROS ----------------------

            //Se o usuario nao digitar nada em em algum dos campos, recebe uma mensagem de erro
            if (user.getUsername().length() == 0) {
                et_username.setError("Digite um Nome de Usuário");
                return;
            }
            if (user.getName().length() == 0) {
                et_name.setError("Digite seu nome");
                return;
            }
            if (user.getLast_name().length() == 0) {
                et_last_name.setError("Digite seu sobrenome");
                return;
            }
            if (user.getPassword().length() == 0) {
                et_password.setError("Digite uma senha");
                return;
            }
            if (user.getEmail().length() == 0) {
                et_email.setError("Digite seu e-mail");
                return;
            }
            if (et_password_conf.getText().length() == 0) {
                et_password_conf.setError("Confirme sua senha");
                return;
            }

            //Se o usuario nao selecionar o tipo de usuario, exibe mensagem de erro
            String tipo = et_user_type.getSelectedItem().toString();
            if (tipo.contentEquals("Selecione o tipo de usuário")) {
                Toast.makeText(getApplicationContext(), "Por favor, selecione um tipo de usuário.", Toast.LENGTH_LONG).show();
                return;
            }

            //Se Senha e a confirmacao de senha nao forem iguais, exibe mensagem ao usuario
            if (password_conf.equals(user.getPassword()) == false) {
                Toast.makeText(getApplicationContext(), "SENHA e CONFIRMAÇÃO DE SENHA não conferem, por favor verifique.", Toast.LENGTH_LONG).show();
                return;
            }
            // end of VERIFICACAO DE ERROS

            //Salva os dados do usuario em SharedPreferences para uso em outras activites
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("image_url", imageURL).apply();

            //--------------- REALIZANDO A POST REQUEST PARA SALVAR OS DADOS DO USUARIO NO BD ---------------
            // Prepare the HTTP request
            api.usersCreate(user).enqueue(new Callback<User>() {

                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {
                    //Se o servidor retornou com sucesso
                    if (response.isSuccess()) {

                        // Exibe mensagem de sucesso
                        Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();

                        //Inicia o aplicativo
                        Intent i = new Intent(SignUpActivity.this, MenuActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        try {
                            String error = response.errorBody().string();
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            Log.e("ERROR TAG", e.getMessage(), e);
                        }

                        Toast.makeText(getApplicationContext(), "Falha de conexão: !response.isSuccess", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ERROR_FAILURE", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
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
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Aqui trata-se a escolha do usuário quando o Dialog para mudança de foto de perfil é apresentado:
        //Se escolheu tirar uma foto(REQUEST_CAMERA), tira a foto: foto foi aprovada? salva no BD e altera a UI
        //Se escolheu pegar uma imagem da galeria, a galeria é apresentada e a foto escolhida é atualizada na UI
        if (resultCode != RESULT_CANCELED) {
            if (resultCode == RESULT_OK) {

                switch (requestCode) {

                    case REQUEST_CAMERA:

                        //Checks for STORAGE PERMISSION, if the app doesn't have permission, asks the user for it
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                                //Storage Permission already granted, do what we need

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
                                        .resize(iv_profile_pic.getMaxWidth(), iv_profile_pic.getMaxHeight())
                                        .transform(new CropCircleTransformation())
                                        .into(iv_profile_pic);

                            } else {

                                //Request STORAGE Permission
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
                                                    ActivityCompat.requestPermissions(SignUpActivity.this,
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
                            iv_profile_pic.setBackground(null);
                            iv_profile_pic.setVisibility(View.VISIBLE);
                            Picasso.Builder builder = new Picasso.Builder(this);
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    exception.printStackTrace();
                                }
                            });
                            builder.build().load(imageUri)
                                    .resize(iv_profile_pic.getMaxWidth(), iv_profile_pic.getMaxHeight())
                                    .transform(new CropCircleTransformation())
                                    .into(iv_profile_pic);
                        }

                    case SELECT_FILE:

                        imageUri = data.getData();

                        new upToCloud().execute();

                        //Colocando e ajustando a imagem na UI
                        iv_profile_pic.setVisibility(View.VISIBLE);

                        Picasso.Builder builder = new Picasso.Builder(SignUpActivity.this);
                        builder.listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                exception.printStackTrace();
                            }
                        });

                        builder.build().load(imageUri).transform(new CropCircleTransformation()).into(iv_profile_pic);

                }//end of switch
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        if (imageUri != null) {

            //Colocando e ajustando a imagem na UI
            iv_profile_pic.setVisibility(View.VISIBLE);

            Picasso.Builder builder = new Picasso.Builder(this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });

            builder.build().load(imageUri).transform(new CropCircleTransformation()).into(iv_profile_pic);
        }

    }

    //Envia as fotos no formato Uri para o servidor Cloudinary
    private class upToCloud extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(SignUpActivity.this);

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

            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... params) {

            if (cameraImage != null) {

                //Primeiro reduzimos o tamanho da imagem obtida da camera
                cameraImageResized = getResizedBitmap(cameraImage, 500);
                //Depois, pegamos o 'path' dessa imagem Bitmap
                imageUri = getImageUri(SignUpActivity.this, cameraImageResized);
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
            } else {

                //Pegamos os 'path' real da imagem Uri para passar ao CLoudinary
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