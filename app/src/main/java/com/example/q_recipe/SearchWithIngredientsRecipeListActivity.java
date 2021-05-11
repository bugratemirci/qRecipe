package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.q_recipe.Business.SearchRecipe;
import com.example.q_recipe.WebServices.PostOperations;

import java.util.ArrayList;

public class SearchWithIngredientsRecipeListActivity extends AppCompatActivity {
    private ListView listviewRecipeListByIngredients;
    private ArrayList<String> ingredientsList;
    private PostOperations postOperations = new PostOperations();
    private SearchRecipe searchRecipe = new SearchRecipe();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_with_ingredients_recipe_list);
        listviewRecipeListByIngredients = findViewById(R.id.listviewRecipeListByIngredients);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        ingredientsList = intent.getStringArrayListExtra("ingredientsList");
        postOperations.getRecipeByIngredients(SearchWithIngredientsRecipeListActivity.this, ingredientsList, listviewRecipeListByIngredients, searchRecipe);

        listviewRecipeListByIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentDetail = new Intent(SearchWithIngredientsRecipeListActivity.this, RecipeDetailActivity.class);

                intentDetail.putExtra("selectedRecipe", searchRecipe.getIngredients()[position]);

                startActivity(intentDetail);
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