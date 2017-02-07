package br.ufscar.connect.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import br.ufscar.connect.R;
import br.ufscar.connect.models.FeedEvaluationPost;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by bruno on 04/02/17.
 */

public class FeedEvaluationListAdapter extends ArrayAdapter<FeedEvaluationPost> {
    
    int layoutId = R.layout.layout_feed_evaluations;
    Bitmap resizedUserPhoto;
    Uri userPhotoUri;
    String userImageUrl;
    
    public FeedEvaluationListAdapter(Context context, List<FeedEvaluationPost> FeedEvaluationPostList){
        super(context, 0, FeedEvaluationPostList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FeedEvaluationPost post = getItem(position);
        
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }

        final ImageView ivUserPhoto = (ImageView) convertView.findViewById(R.id.iv_user_photo);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvUserType = (TextView) convertView.findViewById(R.id.tv_user_type);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        TextView tvPlaceName = (TextView) convertView.findViewById(R.id.tv_place_name);
        RatingBar rbInfra = (RatingBar) convertView.findViewById(R.id.rb_infra);
        RatingBar rbAcess = (RatingBar) convertView.findViewById(R.id.rb_acess);
        RatingBar rbLimpeza = (RatingBar) convertView.findViewById(R.id.rb_limpeza);
        RatingBar rbSeg = (RatingBar) convertView.findViewById(R.id.rb_seg);
        RatingBar rbGeral = (RatingBar) convertView.findViewById(R.id.rb_geral);

        tvUserName.setText(post.getUserName());
        tvUserType.setText(post.getUserType());
        tvDate.setText(post.getDate());
        tvPlaceName.setText(post.getPlaceName());
        rbInfra.setRating(post.getRatingInfra());
        rbAcess.setRating(post.getRatingAcess());
        rbLimpeza.setRating(post.getRatingLimpeza());
        rbSeg.setRating(post.getRatingSeguranca());
        rbGeral.setRating(post.getRatingGeral());

        AsyncTask a = new AsyncTask<Object, Void, Void>(){
            Bitmap userPhoto;

            @Override
            protected Void doInBackground(Object... params) {

                try {
                    userPhoto = BitmapFactory.decodeStream(new URL(post.getUserPhotoUrl()).openConnection().getInputStream());
                    userPhotoUri = getImageUri(getContext(), userPhoto);

                } catch (IOException e) {
                    userPhoto = null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (userPhoto != null) {
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
                    ivUserPhoto.setImageResource(R.drawable.user);
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
}
