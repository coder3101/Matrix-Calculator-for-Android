/*
 * Copyright (C) 2017 Ashar Khan <ashar786khan@gmail.com>
 *
 * This file is part of Matrix Calculator.
 *
 * Matrix Calculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Matrix Calculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Matrix Calculator.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.softminds.matrixcalculatorpro;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.softminds.matrixcalculatorpro.OperationFragments.CloneFragment;
import com.softminds.matrixcalculatorpro.OperationFragments.RankFragment;
import com.softminds.matrixcalculatorpro.base_activities.AboutMe;
import com.softminds.matrixcalculatorpro.base_activities.FeedBack;
import com.softminds.matrixcalculatorpro.base_activities.GlobalValues;
import com.softminds.matrixcalculatorpro.base_activities.SettingsTab;
import com.softminds.matrixcalculatorpro.base_activities.faqs;
import com.softminds.matrixcalculatorpro.base_fragments.MainActivityFragmentList;
import com.softminds.matrixcalculatorpro.dialog_activity.DialogConfirmation;
import com.softminds.matrixcalculatorpro.dialog_activity.MakeNewMatrix;
import com.softminds.matrixcalculatorpro.OperationFragments.AdditionFragement;
import com.softminds.matrixcalculatorpro.OperationFragments.AdjointFragment;
import com.softminds.matrixcalculatorpro.OperationFragments.DeterminantFragment;
import com.softminds.matrixcalculatorpro.OperationFragments.ExponentFragment;
import com.softminds.matrixcalculatorpro.OperationFragments.FunctionalFragment;
import com.softminds.matrixcalculatorpro.OperationFragments.InverseFragment;
import com.softminds.matrixcalculatorpro.OperationFragments.MultiplyFragment;
import com.softminds.matrixcalculatorpro.OperationFragments.SubtractionFragment;
import com.softminds.matrixcalculatorpro.OperationFragments.SwapFragment;
import com.softminds.matrixcalculatorpro.OperationFragments.ScalerFragment;
import com.softminds.matrixcalculatorpro.OperationFragments.TransposeFragment;

import java.io.PrintWriter;
import java.io.StringWriter;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final int RESULT=1;
    Menu ActionbarMenu; //the Menu items in the top 3 dots
    ActionBar actionBar; //the Main Activity Actionbar
    TextView t; //Center Text which describe the context of the Application

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

        t = (TextView)findViewById(R.id.OpeningHint);

        if(isDark)
            t.setTextColor(ContextCompat.getColor(this,R.color.white));

        else
            t.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!((GlobalValues)getApplication()).GetCompleteList().isEmpty())
            t.setText(null);

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

        if(savedInstanceState==null)
        {
            MainActivityFragmentList mh=new MainActivityFragmentList();
            getSupportFragmentManager().beginTransaction().add(R.id.MainContent,mh,"MAIN_LIST").commit();
        }


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
        this.ActionbarMenu = menu;
        this.actionBar = getSupportActionBar();
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
                sharingIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.ShareMessage)+getPackageName());
                startActivity(Intent.createChooser(sharingIntent,getResources().getString(R.string.ShareUsing)));
                break;
            case R.id.rateButton:
                try {
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(intent1);
                }catch (ActivityNotFoundException e){
                    Log.d("ActivityNotFound","PlayStore is not installed, opening browser");
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;
            case R.id.ExitButtonid:
                Bundle Infor = new Bundle();
                int ResultCode1=55;
                Infor.putInt("MESSAGE",R.string.WarningExit);
                Infor.putInt("CONFIRM_TEXT",R.string.Yup);
                Infor.putInt("RESULT_CODE",ResultCode1);
                Infor.putInt("CANCEL_TEXT",R.string.CancelCustomFill);
                Intent intent31 = new Intent(getApplicationContext(), DialogConfirmation.class);
                intent31.putExtras(Infor);
                startActivityForResult(intent31,ResultCode1);
                break;
            case R.id.ClearAllVar:
                if(((GlobalValues)getApplication()).GetLastIndex()!=0){
                    Bundle Info = new Bundle();
                    int ResultCode=550;
                    Info.putInt("MESSAGE",R.string.Warning9);
                    Info.putInt("CONFIRM_TEXT",R.string.Yup);
                    Info.putInt("RESULT_CODE",ResultCode);
                    Info.putInt("CANCEL_TEXT",R.string.CancelCustomFill);
                    Intent intent3 = new Intent(getApplicationContext(), DialogConfirmation.class);
                    intent3.putExtras(Info);
                    startActivityForResult(intent3,ResultCode);

                }
                else
                    Toast.makeText(getApplication(),R.string.Warning8,Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          int id = item.getItemId();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.MainFAB);

       switch (id)
       {
           case R.id.Home:
               //setting the fragment
               MainActivityFragmentList mh=new MainActivityFragmentList();
               getSupportFragmentManager().beginTransaction().replace(R.id.MainContent,mh,"MAIN_LIST").commit();
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(true);
               actionBar.setTitle(R.string.app_name);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   actionBar.setSubtitle(null);
               else
                   actionBar.setSubtitle(R.string.MainSubtitle);
               fab.show();
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint);
               else
                   t.setText(null);
               break;
           case R.id.add_sub :
               //setting fragment
               FragmentTransaction AdditionTransaction = getSupportFragmentManager().beginTransaction();
               AdditionFragement additionFragement = new AdditionFragement();
               AdditionTransaction.replace(R.id.MainContent, additionFragement,"ADDITION_FRAGMENT");
               AdditionTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               AdditionTransaction.commit();
               //setting actionbar
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setTitle(R.string.addsub);
               actionBar.setSubtitle(null);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
                    t.setText(null);
               fab.hide();
               break;
           case R.id.only_sub:
               //setting fragment
               FragmentTransaction SubtractionTransaction = getSupportFragmentManager().beginTransaction();
               SubtractionFragment subtractionFragment = new SubtractionFragment();
               SubtractionTransaction.replace(R.id.MainContent, subtractionFragment,"SUBTRACTION_FRAGMENT");
               SubtractionTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               SubtractionTransaction.commit();
               //setting actionbar
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setTitle(R.string.onlysub);
               actionBar.setSubtitle(null);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
                   t.setText(null);
               fab.hide();
               break;
           case R.id.Clone:
               //setting fragment
               FragmentTransaction clonetransaction = getSupportFragmentManager().beginTransaction();
               CloneFragment cloneFragment = new CloneFragment();
               clonetransaction.replace(R.id.MainContent,cloneFragment,"CLONE_FRAGMENT");
               clonetransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               clonetransaction.commit();
               //setting Actionar
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setTitle(R.string.clone);
               actionBar.setSubtitle(null);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
                   t.setText(null);
               fab.hide();
               break;
           case R.id.Transpose:
               FragmentTransaction  transposeTransaction = getSupportFragmentManager().beginTransaction();
               TransposeFragment transposeFragment = new TransposeFragment();
               transposeTransaction.replace(R.id.MainContent, transposeFragment,"TRANSPOSE_FRAGMENT");
               transposeTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               transposeTransaction.commit();
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setTitle(R.string.transpose);
               actionBar.setSubtitle(null);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
                   t.setText(null);
               fab.hide();
               break;
           case R.id.Swap :
               FragmentTransaction  swapTransaction = getSupportFragmentManager().beginTransaction();
               SwapFragment swapFragment = new SwapFragment();
               swapTransaction.replace(R.id.MainContent, swapFragment,"SWAP_FRAGMENT");
               swapTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               swapTransaction.commit();
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setTitle(R.string.swap);
               actionBar.setSubtitle(null);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
                t.setText(null);
               fab.hide();
               break;
           case R.id.multiply:
               FragmentTransaction  multiplyTransaction = getSupportFragmentManager().beginTransaction();
               MultiplyFragment multiplyFragment = new MultiplyFragment();
               multiplyTransaction.replace(R.id.MainContent, multiplyFragment,"MULTIPLY_FRAGMENT");
               multiplyTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               multiplyTransaction.commit();
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setSubtitle(null);
               actionBar.setTitle(R.string.multiply);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
                   t.setText(null);
               fab.hide();
               break;
           case R.id.exponent:
               FragmentTransaction ExponentTransaction = getSupportFragmentManager().beginTransaction();
               ExponentFragment ef = new ExponentFragment();
               ExponentTransaction.replace(R.id.MainContent, ef,"EXPONENT_FRAGMENT");
               ExponentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               ExponentTransaction.commit();
               //Modify the Actionbar
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setTitle(R.string.exponent);
               actionBar.setSubtitle(null);
               //if else Ladder
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
               {
                   if(isAnyVariableSquare())
                       t.setText(null);
                   else
                       t.setText(R.string.NoSupport);
               }
               fab.hide();
               break;
           case R.id.determinant:
               FragmentTransaction DeterminantTransaction= getSupportFragmentManager().beginTransaction();
               DeterminantFragment df = new DeterminantFragment();
               DeterminantTransaction.replace(R.id.MainContent, df,"DETERMINANT_FRAGMENT");
               DeterminantTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               DeterminantTransaction.commit();
               //Modify the Actionbar
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setTitle(R.string.determinant);
               actionBar.setSubtitle(null);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
                {
                    if(isAnyVariableSquare())
                         t.setText(null);
                    else
                         t.setText(R.string.NoSupport);
                    }
               fab.hide();
               break;
           case R.id.RankofMat:
               FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
               RankFragment rankFragment = new RankFragment();
               transaction.replace(R.id.MainContent,rankFragment,"RANK_FRAGMENT");
               transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               transaction.commit();
               //Actionbar
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setSubtitle(null);
               actionBar.setTitle(R.string.Rankof);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
                   t.setText(null);
               fab.hide();
               break;
           case R.id.inverse:
               FragmentTransaction InverseTransaction= getSupportFragmentManager().beginTransaction();
               InverseFragment inv = new InverseFragment();
               InverseTransaction.replace(R.id.MainContent, inv,"INVERSE_FRAGMENT");
               InverseTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               InverseTransaction.commit();
               //Modify the Actionbar
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setTitle(R.string.inverse);
               actionBar.setSubtitle(null);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
               {
                   if(isAnyVariableSquare())
                       t.setText(null);
                   else
                       t.setText(R.string.NoSupport);
               }
               fab.hide();
               break;
           case R.id.adjoint:
               FragmentTransaction AdjointTransaction= getSupportFragmentManager().beginTransaction();
               AdjointFragment af = new AdjointFragment();
               AdjointTransaction.replace(R.id.MainContent, af,"ADJOINT_FRAGMENT");
               AdjointTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               AdjointTransaction.commit();
               //Modify the Actionbar
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setTitle(R.string.adjoint);
               actionBar.setSubtitle(null);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
               {
                   if(isAnyVariableSquare())
                       t.setText(null);
                   else
                       t.setText(R.string.NoSupport);
               }
               fab.hide();
               break;
           case R.id.ScalerMulti:
               FragmentTransaction ScalerTransaction= getSupportFragmentManager().beginTransaction();
               ScalerFragment sf = new ScalerFragment();
               ScalerTransaction.replace(R.id.MainContent, sf,"SCALAR_FRAGMENT");
               ScalerTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               ScalerTransaction.commit();
               //Modify the Actionbar
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setTitle(R.string.Scaler);
               actionBar.setSubtitle(null);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
               {
                   if(isAnyVariableSquare())
                       t.setText(null);
                   else
                       t.setText(R.string.NoSupport);
               }
               fab.hide();
               break;
           case R.id.functional:
               FragmentTransaction FunctionalTransaction= getSupportFragmentManager().beginTransaction();
               FunctionalFragment ff = new FunctionalFragment();
               FunctionalTransaction.replace(R.id.MainContent, ff,"FUNCTIONAL_FRAGMENT");
               FunctionalTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
               FunctionalTransaction.commit();
               //Modify the Actionbar
               ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
               actionBar.setTitle(R.string.function);
               actionBar.setSubtitle(null);
               if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                   t.setText(R.string.OpenHint2);
               else
               {
                   if(isAnyVariableSquare())
                       t.setText(null);
                   else
                       t.setText(R.string.NoSupport);
               }
               fab.hide();
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
        super.onActivityResult(requestcode,resultCode,data);
        if(resultCode==RESULT)
        {
            if(data!=null) {
                Bundle AllData=new Bundle();
                AllData.putAll(data.getExtras());
                Matrix m=new Matrix();
                try{
                    m.SetFromBundle(AllData);
                    ((GlobalValues) getApplication()).AddToGlobal(m); //Sending the things to Global Reference
                    if(actionBar.getSubtitle()==null)
                        actionBar.setSubtitle(R.string.MainSubtitle);
                    t.setText(null);
                    Toast.makeText(getApplicationContext(), R.string.Created, Toast.LENGTH_SHORT).show();
                }catch (ClassCastException e){
                    if(Build.VERSION.SDK_INT<19) {
                        Toast.makeText(getApplication(),"This Application only Supports Android 4.4.4+ ",Toast.LENGTH_SHORT).show();
                        FirebaseCrash.log("Unsupported Device reported ClassCastException ");
                        FirebaseCrash.report(e);
                    }
                    else {
                        FirebaseCrash.log("Critical Bug from a Supported API ");
                        FirebaseCrash.report(e);
                        Toast.makeText(getApplicationContext(),"The Bug has been Reported to the Developer",Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
        if(resultCode==100)
            this.recreate(); // Recreate this Activity if a Change in Theme has been marked
        if(resultCode ==550) //Clear all Variable if this is Recieved
        {
            ((GlobalValues) getApplication()).ClearAllMatrix();
            actionBar.setSubtitle(null);
            ((GlobalValues)getApplication()).AutoSaved=1; //Reinitilize AutoSave to 1
            TextView t = (TextView) findViewById(R.id.OpeningHint);
            t.setText(R.string.OpenHint);
        }
        if(resultCode == 55) //User wants to exit the application
        {
            finish();
        }

    }
    protected boolean isAnyVariableSquare()
    {
        for(int i = 0; i <((GlobalValues)getApplication()).GetCompleteList().size(); i++)
            if(((GlobalValues)getApplication()).GetCompleteList().get(i).is_squareMatrix())
                return true;
        return false;
    }
    public void SetMainActivity(boolean actionmenu,String MainTitle,String subtitle){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.MainFAB);
        ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(actionmenu);
        actionBar.setTitle(MainTitle);
        if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
            actionBar.setSubtitle(null);
        else
            actionBar.setSubtitle(subtitle);
        fab.show();
        if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
            t.setText(R.string.OpenHint);
        else
            t.setText(null);
    }
}

