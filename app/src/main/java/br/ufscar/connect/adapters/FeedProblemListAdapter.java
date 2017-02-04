package br.ufscar.connect.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        FeedProblemPost post = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }

        ImageView ivUserPhoto = (ImageView) convertView.findViewById(R.id.iv_user_photo);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvUsertype = (TextView) convertView.findViewById(R.id.tv_usertype);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        TextView tvType = (TextView) convertView.findViewById(R.id.tv_type);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tv_address);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tv_description);
        ImageView ivPublPhoto = (ImageView) convertView.findViewById(R.id.iv_publ_photo);

        //Setando valores para os objetos XML
        ivUserPhoto.setImageResource(post.getUserPhotoId());
        tvUsername.setText(post.getUserName());
        tvUsertype.setText(post.getUserType());
        tvDate.setText(post.getPublDate());
        tvType.setText(post.getType());
        tvAddress.setText(post.getAddress());
        tvDescription.setText(post.getDescription());
        ivPublPhoto.setImageResource(post.getPublPhotoId());

        return convertView;
    }


}
