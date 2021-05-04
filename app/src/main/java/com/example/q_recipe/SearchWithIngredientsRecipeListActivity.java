package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.q_recipe.WebServices.PostOperations;

import java.util.ArrayList;

public class SearchWithIngredientsRecipeListActivity extends AppCompatActivity {
    private ListView listviewRecipeListByIngredients;
    private ArrayList<String> ingredientsList;
    private PostOperations postOperations = new PostOperations();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_with_ingredients_recipe_list);
        listviewRecipeListByIngredients = findViewById(R.id.listviewRecipeListByIngredients);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        ingredientsList = intent.getStringArrayListExtra("ingredientsList");
        postOperations.getRecipeByIngredients(SearchWithIngredientsRecipeListActivity.this, ingredientsList, listviewRecipeListByIngredients);


    }
}