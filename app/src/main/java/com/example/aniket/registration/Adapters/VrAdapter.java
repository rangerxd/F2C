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
import com.example.aniket.registration.pojo.VrPojo;


import java.util.ArrayList;

/**
 * Created by aniket on 3/24/2018.
 */

public class VrAdapter extends BaseAdapter{

    Context context;
    Activity activity;
    ArrayList<VrPojo> VrPojoArrayList =new ArrayList<VrPojo>();
    LayoutInflater layoutInflater;

    public VrAdapter(Context context, Activity activity, ArrayList<VrPojo> VrPojoArrayList) {
        this.context = context;
        this.activity = activity;
        this.VrPojoArrayList = VrPojoArrayList;
    }


    @Override
    public int getCount() {
        return VrPojoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return VrPojoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(VrPojoArrayList.get(position).getRid());
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.vrequirement,null);
        TextView name=(TextView)view.findViewById(R.id.txt_requirementname);
        TextView quantity=(TextView)view.findViewById(R.id.txt_requiremnetquan);

        name.setText(VrPojoArrayList.get(position).getRname());
        quantity.setText(VrPojoArrayList.get(position).getRqaun());

        return view;
    }
}
