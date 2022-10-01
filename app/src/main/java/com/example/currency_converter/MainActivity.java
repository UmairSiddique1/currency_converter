package com.example.currency_converter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
private Spinner to,from;
private Button convertBtn;
private TextView textView;
private EditText editText;
private List<String> stringList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        to=findViewById(R.id.toSpinner);
        textView=findViewById(R.id.textView);
        from=findViewById(R.id.fromSprinner);
        convertBtn=findViewById(R.id.convertButton);
        editText=findViewById(R.id.amountEdittext);
        stringList=new ArrayList<>();
        stringList.add("PKR");
        stringList.add("INR");
        stringList.add("CAD");
        stringList.add("USD");
        stringList.add("AED");
        stringList.add("AFN");
        stringList.add("JPY");
        stringList.add("GBP");
        stringList.add("CNY");
        stringList.add("RUB");
        stringList.add("SAR");
        stringList.add("BDT");


        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,stringList);
        from.setAdapter(arrayAdapter);

    ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,stringList);
    to.setAdapter(arrayAdapter1);

    convertBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String tospinner=to.getSelectedItem().toString();
            String fromspinner=from.getSelectedItem().toString();
            String ed=editText.getText().toString();
            fetchData(tospinner,fromspinner,ed);
        }
    });

    }

    private void fetchData(String tospinner, String fromspinner, String amt) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.apilayer.com/fixer/convert?to="+tospinner+"&from="+fromspinner+"&amount="+amt+"";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject=response.getJSONObject("query");
                            String getFrom=jsonObject.getString("from");
                            String getTo=jsonObject.getString("to");
                            int getAmount=jsonObject.getInt("amount");

                            String getDate=response.getString("date");
                            Double getResult=response.getDouble("result");
                            textView.setText("The converted value is "+getResult.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap=new HashMap();
                hashMap.put("apikey","1GfK1WDCgWpC5qJP5Dg9nqonNb8WSjor");
                return hashMap;
            }
        };

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }
}