package com.example.aniket.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button btn_register;
    EditText edtxt_name,edtxt_email,edtxt_phone,edtxt_address,edtxt_pass,edtxt_repass;
    RadioGroup radioGroup;
    String farmor_or_vendr;
    int flagname=0,flagmail=0,flagadd=0,flagmobno=0,flagpass=0,flagrepass=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtxt_name=(EditText)findViewById(R.id.edtxt_name);
        edtxt_email=(EditText)findViewById(R.id.edtxt_email);
        edtxt_address=(EditText)findViewById(R.id.edtxt_addres);
        edtxt_phone=(EditText)findViewById(R.id.edtxt_phone);
        edtxt_pass=(EditText)findViewById(R.id.edtxt_passwd);
        edtxt_repass=(EditText)findViewById(R.id.edtxt_repasswd);
        radioGroup=(RadioGroup)findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rdbtn_farmer:
                        farmor_or_vendr="farmer";
                        break;
                    case R.id.rdbtn_vendor:
                        farmor_or_vendr="vendor";
                        break;
                }
            }
        });
        btn_register=(Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtxt_name.getText().toString().equals("")){
                    edtxt_name.setError("Enter correct Name");
                    flagname=1;
                }
                else{
                    flagname=0;
                }
                if(!validateEmail(edtxt_email.getText().toString())) {
                    edtxt_email.setError("Enter correct Email");
                    flagmail=1;
                }else{
                    flagmail=0;
                }
                if(edtxt_address.getText().toString().equals("")){
                    edtxt_address.setError("Enter correct Address");
                    flagadd=1;
                }
                else{
                    flagadd=0;
                }
                if (!validatePhone(edtxt_phone.getText().toString())) {
                    edtxt_phone.setError("Please type 10 digit number");
                    flagmobno=1;
                }
                else{
                    flagmobno=0;
                }
                if (!validatePassword(edtxt_pass.getText().toString())) {
                    edtxt_pass.setError("Enter strong password");
                    flagpass=1;
                }
                else{
                    flagpass=0;
                }
                if (!validateRePassword(edtxt_repass.getText().toString(),edtxt_pass.getText().toString())) {
                    edtxt_repass.setError("Please type correct password");;
                    flagrepass=1;
                }
                else {
                    flagrepass=0;
                }
                if(flagname==0 &&flagmail==0&&flagadd==0&&flagmobno==0&&flagpass==0&&flagrepass==0) {
                    //Toast.makeText(MainActivity.this, edtxt_name.getText().toString()+" "+edtxt_email.getText().toString()+" "+edtxt_phone.getText().toString()+" "+edtxt_address.getText().toString()+" "+ edtxt_pass.getText().toString(),Toast.LENGTH_LONG).show();
                    new Register(MainActivity.this, edtxt_name.getText().toString(), edtxt_email.getText().toString(), edtxt_phone.getText().toString(), edtxt_address.getText().toString(), edtxt_pass.getText().toString(), farmor_or_vendr).execute();
                }
            }
        });


    }



    protected boolean validatePhone(String phone) {
        boolean check = false;
        if (!Pattern.matches("[0-9]", phone)) {
            if (((phone.length() <10 )|| (phone.length() > 10)) && phone.length()!=0) {
                // if(phone.length() != 10) {
                check =false;
                // txtPhone.setError("Not Valid Number");
            } else {
                check = android.util.Patterns.PHONE.matcher(phone).matches();
            }
        } else {
            check = false;
        }
        return check;
    }

    protected boolean validatePassword(String edtxt_pass) {
        if(edtxt_pass!=null && edtxt_pass.length()>=8) {
            return true;
        } else {
            return false;
        }
    }
    protected boolean validateRePassword(String edtxt_repass,String edtxt_pass) {
        if(edtxt_repass.equals(edtxt_pass)&&edtxt_repass!=null) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean validateEmail(String edtxtemail) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(edtxtemail);

        return matcher.matches();
    }






    public class Register extends AsyncTask<String,String,String>{
        Context context;
        String name,email,phone,address,pass,farmor_or_vendr;
        ProgressDialog progressDialog;
        public Register(Context context, String edtxt_name, String edtxt_email, String edtxt_phone, String edtxt_address, String edtxt_pass, String farmor_or_vendr) {
            this.context = context;
            this.name = edtxt_name;
            this.email = edtxt_email;
            this.phone = edtxt_phone;
            this.address = edtxt_address;
            this.pass = edtxt_pass;
            this.farmor_or_vendr = farmor_or_vendr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Registering...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        public String doInBackground(String... strings) {

            String script="regprocess.php";

            StringRequest stringRequest=new StringRequest(Request.Method.POST, CommonImports.HOST + script, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("MainAct",response);
                    try
                    {
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.getString("status").equals("exist"))
                        {
                            Toast.makeText(context,"Mobile number already registered",Toast.LENGTH_LONG).show();
                            edtxt_name.setText("");
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            finish();
                        }
                        if(jsonObject.getString("status").equals("true"))
                        {
                            Toast.makeText(context,"Registered Successfully",Toast.LENGTH_LONG).show();
                            edtxt_name.setText("");
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            finish();

                        }
                        else
                        {
                            Toast.makeText(context,"Registered fail please try again",Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
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
                    params.put("username",phone);
                    params.put("password",pass);
                    params.put("name",name);
                    params.put("usertype",farmor_or_vendr);
                    params.put("address",address);
                    params.put("email",email);
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
