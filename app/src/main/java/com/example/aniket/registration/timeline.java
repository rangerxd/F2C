package com.example.aniket.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aniket.registration.Adapters.ProductAdapter;
import com.example.aniket.registration.CommonImports;
import com.example.aniket.registration.R;

import com.example.aniket.registration.pojo.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class timeline extends AppCompatActivity{

    ListView lv_doner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        lv_doner=(ListView)findViewById(R.id.lv_timeline);

        lv_doner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductPojo p=(ProductPojo)parent.getItemAtPosition(position);
               // Toast.makeText(timeline.this,"test "+p.getProdname(),Toast.LENGTH_LONG).show();
                Log.e("Reg1","tes");
                Intent i=new Intent(timeline.this,farmerdetail.class);
                i.putExtra("fid",p.getFid());
                i.putExtra("pid",p.getProdid());

              //  Toast.makeText(timeline.this,"fid "+p.getFid(),Toast.LENGTH_LONG).show();

                startActivity(i);


            }
        });

        new getProducts(timeline.this).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.usermenu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){


            case R.id.user_profile:
                startActivity(new Intent(timeline.this,uprofile.class));
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.user_requirement:
                startActivity(new Intent(timeline.this,VendorRequirement.class));
                break;

            case R.id.about_us:
                startActivity(new Intent(timeline.this,About.class));
                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_logout:
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(this, "Logout Succesfull", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(timeline.this,LoginActivity.class));
                finish();

                break;

            case R.id.user_report:
                 startActivity(new Intent(timeline.this,report.class));
                break;
        }
        return true;
    }


        public  class getProducts extends AsyncTask<String,String,String>{
            Context context;
            ProgressDialog progressDialog;
            ArrayList<ProductPojo> ProductPojoArrayList;
            public getProducts(Context context)
            {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(context);
                progressDialog.setMessage("Fetching Records");
                progressDialog.setCancelable(false);
                progressDialog.show();
                ProductPojoArrayList=new ArrayList<ProductPojo>();
            }

            @Override
            public String doInBackground(String... strings) {
                String timelinescript="timeline.php";

                StringRequest stringRequest=new StringRequest(Request.Method.POST, CommonImports.HOST + timelinescript, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Reg1",response);
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                ProductPojo p=new ProductPojo();
                                p.setFid(jsonObject.getString("id"));
                                p.setProdid(jsonObject.getString("pid"));
                                p.setProdname(jsonObject.getString("pname"));
                                p.setProdqauntity(jsonObject.getString("pquantity"));
                                p.setProddesc(jsonObject.getString("pdescription"));

                                ProductPojoArrayList.add(p);
                            }
                            ProductAdapter adapter=new ProductAdapter(timeline.this,timeline.this,ProductPojoArrayList);
                            lv_doner.setAdapter(adapter);

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(context,"ERR: "+e.getMessage(),Toast.LENGTH_LONG).show();
                            Log.e("Reg1",response);
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"ERR: "+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){};
                RequestQueue requestQueue= Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
                return null;
            }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();


        }
    }


}
