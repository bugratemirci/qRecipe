package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChooseMaterialsActivity extends AppCompatActivity {
    private ListView listviewRecipeMaterials, listviewSelectedRecipeMaterials;
    private Button buttonUploadAPicture;
    private ArrayList<String> ingredients = new ArrayList<>();
    private ArrayList<String> selectedIngredients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_materials);
        getSupportActionBar().hide();
        ingredients.add("Tuz");
        ingredients.add("Karabiber");
        ingredients.add("Kekik");
        ingredients.add("Limon");


        buttonUploadAPicture = findViewById(R.id.buttonUploadAPicture);
        listviewRecipeMaterials = findViewById(R.id.listviewRecipeMaterials);
        listviewSelectedRecipeMaterials = findViewById(R.id.listviewSelectedRecipeMaterials);
        selectedIngredients = new ArrayList<>();
        fillListView();

        listviewRecipeMaterials.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIngredients.add(String.valueOf(ingredients.toArray()[position]));
                selectedIngredients.toArray();
                fillListView();
            }
        });
        listviewSelectedRecipeMaterials.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIngredients.remove(selectedIngredients.toArray()[position]);
                fillListView();
            }
        });

        buttonUploadAPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void fillListView(){
        ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, ingredients);
        listviewRecipeMaterials.setAdapter(recipeNamesAdapter);
        if(selectedIngredients.size() != 0){
            ArrayAdapter<String> selectedRecipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, selectedIngredients);
            listviewSelectedRecipeMaterials.setAdapter(selectedRecipeNamesAdapter);
        }

    }
}