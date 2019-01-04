package com.example.aniket.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.ContactsContract;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtxt_username,edtxt_password;
    Button btn_login;
    TextView txtv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtxt_username=(EditText)findViewById(R.id.edtxt_username);
        edtxt_password=(EditText)findViewById(R.id.edtxt_password);
        btn_login=(Button)findViewById(R.id.btn_login);
        txtv_register=(TextView)findViewById(R.id.txtv_register);

        btn_login.setOnClickListener(this);
        txtv_register.setOnClickListener(this);

        }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtv_register:
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                break;
            case R.id.btn_login:
                //startActivity(new Intent(LoginActivity.this,Profile.class));
                new Login(LoginActivity.this,edtxt_username.getText().toString(),edtxt_password.getText().toString()).execute();
                break;
        }
    }
    public class Login extends AsyncTask<String,String,String>{
        Context context;
        String username,password;
        ProgressDialog progressDialog;

        public Login(Context context,String username,String password){
            this.context = context;
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("logging in");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String loginScript =  "loginprocess.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, CommonImports.HOST + loginScript, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("MainAct", response);
                    try {
                        JSONObject jsonObject1 = new JSONObject(response.trim());
                        if ((jsonObject1.getString("status").equals("true")) && (jsonObject1.getString("usertype").equals("farmer"))) {
                            Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG).show();
                            SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("id",jsonObject1.getString("id")).apply();
                            editor.putString("mobno",jsonObject1.getString("mobno")).apply();
                            editor.putString("usertype",jsonObject1.getString("usertype")).apply();
                            editor.putBoolean("IS_LOGIN",true).apply();
                            editor.commit();
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));

                        }
                        else if ((jsonObject1.getString("status").equals("true")) && (jsonObject1.getString("usertype").equals("vendor"))) {
                            //Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG).show();
                            SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("id",jsonObject1.getString("id")).apply();
                            editor.putString("mobno",jsonObject1.getString("mobno")).apply();
                            editor.putString("usertype",jsonObject1.getString("usertype")).apply();
                            editor.putBoolean("IS_LOGIN",true).apply();
                            editor.commit();
                            startActivity(new Intent(LoginActivity.this,timeline.class));

                        }
                        else {
                            Toast.makeText(context, "Invalid Login", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("MainAct",error.getMessage());
                }
            })
            {
                @Override
                public Map<String,String> getParams() throws AuthFailureError{
                    Map<String,String> param = new HashMap<String, String>();
                    param.put("username",username);
                    param.put("password",password);
                    return param;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
