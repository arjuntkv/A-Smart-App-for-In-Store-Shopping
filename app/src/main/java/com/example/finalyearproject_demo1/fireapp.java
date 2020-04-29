package com.example.finalyearproject_demo1;

import android.app.Application;

import com.firebase.client.Firebase;

public class fireapp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
