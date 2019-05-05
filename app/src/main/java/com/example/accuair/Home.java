package com.example.accuair;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    private DrawerLayout home_drawer;
    private ActionBarDrawerToggle ntoggle;
    public Button location_but;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Location_but();

        home_drawer = (DrawerLayout)findViewById(R.id.home_drawer);
        ntoggle = new ActionBarDrawerToggle(this,home_drawer,R.string.open, R.string.close);

        home_drawer.addDrawerListener(ntoggle);
        ntoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigation_view);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                int id = item.getItemId();
                switch(id){

                    case R.id.acc_settings:
                        Intent settings = new  Intent(Home.this,Settings.class);
                        startActivity(settings);
                        /*item.setChecked(true);*/
                        home_drawer.closeDrawers();
                        break;

                    case R.id.acc_logout:
                        Intent logOutActivity=new Intent(Home.this,Accuair.class);
                        startActivity(logOutActivity);
                        finish();
                        break;

                    case R.id.app_terms:
                        Intent terms =new Intent(Home.this, Conditions.class);
                        startActivity(terms);
                        home_drawer.closeDrawers();
                        break;


                    case R.id.app_aboutus:
                        Intent aboutus=new Intent(Home.this,Aboutus.class);
                        startActivity(aboutus);
                        home_drawer.closeDrawers();
                        break;
                }

                return false;
            }
        });


    }

    public void Location_but()
    {
        location_but=(Button)findViewById(R.id.location_but);
        location_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextActivity;
                nextActivity = new Intent(Home.this, Location.class);
                startActivity(nextActivity);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (ntoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
