package com.jalmaksa.bm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Version extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        getSupportActionBar().hide();

    }
}
