package br.ufscar.connect.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import br.ufscar.connect.R;
import br.ufscar.connect.models.FeedProblemPost;


public class FeedProblemListAdapter extends ArrayAdapter<FeedProblemPost> {

    int layoutId = R.layout.layout_feed_problems;

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

        AsyncTask a = new AsyncTask<Object, Void, Void>(){
            Bitmap userPhoto, problemPhoto;

            @Override
            protected Void doInBackground(Object... params) {
                try {
                    userPhoto = BitmapFactory.decodeStream(new URL(post.getUserUrl()).openConnection().getInputStream());
                } catch (IOException e) {
                    userPhoto = null;
                }

                try {
                    problemPhoto = BitmapFactory.decodeStream(new URL(post.getPhotoUrl()).openConnection().getInputStream());
                } catch (IOException e) {
                    problemPhoto = null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (userPhoto != null)
                    ivUserPhoto.setImageBitmap(userPhoto);
                else
                    ivUserPhoto.setImageResource(R.drawable.user);

                if (problemPhoto != null)
                    ivPublPhoto.setImageBitmap(problemPhoto);
                else
                    ivPublPhoto.setImageResource(R.drawable.danosgramado);

                return;
            }
        };

        a.execute();


        return convertView;
    }


}
