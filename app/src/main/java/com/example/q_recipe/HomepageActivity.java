package com.example.q_recipe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.WebServices.GetOperations;

public class HomepageActivity extends AppCompatActivity {
    private LoggedInUser loggedInUser;
    private CardView imageButtonProfile, imageButtonAllRecipes, imageButtonAddRecipe, imageButtonMainPageSearchButton;
    private GetOperations getOperations = new GetOperations();

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
        imageButtonMainPageSearchButton = findViewById(R.id.imageButtonMainPageSearchButton);

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

        imageButtonMainPageSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(HomepageActivity.this, SearchWithIgredientsActivity.class);
                intentProfile.putExtra("user", loggedInUser);

                startActivity(intentProfile);
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomepageActivity.this);
        builder.setMessage("Çıkmak istediğinize emin misiniz?");
        builder.setTitle("Uyarı");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getOperations.logOut(HomepageActivity.this);
                Intent intent = new Intent(HomepageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}