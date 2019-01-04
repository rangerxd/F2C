package com.example.aniket.registration.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.BaseAdapter;

/**
 * Created by aniket on 3/19/2018.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aniket.registration.R;
import com.example.aniket.registration.pojo.ProductPojo;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by hp on 17-Mar-18.
 */

public class ProductAdapter extends BaseAdapter {
    Context context;
    Activity activity;
    ArrayList<ProductPojo> ProductPojoArrayList=new ArrayList<ProductPojo>();
   LayoutInflater layoutInflater;

    public ProductAdapter(Context context, Activity activity, ArrayList<ProductPojo> ProductPojoArrayList) {
        this.context = context;
        this.activity = activity;
        this.ProductPojoArrayList = ProductPojoArrayList;
    }


    @Override
    public int getCount() {
        return ProductPojoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ProductPojoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(ProductPojoArrayList.get(position).getProdid());
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.product_item,null);
        TextView name=(TextView)view.findViewById(R.id.txt_tname);
        TextView quantity=(TextView)view.findViewById(R.id.txt_tquan);
        TextView desc=(TextView)view.findViewById(R.id.txt_tdes);

        name.setText(ProductPojoArrayList.get(position).getProdname());
        quantity.setText(ProductPojoArrayList.get(position).getProdqauntity());
        desc.setText(ProductPojoArrayList.get(position).getProddesc());

        return view;
    }
}
