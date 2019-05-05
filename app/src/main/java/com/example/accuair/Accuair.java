package com.example.accuair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Accuair extends AppCompatActivity {

    public Button login_but,signup_but;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accuair);

        Login_but();
        Signup_but();
    }


    public void Login_but()
    {
        login_but=(Button)findViewById(R.id.login_but);
        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextActivity;
                nextActivity = new Intent(Accuair.this,Login.class);
                startActivity(nextActivity);
            }
        });
    }

    public void Signup_but()
    {
        signup_but=(Button)findViewById(R.id.signup_but);
        signup_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextActivity;
                nextActivity = new Intent(Accuair.this,Signup.class);
                startActivity(nextActivity);
            }
        });
    }
}
