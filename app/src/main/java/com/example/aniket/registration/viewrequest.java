package com.example.aniket.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.aniket.registration.Adapters.ShowRequestAdapter;
import com.example.aniket.registration.pojo.ShowRequestPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewrequest extends AppCompatActivity {

    ListView viewreq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrequest);

        viewreq = (ListView) findViewById(R.id.list_viewreq);

        new ShowRequest(viewrequest.this).execute();
    }

    public class ShowRequest extends AsyncTask<String, String, String> {
        Context context;
        String fid;
        ProgressDialog progressDialog;
        ArrayList<ShowRequestPojo> ShowRequestPojoArrayList;

        public ShowRequest(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Fetching Records");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ShowRequestPojoArrayList = new ArrayList<ShowRequestPojo>();
        }

        @Override
        public String doInBackground(String... strings) {
            String timelinescript = "showrequests.php";
            SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("FTWOC",0);
            fid=sharedPreferences.getString("id","0");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, CommonImports.HOST + timelinescript, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Reg1", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ShowRequestPojo p = new ShowRequestPojo();
                            p.setSreid(jsonObject.getString("reid"));
                            p.setSrpname(jsonObject.getString("pname"));
                            p.setSrvname(jsonObject.getString("name"));
                            p.setSrvphone(jsonObject.getString("mobno"));

                            ShowRequestPojoArrayList.add(p);
                        }
                        ShowRequestAdapter adapter = new ShowRequestAdapter(viewrequest.this, viewrequest.this, ShowRequestPojoArrayList);
                        viewreq.setAdapter(adapter);

                    } catch (Exception e) {
                        Toast.makeText(context, "No requests...", Toast.LENGTH_LONG).show();
                        Log.e("Reg1", response);
                    }
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "No requests...", Toast.LENGTH_LONG).show();
                }
            }) {

                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String,String>();
                    params.put("fid",fid);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
            return null;
        }

    }
}
