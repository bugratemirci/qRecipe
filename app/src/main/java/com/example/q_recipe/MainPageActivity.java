package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.Business.RecipeDetails;
import com.example.q_recipe.Models.Recipe;
import com.example.q_recipe.WebServices.GetOperations;
import com.example.q_recipe.WebServices.PostOperations;
import com.imangazaliev.slugify.Slugify;

import java.io.Serializable;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {

    private ListView listviewWithoutLoginRecipes;
    private GetOperations getOperations = new GetOperations();
    private List<Recipe> recipeList;
    private String[] names;
    private String[] ids;
    private EditText textboxWithoutLoginSearch;
    private Slugify slugify = new Slugify();

    private LoggedInUser loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        listviewWithoutLoginRecipes = findViewById(R.id.listviewWithoutLoginRecipes1);
        textboxWithoutLoginSearch = findViewById(R.id.textboxWithoutLoginSearch);

        getSupportActionBar().hide();
        Intent intent = getIntent();
        loggedInUser = (LoggedInUser) intent.getSerializableExtra("user");

        listviewWithoutLoginRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecipeDetails recipeDetails = new RecipeDetails();
                Intent intentDetail = new Intent(MainPageActivity.this, RecipeDetailActivity.class);

                intentDetail.putExtra("selectedRecipe", ids[position]);

                startActivity(intentDetail);
            }
        });

        fillListview();

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
            ids = new String[recipeList.size()];

            for(int i = 0; i<recipeList.size();i++){
                names[i] = recipeList.get(i).getName();
                ids[i] = recipeList.get(i).getId();
            }

            ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, names);
            listviewWithoutLoginRecipes.setAdapter(recipeNamesAdapter);
        }
        else{
            List<Recipe> recipes = getOperations.getRecipeByName(slugify.slugify(String.valueOf(textboxWithoutLoginSearch.getText())));

            names = new String[recipes.size()];
            ids = new String[recipes.size()];

            for(int i = 0; i<recipes.size();i++){
                names[i] = recipes.get(i).getName();
                ids[i] = recipes.get(i).getId();
            }

            ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, names);
            listviewWithoutLoginRecipes.setAdapter(recipeNamesAdapter);

        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomepageActivity.class);
        intent.putExtra("user", loggedInUser);
        startActivity(intent);
    }
}