package br.ufscar.connect.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.Transform;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import br.ufscar.connect.R;
import br.ufscar.connect.activities.FeedActivity;
import br.ufscar.connect.models.FeedProblemPost;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class FeedProblemListAdapter extends ArrayAdapter<FeedProblemPost> {

    int layoutId = R.layout.layout_feed_problems;
    Bitmap resizedUserPhoto, resizedProblemPhoto;
    Uri userPhotoUri, problemPhotoUri;
    String userImageUrl, problemImageUrl;
    Context context = getContext();

    public FeedProblemListAdapter(Context context, List<FeedProblemPost> feedProblemPostList){
        super(context, 0, feedProblemPostList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FeedProblemPost post = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }

        TextView tvUsername = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvUsertype = (TextView) convertView.findViewById(R.id.tv_usertype);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        TextView tvType = (TextView) convertView.findViewById(R.id.tv_type);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tv_address);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tv_description);
        final ImageView ivUserPhoto = (ImageView) convertView.findViewById(R.id.iv_user_photo);
        final ImageView ivPublPhoto = (ImageView) convertView.findViewById(R.id.iv_publ_photo);

        //Setando valores para os objetos XML
        tvUsername.setText(post.getUserName());
        tvUsertype.setText(post.getUserType());
        tvDate.setText(post.getPublDate());
        tvType.setText(post.getType());
        tvAddress.setText(post.getAddress());
        tvDescription.setText(post.getDescription());
        userImageUrl = post.getUserUrl();
        problemImageUrl = post.getPhotoUrl();


        AsyncTask a = new AsyncTask<Object, Void, Void>(){
            Bitmap userPhoto, problemPhoto;

            @Override
            protected Void doInBackground(Object... params) {

                Uri uri =  Uri.parse(post.getUserUrl());
                try {
                    userPhoto = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {

                    userPhoto = BitmapFactory.decodeStream(new URL(post.getUserUrl()).openConnection().getInputStream());
                    userPhotoUri = getImageUri(getContext(), userPhoto);


                } catch (IOException e) {
                    userPhoto = null;
                }

                try {
                    problemPhoto = BitmapFactory.decodeStream(new URL(post.getPhotoUrl()).openConnection().getInputStream());
                    problemPhotoUri = getImageUri(getContext(), problemPhoto);

                } catch (IOException e) {
                    problemPhoto = null;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                if (userPhoto != null)
                {
                    //mostrando imagem com picasso
                    Picasso.Builder builder = new Picasso.Builder(getContext());
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                    builder.build().load(userPhotoUri).transform(new CropCircleTransformation()).into(ivUserPhoto);
                }
                else
                    ivUserPhoto.setImageResource(R.drawable.usericon2);

                if (problemPhoto != null)
                {
                    //mostrando imagem com picasso
                    Picasso.Builder builder = new Picasso.Builder(getContext());
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                    builder.build().load(problemPhotoUri).into(ivPublPhoto);
                }
                else
                    ivPublPhoto.setImageResource(R.drawable.danosgramado);

            }
        };

        a.execute();


        return convertView;
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

    public static Bitmap createCircleBitmap(Bitmap bitmap){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        int halfWidth = bitmap.getWidth()/2;
        int halfHeight = bitmap.getHeight()/2;
        canvas.drawCircle(halfWidth, halfHeight, Math.max(halfWidth, halfHeight), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


}
