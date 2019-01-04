package com.example.aniket.registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class Profile extends AppCompatActivity {
TextView txt_name ,txt_mobno,txt_address,txt_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txt_name=(TextView)findViewById(R.id.txt_name);
        txt_mobno=(TextView)findViewById(R.id.txt_mobno);
        txt_address=(TextView)findViewById(R.id.txt_address);
        txt_email=(TextView)findViewById(R.id.txt_email);


        new display(Profile.this).execute();
    }
    public class display extends AsyncTask<String,String,String>{
        Context context;
        public display(Context context)
        {
            this.context=context;
        }



        @Override
        protected String doInBackground(String... strings) {
            String script="profile.php";
            SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
           final String id=sharedPreferences.getString("id","0");
            StringRequest stringRequest=new StringRequest(Request.Method.POST, CommonImports.HOST + script, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Mainact", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("true")) {
                            Log.e("Mainact", jsonObject.getString("mobno"));
                            txt_name.setText(jsonObject.getString("name"));
                            txt_mobno.setText(jsonObject.getString("mobno"));
                            txt_address.setText(jsonObject.getString("address"));
                            txt_email.setText(jsonObject.getString("email"));
                        } else {
                            Toast.makeText(context,"Registered fail please try again",Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
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
                    params.put("id",id);
                    return params;
                }

            };
            RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
            return null;
        }

        }
}
