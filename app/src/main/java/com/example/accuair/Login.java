package com.example.accuair;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Login extends AppCompatActivity {



    public Button l_proceed_but;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

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

                EditText username = (EditText) findViewById(R.id.username);
                EditText password = (EditText) findViewById(R.id.password);

                String email = username.getText().toString();
                String pass = password.getText().toString();
                System.out.println("\n\n\n\n\t\temail: " + email + ", pass: " + pass + "\t\t\n\n\n");
                new SendLogin().execute(email, pass);
//                postData(email, pass);

                Intent nextActivity = new Intent(Login.this, Home.class);
                startActivity(nextActivity);
            }

        });
    }

    public void postData2(String email, String password){
        String urlString = "http://accuair.cf/api/login"; // URL to call
        String data = "{'email': '"+email+"', 'password': '"+password+"'}"; //data to post
        OutputStream out = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            out = new BufferedOutputStream(urlConnection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            out.close();

            urlConnection.connect();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void OnLogin(View view){
        String name = username.getText().toString();
        String pass = password.getText().toString();
        String type = "Login";

        BackgroundWork back = new BackgroundWork(this);
        back.execute(type,name,pass);


    }

    class SendLogin extends AsyncTask {


        @Override
        protected Void doInBackground(Object... params) {

            String email = params[0].toString();
            String password = params[1].toString();
            try {
                String link = "http://accuair.cf/api/login";

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", "id");
//        jsonObject.put("value", 1296);
//        jsonArray.put(jsonObject);
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("name", "email");
                jsonObject1.put("value", email);
                jsonArray.put(jsonObject1);
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("name", "password");
                jsonObject2.put("value", password);
                jsonArray.put(jsonObject2);


//                        String data = URLEncoder.encode("data", "UTF-8") + "=" + URLEncoder.encode(jsonArray.toString(), "UTF-8");

                String data = URLEncoder.encode("id", "UTF-8") + "=" +
                        URLEncoder.encode("1206", "UTF-8") + "&" +

                        URLEncoder.encode("lat", "UTF-8") + "=" +
                        URLEncoder.encode("5521512", "UTF-8") + "&" +

                        URLEncoder.encode("lot", "UTF-8") + "=" +
                        URLEncoder.encode("101010", "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                //System.out.println(data);
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

//            wr.write(data);
                String jsonData = jsonArray.toString();
                wr.write(jsonData);
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
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

        }

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

