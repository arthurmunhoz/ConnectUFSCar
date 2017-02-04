package br.ufscar.connect.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufscar.connect.activities.FeedActivity;
import br.ufscar.connect.R;


public class FeedActivityCustomAdapter extends BaseAdapter {

    String[] result, result2, result3, result4, result5, result6;
    int[] result7, result8;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;

    public FeedActivityCustomAdapter(FeedActivity activity, int[] user_photo_list, String[] username_list, String[] usertype_list, String[] publ_date_list, String[] address_list,
                                     String[] type_list, String[] description_list, int[] publ_photo_list) {

        result = username_list;
        result2 = usertype_list;
        result3 = publ_date_list;
        result4 = address_list;
        result5 = type_list;
        result6 = description_list;
        result7 = publ_photo_list;
        result8 = user_photo_list;
        context = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        ImageView iv_user_photo;
        TextView tvUsername;
        TextView tvUsertype;
        TextView tvDate;
        TextView tvType;
        TextView tvAddress;
        TextView tvDescription;
        ImageView iv_publ_photo;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.layout_feed_problems, null);

        //Declarando objetos do XML
        holder.iv_user_photo = (ImageView) rowView.findViewById(R.id.iv_user_photo);
        holder.tvUsername = (TextView) rowView.findViewById(R.id.tv_name);
        holder.tvUsertype = (TextView) rowView.findViewById(R.id.tv_usertype);
        holder.tvDate = (TextView) rowView.findViewById(R.id.tv_date);
        holder.tvType = (TextView) rowView.findViewById(R.id.tv_type);
        holder.tvAddress = (TextView) rowView.findViewById(R.id.tv_address);
        holder.tvDescription = (TextView) rowView.findViewById(R.id.tv_description);
        holder.iv_publ_photo = (ImageView) rowView.findViewById(R.id.iv_publ_photo);

        //Setando valores para os objetos XML
        holder.iv_user_photo.setImageResource(result8[position]);
        holder.tvUsername.setText(result[position]);
        holder.tvUsertype.setText(result2[position]);
        holder.tvDate.setText(result3[position]);
        holder.tvType.setText(result4[position]);
        holder.tvAddress.setText(result5[position]);
        holder.tvDescription.setText(result6[position]);
        holder.iv_publ_photo.setImageResource(result7[position]);

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }

}