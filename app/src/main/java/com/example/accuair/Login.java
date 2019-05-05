package com.example.accuair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    public Button l_proceed_but;

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
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        if (username.getText().toString().trim().equals("user") && password.getText().toString().equals("abc")) {
            Intent nextActivity = new Intent(Login.this, Home.class);
            startActivity(nextActivity);
        }

            }
        });
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
