package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.q_recipe.Models.Recipe;
import com.example.q_recipe.WebServices.GetOperations;
import com.imangazaliev.slugify.Slugify;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


public class RecipesWithoutLoginActivity extends AppCompatActivity {
    private ListView listviewWithoutLoginRecipes;
    private GetOperations getOperations = new GetOperations();
    private List<Recipe> recipeList;
    private String[] names;
    private TextView textboxWithoutLoginSearch;
    private Slugify slugify = new Slugify();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_without_login);
        listviewWithoutLoginRecipes = findViewById(R.id.listviewWithoutLoginRecipes);
        textboxWithoutLoginSearch = findViewById(R.id.textboxWithoutLoginSearch);

        fillListview();

        textboxWithoutLoginSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    fillListview();
                    return true;
                }
                return false;
            }
        });

        textboxWithoutLoginSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(textboxWithoutLoginSearch.getText().length() == 0){
                    fillListview();
                }
            }
        });

    }

    public void fillListview(){
        if(textboxWithoutLoginSearch.getText().length() == 0){
            recipeList = getOperations.getRecipes();
            names = new String[recipeList.size()];


            for(int i = 0; i<recipeList.size();i++){
                names[i] = recipeList.get(i).getName();
            }

            ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, names);
            listviewWithoutLoginRecipes.setAdapter(recipeNamesAdapter);
        }
        else{
            List<Recipe> recipes = getOperations.getRecipeByName(slugify.slugify(String.valueOf(textboxWithoutLoginSearch.getText())));
            names = new String[recipes.size()];

            for(int i = 0; i<recipes.size();i++){
                names[i] = recipes.get(i).getName();
            }

            ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, names);
            listviewWithoutLoginRecipes.setAdapter(recipeNamesAdapter);

        }
    }

}