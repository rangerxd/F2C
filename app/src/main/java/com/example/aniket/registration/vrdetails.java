package com.example.aniket.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aniket.registration.Adapters.VrAdapter;
import com.example.aniket.registration.pojo.VrPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class vrdetails extends AppCompatActivity {

    ListView vrlist;
    String i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrdetails);

        vrlist = (ListView) findViewById(R.id.vr_list);
        i = getIntent().getStringExtra("vid");

        new GetRequirement(vrdetails.this).execute();
    }

    public class GetRequirement extends AsyncTask<String, String, String> {
        Context context;
        ProgressDialog progressDialog;
        ArrayList<VrPojo> VrPojoArrayList;

        public GetRequirement(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Fetching Records");
            progressDialog.setCancelable(false);
            progressDialog.show();
            VrPojoArrayList = new ArrayList<VrPojo>();
        }

        @Override
        public String doInBackground(String... strings) {
            String timelinescript = "vrshow.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, CommonImports.HOST + timelinescript, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Reg1", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            VrPojo p = new VrPojo();
                            p.setRid(jsonObject.getString("rid"));
                            p.setRname(jsonObject.getString("rname"));
                            p.setRqaun(jsonObject.getString("rquan"));
                            VrPojoArrayList.add(p);
                        }
                        VrAdapter adapter = new VrAdapter(vrdetails.this, vrdetails.this, VrPojoArrayList);
                        vrlist.setAdapter(adapter);

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
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", i);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
            return null;
        }


    }
}
