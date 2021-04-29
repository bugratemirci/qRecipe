package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.Models.Recipe;
import com.example.q_recipe.WebServices.GetOperations;
import com.imangazaliev.slugify.Slugify;

import java.io.Serializable;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {

    private ListView listviewWithoutLoginRecipes;
    private GetOperations getOperations = new GetOperations();
    private List<Recipe> recipeList;
    private String[] names;
    private EditText textboxWithoutLoginSearch;
    private Slugify slugify = new Slugify();
    private Button buttonAddRecipe;
    private LoggedInUser loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        listviewWithoutLoginRecipes = findViewById(R.id.listviewWithoutLoginRecipes);
        textboxWithoutLoginSearch = findViewById(R.id.textboxWithoutLoginSearch);
        buttonAddRecipe = findViewById(R.id.buttonAddRecipe);

        Intent intent = getIntent();
        loggedInUser = (LoggedInUser) intent.getSerializableExtra("user");
        if(loggedInUser == null) {

            buttonAddRecipe.setVisibility(View.INVISIBLE);
        }
        else {
            buttonAddRecipe.setVisibility(View.VISIBLE);

        }


        fillListview();
        buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRecipeIntent = new Intent(MainPageActivity.this, AddRecipeActivity.class);
                addRecipeIntent.putExtra("user",(Serializable) loggedInUser);
                startActivity(addRecipeIntent);
            }
        });
        textboxWithoutLoginSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

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