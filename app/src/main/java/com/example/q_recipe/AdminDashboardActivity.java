package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        getSupportActionBar().hide();

    }
}