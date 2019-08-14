package com.example.accuair;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Readings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings);

        String lat = (String) getIntent().getStringExtra("lat");
        String lng = (String) getIntent().getStringExtra("lng");

        final String message = "You clicked on, lat: "+lat+", lng: "+lng+", going to get data";

        getData(new Object[]{lat, lng, this});

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        /*ListView listRecommended = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(Readings.this,CO2,icons,ratings,prices);
        listRecommended.setAdapter(adapter);*/

        //add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }



    public void getData(final Object[] sendingData) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String lat = String.valueOf(sendingData[0]);
                    String lng = String.valueOf(sendingData[1]);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
//                    cal.add(Calendar.HOUR_OF_DAY, -2);
                    cal.add(Calendar.MONTH, -6);
                    String from = formatter.format(cal.getTime());
                    System.out.println(from);

                    String link = "http://accuair.cf/api/getdata";
                    URL url = new URL(link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("lat", lat);
                    jsonParam.put("lng", lng);
                    jsonParam.put("from",from);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();
                    String resCode = String.valueOf(conn.getResponseCode());

                    BufferedReader reader = null;
                    if (resCode.startsWith("2")){
                        reader = new BufferedReader(new
                                InputStreamReader(conn.getInputStream()));
                    }
                    else {
                        reader = new BufferedReader(new
                                InputStreamReader(conn.getErrorStream() ));
                    }


                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
//                        break;
                    }
                    String results = sb.toString();

                    System.out.println("Received \t\t\t\t : \t\t "+results+" \t\t \n\n\t\t");
                    Log.i("STATUS", resCode);
                    Log.i("MSG" , results);

                    conn.disconnect();

                    try{
                        JSONObject respJson = new JSONObject(results);
                        int status = 0;
                        if (respJson.has("status")){
                            status = Integer.parseInt(respJson.getString("status"));
                        }
                        if (status == 1){

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Successfully retrieved  data!", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            });


                            String st  = "1";

                            if (respJson.has("readings")){
                                JSONObject json_data;

//                                List<JSONObject> readings1 = (List<JSONObject>) respJson.get("readings");
//                                JSONArray readings = new JSONArray(respJson.get("readings"));
                                JSONArray readings = (JSONArray) respJson.get("readings");
                                final ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
                                for(int i=0; i < readings.length() ; i++) {
                                    json_data = readings.getJSONObject(i);
                                    String  latitude =json_data.getString("lat");
                                    String longitude =json_data.getString("lng");
                                    String co = json_data.getString("co");
                                    String co2 = json_data.getString("co2");
                                    String tem = json_data.getString("tem");
                                    String humidity = json_data.getString("humidity");
                                    String time = json_data.getString("created_at");

//                                    items.add(Arr)
//                                    Log.d("NAME","Latitude");
                                }

                                System.out.println("Done!");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ArrayAdapter<ArrayList<String>> adapter = new ArrayAdapter<ArrayList<String>>((Context) sendingData[2],
                                                android.R.layout.simple_expandable_list_item_1, items);

                                        ListView listRecommended = (ListView) findViewById(R.id.listView);
//                                ArrayAdapter adapter = new ArrayAdapter(mArrayAdapter);
                                        listRecommended.setAdapter(adapter);
                                    }
                                });

//                                setListAdapter(adapter);
                            }


                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Couldn't get data!", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }
                    catch (JSONException ex){
                        Log.d("ERROR",ex.getMessage());
                    }





                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

}

class Item{
    private String lat, lng, co, co2, tem, humidity, time;

    public Item(String lat, String lng, String co, String co2, String tem, String humidity, String time) {
        this.lat = lat;
        this.lng = lng;
        this.co = co;
        this.co2 = co2;
        this.tem = tem;
        this.humidity = humidity;
        this.time = time;
    }
}

/*class CustomAdapter extends ArrayAdapter<Item>{
    public CustomAdapter(ArrayList<Item> items){
        super(getActivity(), 0, items);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item current = getItem(position);
//        return super.getView(position, convertView, parent);
    }
}*/

/*
class CustomAdapter extends ArrayAdapter<Item>{
    public CustomAdapter(ArrayList<Item> items){
        super(getActivity(), 0, items);
    }
}*/
