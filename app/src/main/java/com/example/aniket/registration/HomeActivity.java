package com.example.aniket.registration;

import android.annotation.SuppressLint;
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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aniket.registration.Adapters.FarmerAdapter;
import com.example.aniket.registration.Adapters.ProductAdapter;
import com.example.aniket.registration.pojo.FarmerPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    ListView farmerlist;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        farmerlist=(ListView)findViewById(R.id.farmerlist);

         new FGetProducts(HomeActivity.this).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.option_menu,menu);
        //return super.onCreateOptionsMenu(menu);
    return true;
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
               switch (item.getItemId()){
                   case R.id.profile:
                       startActivity(new Intent(HomeActivity.this,Profile.class));
                       Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                       break;

                   case R.id.about_us:
                       startActivity(new Intent(HomeActivity.this,About.class));
                       Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                       break;

                   case R.id.logout:
                       SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.clear();
                       editor.commit();
                       Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                       finish();
                       break;

                   case R.id.viewvendor:
                       startActivity(new Intent(HomeActivity.this,ViewVendor.class));
                       break;

                   case R.id.report:
                       finish();
                       startActivity(new Intent(HomeActivity.this,report.class));
                       break;

                   case R.id.upload:
                       finish();
                       startActivity(new Intent(HomeActivity.this,Upload.class));
                       Toast.makeText(this, "Upload", Toast.LENGTH_SHORT).show();

                        break;
                   case R.id.viewrequest:
                         startActivity(new Intent(HomeActivity.this,viewrequest.class));
                       break;
               }
        return true;
    }


    public  class FGetProducts extends AsyncTask<String,String,String> {
        Context context;
        ProgressDialog progressDialog;
        ArrayList<FarmerPojo> FarmerPojoArrayList;
        public FGetProducts(Context context)
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
            FarmerPojoArrayList=new ArrayList<FarmerPojo>();
        }

        @Override
        public String doInBackground(String... strings) {
            String timelinescript="ftimeline.php";
            SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
            final String id=sharedPreferences.getString("id","0");
            StringRequest stringRequest=new StringRequest(Request.Method.POST, CommonImports.HOST + timelinescript, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("kk",response);
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            FarmerPojo p=new FarmerPojo();
                            p.setFfid(jsonObject.getString("id"));
                            p.setFprodid(jsonObject.getString("pid"));
                            p.setFprodname(jsonObject.getString("pname"));
                            p.setFprodqauntity(jsonObject.getString("pquantity"));
                            p.setFproddesc(jsonObject.getString("pdescription"));

                            FarmerPojoArrayList.add(p);
                        }
                        FarmerAdapter fadapter=new FarmerAdapter(HomeActivity.this,HomeActivity.this,FarmerPojoArrayList);
                        farmerlist.setAdapter(fadapter);

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(context,"No products uploaded....",Toast.LENGTH_LONG).show();
                        Log.e("Reg1",response);
                    }
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,"No products uploaded....",Toast.LENGTH_LONG).show();
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String,String>();
                    params.put("id",id);
                    return params;
                }
            };
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


