package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminDashboardActivity extends AppCompatActivity {
    CardView adminPanelAddIngredientButton, adminDashboardUserOperationsButton, adminDashboardRecipeOperationsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        getSupportActionBar().hide();

        adminPanelAddIngredientButton = findViewById(R.id.adminPanelAddIngredientButton);
        adminDashboardUserOperationsButton = findViewById(R.id.adminDashboardUserOperationsButton);
        adminDashboardRecipeOperationsButton = findViewById(R.id.adminDashboardRecipeOperationsButton);

        adminDashboardUserOperationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, AdminDashboardUserOperationsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
            }
        });
        adminPanelAddIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, AddIngredientsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
            }
        });
        adminDashboardRecipeOperationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, EditRecipeByAdminActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.ltor, R.anim.fade_out);
    }
}