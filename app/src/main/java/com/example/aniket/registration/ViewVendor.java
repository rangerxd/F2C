package com.example.aniket.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aniket.registration.Adapters.ViewVendorAdapter;
import com.example.aniket.registration.pojo.ProductPojo;
import com.example.aniket.registration.pojo.ViewVendorPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewVendor extends AppCompatActivity {

    ListView list_vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vendor);

        list_vv = (ListView) findViewById(R.id.list_vv);

        list_vv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewVendorPojo v=(ViewVendorPojo) parent.getItemAtPosition(position);
                // Toast.makeText(timeline.this,"test "+p.getProdname(),Toast.LENGTH_LONG).show();
                Log.e("Reg1","tes");
                Intent i=new Intent(ViewVendor.this,vrdetails.class);
                i.putExtra("vid",v.getVid());
                //  Toast.makeText(timeline.this,"fid "+p.getFid(),Toast.LENGTH_LONG).show();

                startActivity(i);


            }
        });

        new ViewVendor.GetVendor(ViewVendor.this).execute();
    }


    public class GetVendor extends AsyncTask<String, String, String> {
        Context context;
        ProgressDialog progressDialog;
        ArrayList<ViewVendorPojo> ViewVendorPojoArrayList;

        public GetVendor(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Fetching Records");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ViewVendorPojoArrayList = new ArrayList<ViewVendorPojo>();
        }

        @Override
        public String doInBackground(String... strings) {
            String timelinescript = "viewvendor.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, CommonImports.HOST + timelinescript, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Reg1", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ViewVendorPojo p = new ViewVendorPojo();
                            p.setVid(jsonObject.getString("id"));
                            p.setVname(jsonObject.getString("name"));
                            p.setVnumber(jsonObject.getString("mobno"));
                            p.setVadd(jsonObject.getString("address"));

                            ViewVendorPojoArrayList.add(p);
                        }
                        ViewVendorAdapter adapter = new ViewVendorAdapter(ViewVendor.this, ViewVendor.this, ViewVendorPojoArrayList);
                        list_vv.setAdapter(adapter);

                    } catch (Exception e) {
                        Toast.makeText(context, "ERR: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("Reg1", response);
                    }
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "ERR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
            return null;
        }


    }
}