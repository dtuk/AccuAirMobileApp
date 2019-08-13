package com.example.accuair;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class PrefConfig {
    private SharedPreferences shared_pref;
    private Context context;

    public PrefConfig(Context context){
        this.context = context;
        shared_pref = context.getSharedPreferences(context.getString(R.string.pref_file),Context.MODE_PRIVATE);

    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = shared_pref.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status),status);
    }

    public boolean readLoginStatus(){
        return shared_pref.getBoolean(context.getString(R.string.pref_login_status),false);
    }

    public void writeName(String name){
        SharedPreferences.Editor editor = shared_pref.edit();
        editor.putString(context.getString(R.string.pref_name),name);
        editor.commit();
    }

    public String readName(){
        return  shared_pref.getString(context.getString(R.string.pref_name),"User");
    }

    public void displayToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

    }


}
