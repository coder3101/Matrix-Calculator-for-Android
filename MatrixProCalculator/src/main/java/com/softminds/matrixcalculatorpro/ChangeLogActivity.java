package com.softminds.matrixcalculatorpro;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ChangeLogActivity extends AppCompatActivity {

    FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    ProgressDialog pr;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        pr = new ProgressDialog(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.changelog_layout);

        layout = (LinearLayout) findViewById(R.id.changelog_container);

        pr.setMessage(getString(R.string.Wait));
        pr.setIndeterminate(true);
        pr.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pr.setTitle(R.string.Loading);
        pr.show();

        firebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build());
        HashMap<String,Object> defaultNews = new HashMap<>();
        //10 changes
        defaultNews.put("news1","null");
        defaultNews.put("news2","null");
        defaultNews.put("news3","null");
        defaultNews.put("news4","null");
        defaultNews.put("news5","null");
        defaultNews.put("news6","null");
        defaultNews.put("news7","null");
        defaultNews.put("news8","null");
        defaultNews.put("news9","null");
        defaultNews.put("news10","null");
        //to mark the top 3 as latest red for new changes
        defaultNews.put("mark_red",false);

        firebaseRemoteConfig.setDefaults(defaultNews);

        final Task<Void> fetch = firebaseRemoteConfig.fetch(0);
        fetch.addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseRemoteConfig.activateFetched();
                Log.d("RemoteConfig","Change-logs Updated");
                for(int i=0;i<10;i++)
                    SetNewContents(i);
            }
        });

        fetch.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pr.dismiss();
                Log.d("Failed", "Cannot Load the Changes");
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),R.string.InternetPlzz,Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }

    private void SetNewContents(int key){
        if(!Changes(key).equals("null")) {
            CardView.LayoutParams param = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            CardView card = new CardView(this);
            card.setCardElevation(5);
            card.setLayoutParams(param);
            card.setPadding(ConvertTopx(15), ConvertTopx(15), ConvertTopx(15), ConvertTopx(15));
            card.setUseCompatPadding(true);
            TextView changes = new TextView(this);
            changes.setGravity(Gravity.CENTER);
            changes.setPadding(ConvertTopx(5),ConvertTopx(5),ConvertTopx(5),ConvertTopx(5));
            changes.setTypeface(Typeface.DEFAULT_BOLD);
            changes.setText(Changes(key));
            if(firebaseRemoteConfig.getBoolean("mark_red")&& key==0)
                changes.setTextColor(Color.parseColor("#FF0000"));
            card.addView(changes);
            layout.addView(card);
        }
        pr.dismiss();
    }

    private int ConvertTopx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return ((int) (dp * ((float) metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT));

    }
    private String Changes(int k){
        return firebaseRemoteConfig.getString("news"+String.valueOf(k+1));

    }

}
