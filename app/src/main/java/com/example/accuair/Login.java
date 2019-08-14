package com.example.accuair;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Login extends AppCompatActivity {



    public Button l_proceed_but;
    EditText emailEt, passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       Login_proceed_but();

        //add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void Login_proceed_but() {
        l_proceed_but = (Button) findViewById(R.id.l_proceed_but);

        l_proceed_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*EditText username = (EditText) findViewById(R.id.username);
                EditText password = (EditText) findViewById(R.id.password);

                String email = username.getText().toString();
                String pass = password.getText().toString();
                System.out.println("\n\n\n\n\t\temail: " + email + ", pass: " + pass + "\t\t\n\n\n");
                new SendLogin().execute(email, pass);
                postData(email, pass);*/
                OnLogin(view);

            }

        });
    }



    public void OnLogin(View view){

        emailEt = (EditText) findViewById(R.id.email);
        passwordEt = (EditText) findViewById(R.id.password);

        String mail = emailEt.getText().toString();
        String pass = passwordEt.getText().toString();

        System.out.println("Email: "+mail+", pass: "+pass);

//        new SendLogin().execute(new Object[]{mail, pass});

        sendPost(new Object[]{mail, pass, this});


    }

    public void sendPost(final Object[] sendingData) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String email = String.valueOf(sendingData[0]);
                    String password = String.valueOf(sendingData[1]);
                    String link = "http://accuair.cf/api/login";
                    URL url = new URL(link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("email", email);
                    jsonParam.put("password", password);

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
                        if (status == 1 || true){

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Successfully logged in!", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            });


                            String st  = "1";

                            BackgroundWork back = new BackgroundWork((Context) sendingData[2]);
                            back.execute(st,email,password);

                            Intent nextActivity = new Intent(Login.this, Home.class);
                            startActivity(nextActivity);
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Invalid Login credentials!", Toast.LENGTH_LONG).show();
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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

class SendLogin extends AsyncTask {


    @Override
    protected Object doInBackground(Object[] params) {
//        while (true) {

            try {
//                while ((boolean) params[1]) {
//                    MainActivity mainActivity = (MainActivity) params[0];
//                    mainActivity.getLoco();
//                    if (mainActivity.mylastLocation != null) {
                        String email = String.valueOf(params[0]);
                        String password = String.valueOf(params[1]);
                        String link = "http://accuair.cf/api/login";

//                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("email", email);
                        jsonObject.put("password", password);
//                        jsonArray.put(jsonObject);
//                        JSONObject jsonObject1 = new JSONObject();
//                        jsonObject1.put("name", "lat");
//                        jsonObject1.put("value", mainActivity.mylastLocation.getLatitude());
//                        jsonArray.put(jsonObject1);
//                        JSONObject jsonObject2 = new JSONObject();
//                        jsonObject2.put("name", "lon");
//                        jsonObject2.put("value", mainActivity.mylastLocation.getLongitude());
//                        jsonArray.put(jsonObject2);

                String data_string = jsonObject.toString();
                System.out.println(data_string);
                        String data = URLEncoder.encode("data", "UTF-8") + "=" + URLEncoder.encode(data_string, "UTF-8");
                        System.out.println(data);
//                        String data  = URLEncoder.encode("id", "UTF-8") + "=" +
//                                URLEncoder.encode("1206", "UTF-8")+"&"+
//
//                                URLEncoder.encode("lat", "UTF-8") + "=" +
//                                URLEncoder.encode("5521512", "UTF-8")+"&"+
//
//                                URLEncoder.encode("lot", "UTF-8") + "=" +
//                                URLEncoder.encode("101010", "UTF-8");
                        URL url = new URL(link);
                        URLConnection conn = url.openConnection();
                        //System.out.println(data);
                        conn.setDoOutput(true);
                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                        wr.write(data);
                        wr.flush();

                        BufferedReader reader = new BufferedReader(new
                                InputStreamReader(conn.getInputStream()));

                        StringBuilder sb = new StringBuilder();
                        String line = null;

                        // Read Server Response
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                            break;
                        }
                        String results = sb.toString();
                        if (results.length() == 0) {

                        }
                        System.out.println(results);
//                    }
//                    Thread.sleep(4000);
//                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

    }
}