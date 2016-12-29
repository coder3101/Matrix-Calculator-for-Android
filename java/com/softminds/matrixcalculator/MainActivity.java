/*
MIT License

Copyright (c) 2016 Ashar Khan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/package com.softminds.matrixcalculator;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        final int RESULT=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDark_NoActionBar);
        else
            setTheme(R.style.AppTheme_NoActionBar);


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(savedInstanceState==null)
        {
            MainActivityFragmentList mh=new MainActivityFragmentList();
            getFragmentManager().beginTransaction().add(R.id.MainContent,mh).commit();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        navigationView.setCheckedItem(R.id.Home);


        if(v!=null)
        {
            if(isDark) {
                v.setBackground(ContextCompat.getDrawable(this, R.drawable.side_nav_bar_dark));
                navigationView.setItemTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            }
            else
                v.setBackground(ContextCompat.getDrawable(this,R.drawable.side_nav_bar));
        }


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.MainFAB);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(getApplicationContext(),MakeNewMatrix.class);
                startActivityForResult(intent,RESULT);


            }
        });


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this,SettingsTab.class);
                startActivityForResult(intent,100);
                break;
            case R.id.aboutmeButton:
                Intent intent2 = new Intent(getApplication(),AboutMe.class);
                startActivity(intent2);
                break;
            case R.id.action_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,R.string.app_name);
                sharingIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.ShareMessage));
                startActivity(Intent.createChooser(sharingIntent,getResources().getString(R.string.ShareUsing)));
                break;
            case R.id.rateButton:
                Intent intent1 = new Intent(getApplicationContext(),RatingActivity.class);
                startActivity(intent1);
                break;
            case R.id.ExitButtonid:
                finish();
                break;
            case R.id.ClearAllVar:
                if(((GlobalValues)getApplication()).GetLastIndex()!=0){
                    final Snackbar   snackbar;
                    snackbar = Snackbar.make(findViewById(R.id.MainContent),R.string.Warning9,Snackbar.LENGTH_LONG);
                    snackbar.show();
                    snackbar.setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                            ((GlobalValues) getApplication()).ClearAllMatrix();
                        }
                    });
                    }
                else
                    Toast.makeText(getApplication(),R.string.Warning8,Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       switch (id)
       {
           case R.id.add_sub :
               break;
           case R.id.Swap :
               break;
           case R.id.multiply:
               break;
           case R.id.exponent:
               break;
           case R.id.determinant:
               break;
           case R.id.inverse:
               break;
           case R.id.adjoint:
               break;
           case R.id.functional:
               break;
           case R.id.custom:
               break;
           case R.id.nav_help:
               startActivity(new Intent(getApplicationContext(),faqs.class));
               break;
           case R.id.nav_feedback:
               startActivity(new Intent(getApplicationContext(),FeedBack.class));
               break;

       }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestcode,int resultCode,Intent data)
    {
        super.onActivityResult(resultCode,resultCode,data);
        if(resultCode==RESULT)
        {
            if(data!=null) {
                Toast.makeText(getApplicationContext(), "Variable Created", Toast.LENGTH_SHORT).show();
                Bundle AllData=new Bundle();
                AllData.putAll(data.getExtras());
                Matrix m=new Matrix();
                m.SetFromBundle(AllData);
                ((GlobalValues) getApplication()).AddToGlobal(m); //Sending the things to Global Reference

            }
        }
        if(resultCode==100)
            this.recreate(); // Recreate this Activity if a Change in Theme has been marked

    }
}
