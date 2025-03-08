package com.example.unisaplibraryapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {
    TextView homepage_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage); // Use the new layout file

        homepage_text = (android.widget.TextView) findViewById(R.id.homepage_text);
    }
    @SuppressLint("SetTextI18n")
    public void changeHeader(View view){
        homepage_text.setText("testButton");
        System.out.println("testButton clicked!");
    }
}