package com.example.phoneinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ButtonLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_layout);
    }

    public void onClicked(View view) {
        startActivity(new Intent(ButtonLayoutActivity.this,MainActivity.class));
    }
}