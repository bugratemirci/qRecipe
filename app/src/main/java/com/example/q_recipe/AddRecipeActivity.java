package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.Business.RecipeDetails;

public class AddRecipeActivity extends AppCompatActivity {
    private EditText textboxNewRecipeName, textboxNewRecipeDescription;
    private Button buttonNewRecipeChooseMaterials;

    private RecipeDetails recipeDetails = new RecipeDetails();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        getSupportActionBar().hide();

        textboxNewRecipeName = findViewById(R.id.textboxNewRecipeName);
        textboxNewRecipeDescription = findViewById(R.id.textboxNewRecipeDescription);
        buttonNewRecipeChooseMaterials = findViewById(R.id.buttonNewRecipeChooseMaterials);


        Intent intent = getIntent();
        LoggedInUser loggedInUser = (LoggedInUser) intent.getSerializableExtra("user");
        buttonNewRecipeChooseMaterials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChooseMaterials = new Intent(AddRecipeActivity.this, ChooseMaterialsActivity.class);
                recipeDetails.setRecipeName(String.valueOf(textboxNewRecipeName.getText()));
                recipeDetails.setRecipeDescription(String.valueOf(textboxNewRecipeDescription.getText()));
                intentChooseMaterials.putExtra("user", loggedInUser);
                intentChooseMaterials.putExtra("recipe", recipeDetails);

                startActivity(intentChooseMaterials);
            }
        });


    }
}