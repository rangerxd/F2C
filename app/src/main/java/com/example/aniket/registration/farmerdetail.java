package com.example.aniket.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class farmerdetail extends AppCompatActivity {

    TextView farmername,farmernumber,farmeraddress,farmeremail;
    String fi,pid,vid;
    Button btn_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmerdetail);

        farmername = (TextView) findViewById(R.id.farmername);
        farmeraddress = (TextView)findViewById(R.id.farmeraddress);
        farmernumber = (TextView)findViewById(R.id.farmernumber);
        farmeremail = (TextView)findViewById(R.id.farmeremail);
        btn_request = (Button)findViewById(R.id.btn_request);
         fi = getIntent().getStringExtra("fid");
         pid=getIntent().getStringExtra("pid");
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
        vid=sharedPreferences.getString("id","0");

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(farmerdetail.this,RequestProcess.class);
                i.putExtra("fid",fi);
                i.putExtra("pid",pid);
                i.putExtra("vid",vid);

                Toast.makeText(farmerdetail.this, "Request sent", Toast.LENGTH_SHORT).show();
                startActivity(i);

            }
        });
        //Toast.makeText(farmerdetail.this,i,Toast.LENGTH_SHORT);
        new FarmerDetails(farmerdetail.this).execute();
    }


    public class FarmerDetails extends AsyncTask<String,String,String>{
        Context context;
        ProgressDialog progressDialog;
        public FarmerDetails(Context context)
        {
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Fetching Records");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            String script="farmerdetail.php";
          //  SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
            //final String id=sharedPreferences.getString("fid","0");
            StringRequest stringRequest=new StringRequest(Request.Method.POST, CommonImports.HOST + script, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("detail", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("true")) {
                            Log.e("detail", jsonObject.getString("mobno"));
                            farmername.setText(jsonObject.getString("name"));
                            farmernumber.setText(jsonObject.getString("mobno"));
                            farmeraddress.setText(jsonObject.getString("address"));
                            farmeremail.setText(jsonObject.getString("email"));
                        } else {
                            Toast.makeText(context,"Fetching failed..",Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                    }
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("detail",error.getMessage());
                }


            })
            {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String,String>();
                    params.put("id",fi);

                    return params;
                }

            };
            RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
            return null;
        }

    }
    }

