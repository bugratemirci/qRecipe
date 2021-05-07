package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.q_recipe.Models.Ingredient;
import com.example.q_recipe.WebServices.GetOperations;

import java.util.ArrayList;
import java.util.List;

public class SearchWithIgredientsActivity extends AppCompatActivity {
    private Button buttonSearchWithIngredients;
    private ListView listviewSearchIngredientsAll, listviewSearchIngredientsSelected;

    private ArrayList<String> selectedIngredients;
    private EditText textboxSearchIngredient;
    private ArrayList<String> ingredients = new ArrayList<>();
    private GetOperations getOperations = new GetOperations();
    private List<Ingredient> ingredientList;
    private String getNameIngredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_with_igredients);
        listviewSearchIngredientsAll = findViewById(R.id.listviewSearchIngredientsAll);
        listviewSearchIngredientsSelected = findViewById(R.id.listviewSearchIngredientsSelected);
        buttonSearchWithIngredients = findViewById(R.id.buttonSearchWithIngredients);
        textboxSearchIngredient = findViewById(R.id.textboxSearchIngredientAddRecipe);

        getSupportActionBar().hide();


        ingredientList = getOperations.getIngredients();
        for(Ingredient ingredient: ingredientList) {
            ingredients.add(ingredient.getName());
        }

        selectedIngredients = new ArrayList<>();
        fillListView(null);
        textboxSearchIngredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fillListView(s);
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        listviewSearchIngredientsAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedIngredients.add(String.valueOf(listviewSearchIngredientsAll.getItemAtPosition(position)));
                ingredients.remove(String.valueOf(listviewSearchIngredientsAll.getItemAtPosition(position)));
                selectedIngredients.toArray();

                fillListView(null);


            }

        });
        listviewSearchIngredientsSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ingredients.add(String.valueOf(selectedIngredients.toArray()[position]));
                selectedIngredients.remove(String.valueOf(selectedIngredients.toArray()[position]));
                fillListView(null);
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
    public void fillListView(CharSequence charSequence){
        if(charSequence == null){

            ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, ingredients);
            listviewSearchIngredientsAll.setAdapter(recipeNamesAdapter);
        }
        else{
            ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, ingredients);
            recipeNamesAdapter.getFilter().filter(charSequence);
            listviewSearchIngredientsAll.setAdapter(recipeNamesAdapter);
        }
        if(selectedIngredients.size() != 0){
            ArrayAdapter<String> selectedRecipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, selectedIngredients);
            listviewSearchIngredientsSelected.setAdapter(selectedRecipeNamesAdapter);
        }

    }
}