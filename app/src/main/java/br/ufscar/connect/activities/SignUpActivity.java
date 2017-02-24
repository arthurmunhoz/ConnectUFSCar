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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.ufscar.connect.ConnectApplication;
import br.ufscar.connect.models.User;
import br.ufscar.connect.R;
import br.ufscar.connect.interfaces.ConnectUFSCarApi;
import id.zelory.compressor.Compressor;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

//import com.android.volley.Response;


@SuppressWarnings("ALL")
public class SignUpActivity extends Activity implements View.OnFocusChangeListener {

    public static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 0;
    static final int REQUEST_CAMERA = 1;
    static final int SELECT_FILE = 2;
    //---------------------------------------------
    // Declarando variaveis
    ExifInterface exif;
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
    File imageFile;
    RequestQueue requestQueue;
    Spinner et_user_type, spinner; //spinner para selecao de curso
    StringRequest stringRequest;
    User user = new User();
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
        api = ((ConnectApplication) getApplication()).getApi();

        //Configuracoes do spinner para selecao de curso
        et_user_type = (Spinner) findViewById(R.id.spinnerTipos);
        adapter = ArrayAdapter.createFromResource(this, R.array.tipos_usuario, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et_user_type.setAdapter(adapter);

        //---------------------------------------------------------
        //Refereciando os objetos do XML
        et_username = (EditText) findViewById(R.id.et_username);
        et_name = (EditText) findViewById(R.id.et_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_conf = (EditText) findViewById(R.id.et_password_conf);
        iv_profile_pic = (ImageView) findViewById(R.id.iv_profile_picture);
        iv_profile_pic.setImageResource(R.drawable.usericon2);
        et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                    if (b == true)
                        et_username.setError("Digite apenas letras minúsculas");
            }
        });
    }

    public void onButtonClick(View v) {

        //--------------------------------------------------------------------
        //Clicou no botão ALTERAR FOTO ou na imagem de perfil
        if (v.getId() == R.id.iv_profile_picture || v.getId() == R.id.tv_changePicture) {

            //Request STORAGE Permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_READ_EXTERNAL_STORAGE);

                // Checa se o user realmente deu permissão
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    goToPhotoDialog();
                }
            }
            // Permissão já estava dada
            else {
                goToPhotoDialog();
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

            //Se o usuario nao digitar nada em em algum dos campos, recebe uma mensagem de erro
            if (!allFieldsValid())
                return;

            //Salva os dados do usuario em SharedPreferences para uso em outras activites
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("image_url", imageURL).apply();

            //---- Request using RETROFIT ----
            // Prepare the HTTP request
            Call<User> call = api.usersCreate(user);
            call.enqueue(new Callback<User>() {

                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {

                    //Se o servidor retornou com sucesso
                    if (response.isSuccess() && response.code() == 200) {

                        // Exibe mensagem de sucesso
                        Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();

                        //Inicia o aplicativo
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao criar usuário", Toast.LENGTH_LONG).show();
                        Log.e("ERROR_FAILURE: ", response.code() + " Message: " + response.message());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("ERROR_FAILURE: ", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Erro ao criar usuário", Toast.LENGTH_LONG).show();
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
                        uploadFromCamera(data);
                        return;
                    case SELECT_FILE:
                        uploadFromFile(data);
                }
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

    private void goToPhotoDialog() {
        //Storage Permission already granted, do what we need
        final CharSequence[] items = {"Tire uma foto", "Escolha da galeria", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    private boolean allFieldsValid() {
        if (user.getUsername().length() == 0) {
            et_username.setError("Digite um Nome de Usuário");
            return false;
        }

        if (user.getUsername().contains("A") || user.getUsername().contains("B") || user.getUsername().contains("C") ||
                user.getUsername().contains("D") || user.getUsername().contains("E") || user.getUsername().contains("F") ||
                user.getUsername().contains("G") || user.getUsername().contains("H") || user.getUsername().contains("I") ||
                user.getUsername().contains("J") || user.getUsername().contains("K") || user.getUsername().contains("L") ||
                user.getUsername().contains("M") || user.getUsername().contains("N") || user.getUsername().contains("O") ||
                user.getUsername().contains("P") || user.getUsername().contains("Q") || user.getUsername().contains("R") ||
                user.getUsername().contains("S") || user.getUsername().contains("T") || user.getUsername().contains("U") ||
                user.getUsername().contains("V") || user.getUsername().contains("X") || user.getUsername().contains("Y") ||
                user.getUsername().contains("Z") || user.getUsername().contains("W")){

            String aux = et_username.getText().toString().toLowerCase();
            et_username.setText(aux);
            Toast.makeText(getApplicationContext(), "'Nome de usuário' convertido para letras minúsculas", Toast.LENGTH_LONG).show();
            return false;
        }

        if (user.getName().length() == 0) {
            et_name.setError("Digite seu nome");
            return false;
        }
        if (user.getLast_name().length() == 0) {
            et_last_name.setError("Digite seu sobrenome");
            return false;
        }
        if (user.getPassword().length() == 0) {
            et_password.setError("Digite uma senha");
            return false;
        }
        if (user.getEmail().length() == 0) {
            et_email.setError("Digite seu e-mail");
            return false;
        }
        if (et_password_conf.getText().length() == 0) {
            et_password_conf.setError("Confirme sua senha");
            return false;
        }

        //Se o usuario nao selecionar o tipo de usuario, exibe mensagem de erro
        String tipo = et_user_type.getSelectedItem().toString();
        if (tipo.contentEquals("Selecione o tipo de usuário")) {
            Toast.makeText(getApplicationContext(), "Por favor, selecione um tipo de usuário.", Toast.LENGTH_LONG).show();
            return false;
        }

        //Se Senha e a confirmacao de senha nao forem iguais, exibe mensagem ao usuario
        if (password_conf.equals(user.getPassword()) == false) {
            Toast.makeText(getApplicationContext(), "SENHA e CONFIRMAÇÃO DE SENHA não conferem, por favor verifique.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void uploadFromCamera(Intent data) {
        //Imagem capturada salva na variavel imageUri(Uri)
        imageUri = data.getData();
        cameraImage = (Bitmap) data.getExtras().get("data");
        //Colocando e ajustando a imagem na UI
        iv_profile_pic.setBackground(null);
        iv_profile_pic.setVisibility(View.VISIBLE);
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
    }

    private void uploadFromFile(Intent data) {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {// to avoid the NullPointerException
            cameraImage = (Bitmap) data.getExtras().get("data");
            Toast.makeText(getApplicationContext(), imageUri.toString(), Toast.LENGTH_LONG).show();
        }

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
    }

    @Override
    public void onFocusChange(View view, boolean b) {

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

                // REDUZINDO/CONVERTENDO/MANIPULANDO a foto tirada pelo usuário para o upload
                // ser feito de forma correta, sem rotacionar a imagem ao enviar ao servidor Cloudinary
                //Primeiro reduzimos o tamanho da imagem obtida da camera
                cameraImageResized = getResizedBitmap(cameraImage, 600);
                //Depois, pegamos o 'path' dessa imagem Bitmap
                imageUri = getImageUri(SignUpActivity.this, cameraImageResized);
                //Por ultimo, pegamos o 'path' real da imagem Uri para passar ao CLoudinary
                imagePath = getPath(imageUri);
                //Criando um imagem do tipo arquivo para manipular a orientação e salvar corretamente no Cloudinary
                imageFile = new File(imagePath);

                exif = null;
                try {
                    exif = new ExifInterface(imagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap bmRotated = rotateBitmap(cameraImageResized, orientation);

                //--------------------------------------------
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

                //Primeiro pegamos o path real da Uri da imagem
                imagePath = getPath(imageUri);
                //Depois, criamos um arquivo com o path da imagem
                imageFile = new File(imagePath);
                //Depois, utilizamos a biblioteca Compressor para comprimir uma imageFile para Bitmap
                cameraImage = Compressor.getDefault(SignUpActivity.this).compressToBitmap(imageFile);
                //Depois, pegamos o 'path' dessa imagem Bitmap
                imageUri = getImageUri(SignUpActivity.this, cameraImage);
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
        }

    }


    //---------------------------------------------------------------------------------------------
    // FUNCOES PARA MANIPULACAO DE IMAGENS   ------------------------------------------------------

    //Retorna o 'path' da imagem Uri em String
    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //Retorna o 'path' de uma imagem Bitmap em Uri
    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 0, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //Reduz o tamanho de uma imagem Bitmap
    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
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

    //---------------------------------------------------------------------
    // THIS FUNCTIONS MANAGE THE PHOTOS TAKEN FROM THE CAMERA SO THEY DONT
    // GET ROTATED WHEN SENT TO THE Cloudinary SERVER
    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


}