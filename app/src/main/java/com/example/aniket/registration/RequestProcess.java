package com.example.aniket.registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class RequestProcess extends AppCompatActivity {

    String fid,pid,vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_process);

        fid = getIntent().getStringExtra("fid");
        pid = getIntent().getStringExtra("pid");
        vid = getIntent().getStringExtra("vid");
        Log.e("fid",fid);
        new RequestSend(RequestProcess.this, fid, pid, vid).execute();
    }

    public  class RequestSend extends AsyncTask<String,String,String>
    {
        Context context;
        String fid1,pid1,vid1;
        public RequestSend(Context context,String fid,String pid,String vid)
        {
            this.context=context;
            fid1 = fid;
            pid1 = pid;
            vid1 = vid;

        }

        @Override
        protected String doInBackground(String... strings) {
            String script="sendrequest.php";
           // SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
           // final String id=sharedPreferences.getString("id","0");
            StringRequest stringRequest=new StringRequest(Request.Method.POST, CommonImports.HOST + script, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("MainAct", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("true")) {
                            Toast.makeText(context, "Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                            // finishActivity(HomeActivity.class);
                            // startActivity(new Intent(Upload.this, HomeActivity.class));
                        } else {
                            Toast.makeText(context, "Upload fail please try again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    params.put("id",vid1);
                    params.put("fid",fid1);
                    params.put("pid",pid1);
                    return params;
                }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            return null;

        }
        protected void onPostExecute(String s) {
           Toast.makeText(RequestProcess.this,"Request Sent Succesfully",Toast.LENGTH_SHORT).show();
           startActivity(new Intent(RequestProcess.this,timeline.class));
            finish();
        }
    }

}
