package com.example.unisaplibraryapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);
        finish(); // Finish MainActivity so that the user can't return to it
    }
}
