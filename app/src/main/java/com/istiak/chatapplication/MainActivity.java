package com.istiak.chatapplication;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etxtMsg;
    private Button btnSend;
    private RecyclerView recyclerView;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etxtMsg = findViewById(R.id.etxt_msg);
        btnSend = findViewById(R.id.btn_send);
        recyclerView = findViewById(R.id.recycler_view);

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        showData();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData();
                sendMsg();
            }
        });

    }









    private void sendMsg() {
        //Getting values from edit texts
        final String msg = etxtMsg.getText().toString().trim();



        //Checking  field/validation
        if (msg.isEmpty()) {
            etxtMsg.setError("Please enter name !");
            etxtMsg.requestFocus();
        }else
        {


            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.UPLOAD_DATA_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //for track response in logcat
                    Log.d("RESPONSE", response);
                    if (response.equals("success")) {
                        showData();
                    }

                    else if (response.equals("failure")) {

                        Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();

                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(Constant.KEY_MSG, msg);

                    //returning parameter
                    return params;
                }
            };


            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
















            private void showData () {

                String URL = Constant.VIEW_DATA_URL;

                StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                        Log.d("data", response);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                            }
                        });

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);
            }


            private void showJSON (String response){
                //Create json object for receiving jason data
                JSONObject jsonObject = null;
                ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);


                    if (result.length() == 0) {
                        Toast.makeText(this, "No data Found", Toast.LENGTH_SHORT).show();
                    } else {

                        for (int i = 0; i < result.length(); i++) {
                            JSONObject jo = result.getJSONObject(i);
                            String msg = jo.getString(Constant.KEY_MSG);


                            //put value into Hashmap
                            HashMap<String, String> user_data = new HashMap<>();
                            user_data.put(Constant.KEY_MSG, msg);

                            list.add(user_data);
                        }


                        //  call the constructor of CustomAdapter to send the reference and data to Adapter
                        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, list);
                        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
