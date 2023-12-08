package com.nivek.kevoweather;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static final String TAG ="SingleFragmentActivity";
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check for the existence of the fragment using the fragment manager
        FragmentManager fm = getSupportFragmentManager() ;
        Fragment fragment = fm.findFragmentById(R.id.recyClerViewContainer);
        Log.i(TAG,"Activity was created");
        if(fragment == null){
            Log.i(TAG,"Tried creating a new fragment") ;
            fragment = createFragment() ;
            fm.beginTransaction().add(R.id.recyClerViewContainer , fragment).commit() ;
        }
    }
}
