package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.q_recipe.Business.LoggedInUser;

public class HomepageActivity extends AppCompatActivity {
    private LoggedInUser loggedInUser;
    private ImageButton imageButtonProfile, imageButtonAllRecipes, imageButtonAddRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        loggedInUser = (LoggedInUser) intent.getSerializableExtra("user");

        imageButtonAllRecipes = findViewById(R.id.imageButtonAllRecipes);
        imageButtonAddRecipe = findViewById(R.id.imageButtonAddRecipe);
        imageButtonProfile = findViewById(R.id.imageButtonProfile);

        imageButtonAllRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAllRecipes = new Intent(HomepageActivity.this, MainPageActivity.class);
                intentAllRecipes.putExtra("user", loggedInUser);

                startActivity(intentAllRecipes);
            }
        });

        imageButtonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddRecipe = new Intent(HomepageActivity.this, AddRecipeActivity.class);
                intentAddRecipe.putExtra("user", loggedInUser);

                startActivity(intentAddRecipe);
            }
        });

        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(HomepageActivity.this, UserProfileActivity.class);
                intentProfile.putExtra("user", loggedInUser);

                startActivity(intentProfile);
            }
        });





    }
}