package br.ufscar.connect.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import br.ufscar.connect.R;
import br.ufscar.connect.models.FeedEvaluationPost;

/**
 * Created by bruno on 04/02/17.
 */

public class FeedEvaluationListAdapter extends ArrayAdapter<FeedEvaluationPost> {
    
    int layoutId = R.layout.layout_feed_evaluations;
    
    public FeedEvaluationListAdapter(Context context, List<FeedEvaluationPost> FeedEvaluationPostList){
        super(context, 0, FeedEvaluationPostList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FeedEvaluationPost post = getItem(position);
        
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }

        ImageView ivUserPhoto = (ImageView) convertView.findViewById(R.id.iv_user_photo);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvUserType = (TextView) convertView.findViewById(R.id.tv_user_type);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        TextView tvPlaceName = (TextView) convertView.findViewById(R.id.tv_place_name);
        RatingBar rbInfra = (RatingBar) convertView.findViewById(R.id.rb_infra);
        RatingBar rbAcess = (RatingBar) convertView.findViewById(R.id.rb_acess);
        RatingBar rbLimpeza = (RatingBar) convertView.findViewById(R.id.rb_limpeza);
        RatingBar rbSeg = (RatingBar) convertView.findViewById(R.id.rb_seg);
        RatingBar rbGeral = (RatingBar) convertView.findViewById(R.id.rb_geral);

        ivUserPhoto.setImageResource(post.getUserPhotoId());
        tvUserName.setText(post.getUserName());
        tvUserType.setText(post.getUserType());
        tvDate.setText(post.getDate());
        tvPlaceName.setText(post.getPlaceName());
        rbInfra.setRating(post.getRatingInfra());
        rbAcess.setRating(post.getRatingAcess());
        rbLimpeza.setRating(post.getRatingLimpeza());
        rbSeg.setRating(post.getRatingSeguranca());
        rbGeral.setRating(post.getRatingGeral());

        return convertView;
    }
}