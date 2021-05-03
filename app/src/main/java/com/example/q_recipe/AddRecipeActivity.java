package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.WebServices.PostOperations;

public class AddRecipeActivity extends AppCompatActivity {
    private EditText textboxNewRecipeName, textboxNewRecipeDescription;
    private Button buttonNewRecipeIngredients, buttonNewRecipeSave;
    private ImageButton imageButtonRecipeImage;
    private String[] ingredients = new String[]{"Tuz", "Karabiber"};
    private TextView labelWarningAddRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        textboxNewRecipeName = findViewById(R.id.textboxNewRecipeName);
        textboxNewRecipeDescription = findViewById(R.id.textboxNewRecipeDescription);
        buttonNewRecipeIngredients = findViewById(R.id.buttonNewRecipeIngredients);
        buttonNewRecipeSave = findViewById(R.id.buttonNewRecipeSave);
        imageButtonRecipeImage = findViewById(R.id.imageButtonRecipeImage);
        labelWarningAddRecipe = findViewById(R.id.labelWarningAddRecipe);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        LoggedInUser loggedInUser = (LoggedInUser) intent.getSerializableExtra("user");
        buttonNewRecipeSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostOperations postOperations = new PostOperations();
                postOperations.addRecipe(AddRecipeActivity.this,
                        textboxNewRecipeName.getText().toString(),
                        textboxNewRecipeDescription.getText().toString(),
                        ingredients,
                        labelWarningAddRecipe,
                        loggedInUser.getAccess_token(),
                        loggedInUser.getId(),
                        loggedInUser
                        );
            }
        });


    }
}