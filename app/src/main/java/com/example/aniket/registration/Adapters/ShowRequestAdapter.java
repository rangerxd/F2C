package com.example.aniket.registration.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aniket.registration.R;
import com.example.aniket.registration.pojo.ShowRequestPojo;

import java.util.ArrayList;

/**
 * Created by aniket on 3/24/2018.
 */

public class ShowRequestAdapter extends BaseAdapter{

    Context context;
    Activity activity;
    ArrayList<ShowRequestPojo> ShowRequestPojoArrayList =new ArrayList<ShowRequestPojo>();
    LayoutInflater layoutInflater;

    public ShowRequestAdapter(Context context, Activity activity, ArrayList<ShowRequestPojo> ShowRequestPojoArrayList) {
        this.context = context;
        this.activity = activity;
        this.ShowRequestPojoArrayList = ShowRequestPojoArrayList;
    }


    @Override
    public int getCount() {
        return ShowRequestPojoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ShowRequestPojoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(ShowRequestPojoArrayList.get(position).getSreid());
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.showrequest,null);
        TextView prodname=(TextView)view.findViewById(R.id.txt_srprodname);
        TextView vendorname=(TextView)view.findViewById(R.id.txt_srname);
        TextView mobno =(TextView)view.findViewById(R.id.txt_srmobno);

        prodname.setText(ShowRequestPojoArrayList.get(position).getSrpname());
        vendorname.setText(ShowRequestPojoArrayList.get(position).getSrvname());
        mobno.setText(ShowRequestPojoArrayList.get(position).getSrvphone());

        return view;
    }
}
