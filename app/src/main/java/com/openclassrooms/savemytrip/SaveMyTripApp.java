package com.openclassrooms.savemytrip;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

/**
 * Created by Philippe on 28/02/2018.
 */

public class SaveMyTripApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }
    }
}
