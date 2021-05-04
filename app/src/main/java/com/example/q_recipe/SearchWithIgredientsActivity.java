package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.q_recipe.Models.Ingredient;
import com.example.q_recipe.WebServices.GetOperations;

import java.util.ArrayList;
import java.util.List;

public class SearchWithIgredientsActivity extends AppCompatActivity {
    private Button buttonSearchWithIngredients;
    private ListView listviewSearchIngredientsAll, listviewSearchIngredientsSelected;
    private ArrayList<String> ingredients = new ArrayList<>();
    private ArrayList<String> selectedIngredients;

    private GetOperations getOperations = new GetOperations();
    private List<Ingredient> ingredientList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_with_igredients);
        listviewSearchIngredientsAll = findViewById(R.id.listviewSearchIngredientsAll);
        listviewSearchIngredientsSelected = findViewById(R.id.listviewSearchIngredientsSelected);
        buttonSearchWithIngredients = findViewById(R.id.buttonSearchWithIngredients);
        getSupportActionBar().hide();

        ingredientList = getOperations.getIngredients();
        for(Ingredient ingredient: ingredientList) {
            ingredients.add(ingredient.getName());
        }

        selectedIngredients = new ArrayList<>();
        fillListView();

        listviewSearchIngredientsAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIngredients.add(String.valueOf(ingredients.toArray()[position]));
                ingredients.remove(String.valueOf(ingredients.toArray()[position]));
                selectedIngredients.toArray();
                fillListView();
            }
        });
        listviewSearchIngredientsSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ingredients.add(String.valueOf(selectedIngredients.toArray()[position]));
                selectedIngredients.remove(selectedIngredients.toArray()[position]);
                fillListView();
            }
        });
        buttonSearchWithIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchWithIgredientsActivity.this, SearchWithIngredientsRecipeListActivity.class);
                intent.putExtra("ingredientsList", selectedIngredients);
                startActivity(intent);
            }
        });

    }
    public void fillListView(){
        ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, ingredients);
        listviewSearchIngredientsAll.setAdapter(recipeNamesAdapter);
        if(selectedIngredients.size() != 0){
            ArrayAdapter<String> selectedRecipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, selectedIngredients);
            listviewSearchIngredientsSelected.setAdapter(selectedRecipeNamesAdapter);
        }

    }
}