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
import com.example.aniket.registration.pojo.FarmerPojo;
import com.example.aniket.registration.pojo.ProductPojo;

import java.util.ArrayList;

/**
 * Created by aniket on 3/24/2018.
 */

public class FarmerAdapter extends BaseAdapter{

    Context context;
    Activity activity;
    ArrayList<FarmerPojo> FarmerPojoArrayList =new ArrayList<FarmerPojo>();
    LayoutInflater layoutInflater;

    public FarmerAdapter(Context context, Activity activity, ArrayList<FarmerPojo> FarmerPojoArrayList) {
        this.context = context;
        this.activity = activity;
        this.FarmerPojoArrayList = FarmerPojoArrayList;
    }


    @Override
    public int getCount() {
        return FarmerPojoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return FarmerPojoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(FarmerPojoArrayList.get(position).getFprodid());
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.product_item,null);
        TextView name=(TextView)view.findViewById(R.id.txt_tname);
        TextView quantity=(TextView)view.findViewById(R.id.txt_tquan);
        TextView desc=(TextView)view.findViewById(R.id.txt_tdes);

        name.setText(FarmerPojoArrayList.get(position).getFprodname());
        quantity.setText(FarmerPojoArrayList.get(position).getFprodqauntity());
        desc.setText(FarmerPojoArrayList.get(position).getFproddesc());

        return view;
    }
}
