package com.example.accuair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends AppCompatActivity {

    public Button s_proceed_but;

    public EditText username,email,password,copassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Signup_proceed_but();
        username = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        copassword = (EditText)findViewById(R.id.copassword);

        //add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void OnReg(View view){
        String name = username.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        String cpass =copassword.getText().toString();
        String type = "Register";

        BackgroundWork back = new BackgroundWork(this);
        back.execute(type,name,mail,pass,cpass);


    }
    public void Signup_proceed_but()
    {
        s_proceed_but=(Button)findViewById(R.id.s_proceed_but);
        s_proceed_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextActivity;
                nextActivity = new Intent(Signup.this,Login.class);
                startActivity(nextActivity);
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