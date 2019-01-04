package com.example.aniket.registration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class VendorRequirement extends AppCompatActivity {

    EditText ed_rname,rquantity;
    Button btn_rupload;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_requirement);

        ed_rname=(EditText) findViewById(R.id.rname);
        rquantity= (EditText)findViewById(R.id.rquan);

        btn_rupload= findViewById(R.id.btn_require);

        btn_rupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(ed_rname.getText()))
                {
                    ed_rname.setError("Product Name is required");
                    flag=1;
                }else
                {
                    flag=0;
                }
                if(TextUtils.isEmpty(rquantity.getText())) {
                    rquantity.setError("Product Quantity is required");
                    flag=1;
                }else {
                    flag=0;
                }
                AlertDialog.Builder adb = new AlertDialog.Builder(VendorRequirement.this);
                adb.setTitle("Upload Requirement");
                adb.setMessage("Do want to Upload Requirement ...");
                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if((flag == 0)) {
                            new Rupload(VendorRequirement.this, ed_rname.getText().toString(), rquantity.getText().toString()).execute();
                        }
                        Toast.makeText(getApplicationContext(),"Requirement uploaded",Toast.LENGTH_LONG).show();
                    } });
                adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(new Intent(VendorRequirement.this,timeline.class));
                    } });
                adb.show();
            }
        });
    }

    public  class Rupload extends AsyncTask<String,String,String>
    {
        Context context;
        String rname,rqaun;

        public Rupload(Context context,String pname,String pqaun)
        {
            this.context=context;
            this.rname=pname;
            this.rqaun=pqaun;
        }

        @Override
        protected String doInBackground(String... strings) {
            String script="rupload.php";
            SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
            final String id=sharedPreferences.getString("id","0");
            StringRequest stringRequest=new StringRequest(Request.Method.POST, CommonImports.HOST + script, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("MainAct", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equals("true")) {
                            Toast.makeText(context, "Uploaded Sucessfully", Toast.LENGTH_LONG).show();
                            // finishActivity(HomeActivity.class);
                            // startActivity(new Intent(Upload.this, HomeActivity.class));
                        } else {
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
                    params.put("rname",rname);
                    params.put("rquan",rqaun);
                    params.put("id",id);
                    return params;
                }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            return null;

        }

        protected void onPostExecute(String s) {
            startActivity(new Intent(VendorRequirement.this,timeline.class));
            finish();
        }
    }
}
