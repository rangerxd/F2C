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
import com.example.aniket.registration.pojo.ViewVendorPojo;


import java.util.ArrayList;

/**
 * Created by aniket on 3/24/2018.
 */

public class ViewVendorAdapter extends BaseAdapter{

    Context context;
    Activity activity;
    ArrayList<ViewVendorPojo> ViewVendorPojoArrayList =new ArrayList<ViewVendorPojo>();
    LayoutInflater layoutInflater;

    public ViewVendorAdapter(Context context, Activity activity, ArrayList<ViewVendorPojo> ViewVendorPojoArrayList) {
        this.context = context;
        this.activity = activity;
        this.ViewVendorPojoArrayList = ViewVendorPojoArrayList;
    }


    @Override
    public int getCount() {
        return ViewVendorPojoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ViewVendorPojoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(ViewVendorPojoArrayList.get(position).getVid());
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.vendordetail,null);
        TextView name=(TextView)view.findViewById(R.id.txt_vendorname);
        TextView mobno=(TextView)view.findViewById(R.id.txt_vendormobno);
        TextView address=(TextView)view.findViewById(R.id.txt_vendoradd);

        name.setText(ViewVendorPojoArrayList.get(position).getVname());
        mobno.setText(ViewVendorPojoArrayList.get(position).getVnumber());
        address.setText(ViewVendorPojoArrayList.get(position).getVadd());

        return view;
    }
}
