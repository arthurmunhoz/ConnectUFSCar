package br.ufscar.connect.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufscar.connect.activities.ProfileActivity;
import br.ufscar.connect.R;


public class ProfileActivityCustomAdapter extends BaseAdapter {

    String[] result, result2, result3;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;

    public ProfileActivityCustomAdapter(ProfileActivity activity, String[] type_list, String[] address_list, String[] description_list, int[] publicationPhotoList) {

        result = type_list;
        result2 = address_list;
        result3 = description_list;
        context = activity;
        imageId = publicationPhotoList;
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
        ImageView iv_photo;
        TextView tvType;
        TextView tvAddress;
        TextView tvDescription;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.layout_my_publications, null);

        holder.tvType = (TextView) rowView.findViewById(R.id.tv_type);
        holder.tvAddress = (TextView) rowView.findViewById(R.id.tv_address);
        holder.tvDescription = (TextView) rowView.findViewById(R.id.tv_description);
        holder.iv_photo = (ImageView) rowView.findViewById(R.id.iv_user_photo);
        holder.tvType.setText(result[position]);
        holder.tvAddress.setText(result2[position]);
        holder.tvDescription.setText(result3[position]);
        holder.iv_photo.setImageResource(imageId[position]);

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