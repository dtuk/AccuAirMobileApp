package com.example.accuair;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWork extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertd;

    BackgroundWork(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String Login_url = "http://www.accuair.cf/api/login";
        String Register_url = "http://www.accuair.cf/api/register";
        if(type.equals("Login")){

            try {
                URL url = new URL(Login_url);
                String name = params[1];
                String pass = params[2];
                HttpURLConnection connect = (HttpURLConnection)url.openConnection();
                connect.setRequestMethod("POST");
                connect.setDoOutput(true);
                connect.setDoInput(true);

                OutputStream out = connect.getOutputStream();
                BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(out,  "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
                buff.write(post_data);
                buff.flush();
                buff.close();
                out.close();

                InputStream in = connect.getInputStream();
                BufferedReader buffr = new BufferedReader(new InputStreamReader(in,"iso-8859-1"));
                String result = "";
                String line = "";
                 while ((line=buffr.readLine())!=null){
                     result += line;
                 }
                 buffr.close();
                 in.close();
                 connect.disconnect();
                 return result;



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if(type.equals("Register")){

            try {
                URL url = new URL(Register_url);
                String name = params[1];
                String mail = params[2];
                String pass = params[3];
                String cpass = params[4];
                HttpURLConnection connect = (HttpURLConnection)url.openConnection();
                connect.setRequestMethod("POST");
                connect.setDoOutput(true);
                connect.setDoInput(true);

                OutputStream out = connect.getOutputStream();
                BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(out,  "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                URLEncoder.encode("mail","UTF-8")+"="+URLEncoder.encode(mail,"UTF-8")+"&"+
                URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")+"&"+
                        URLEncoder.encode("cpass","UTF-8")+"="+URLEncoder.encode(cpass,"UTF-8");
                buff.write(post_data);
                buff.flush();
                buff.close();
                out.close();

                InputStream in = connect.getInputStream();
                BufferedReader buffr = new BufferedReader(new InputStreamReader(in,"iso-8859-1"));
                String result = "";
                String line = "";
                while ((line=buffr.readLine())!=null){
                    result += line;
                }
                buffr.close();
                in.close();
                connect.disconnect();
                return result;



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertd = new AlertDialog.Builder(context).create();
        alertd.setTitle("Register Status");
    }


    @Override
    protected void onPostExecute(String result) {
        alertd.setMessage(result);
        alertd.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}


