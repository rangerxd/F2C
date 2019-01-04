package com.example.aniket.registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class report extends AppCompatActivity {
    EditText edtxt_rsubject,edtxt_rdes;
    Button btn_submit;
    int flag=0;
    String usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        edtxt_rsubject=(EditText) findViewById(R.id.reportsubject);
        edtxt_rdes= (EditText)findViewById(R.id.reportdescription);


        btn_submit= findViewById(R.id.reportsubmit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edtxt_rsubject.getText()))
                {
                    edtxt_rsubject.setError("Report title required");
                    flag=1;
                }else
                {
                    flag=0;
                }
                if(TextUtils.isEmpty(edtxt_rdes.getText())) {
                    edtxt_rdes.setError("Report description required");
                    flag=1;
                }else {
                    flag=0;
                }
                if(flag==0) {
                    new Report(report.this, edtxt_rsubject.getText().toString(), edtxt_rdes.getText().toString()).execute();
                }
            }
        });
    }



    public  class Report extends AsyncTask<String,String,String>
    {
        Context context;
        String rsubject,rdes;

        public Report(Context context,String rsubject,String rdes)
        {
            this.context=context;
            this.rsubject=rsubject;
            this.rdes=rdes;

        }

        @Override
        protected String doInBackground(String... strings) {
            String script="reports.php";
            SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
            final String id=sharedPreferences.getString("id","0");
            usertype=sharedPreferences.getString("usertype","0");
            StringRequest stringRequest=new StringRequest(Request.Method.POST, CommonImports.HOST + script, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("MainAct", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("true") && usertype.equals("farmer")) {
                            Toast.makeText(context, " Report Uploaded Sucessfully", Toast.LENGTH_LONG).show();

                           // startActivity(new Intent(report.this, HomeActivity.class));
                        }
                        else if(jsonObject.getString("status").equals("true") && usertype.equals("vendor"))
                        {
                            Toast.makeText(context, " Report Uploaded Sucessfully", Toast.LENGTH_LONG).show();

                           // startActivity(new Intent(report.this, timeline.class));
                        }

                        else {
                            Toast.makeText(context, "Upload fail please try again", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("MainAct",error.getMessage());
                }
            })
            {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String,String>();
                    params.put("rsubject",rsubject);
                    params.put("rdes",rdes);
                    params.put("id",id);
                    return params;
                }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            return null;

        }

        protected void onPostExecute(String s) {
            if(usertype.equals("farmer")) {
                startActivity(new Intent(report.this, HomeActivity.class));
                finish();
            }
            else if(usertype.equals("vendor"))
            {
                startActivity(new Intent(report.this,timeline.class));
                finish();
            }
        }
    }

}
