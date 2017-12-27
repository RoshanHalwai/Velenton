package com.example.rhalwai.velenton.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rhalwai.velenton.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhalwai on 12/24/2017.
 */

public class MoreListAdapter extends ArrayAdapter<MoreList> implements View.OnClickListener {

    private Context mCtx;
    private List<MoreList> moreLists;
    private int lastPosition = -1;

    public MoreListAdapter(ArrayList<MoreList> data, Context context) {
        super(context, R.layout.layout_list_more, data);
        this.moreLists = data;
        this.mCtx = context;

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MoreList moreList = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_list_more, parent, false);
            viewHolder.itemName = convertView.findViewById(R.id.moreListText);
            viewHolder.itemImage = convertView.findViewById(R.id.moreListImage);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        lastPosition = position;

        viewHolder.itemName.setText(moreList.getName());
        viewHolder.itemImage.setImageDrawable(mCtx.getDrawable(moreList.getImageId()));
        // Return the completed view to render on screen
        return convertView;
    }

    class ViewHolder {
        TextView itemName;
        ImageView itemImage;
    }
}
