package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.q_recipe.WebServices.PostOperations;

public class AddIngredientsActivity extends AppCompatActivity {
    private EditText textboxIngredientName;
    private Button buttonAddIngredients;
    private PostOperations postOperations = new PostOperations();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);
        textboxIngredientName = findViewById(R.id.textboxIngredientName);
        buttonAddIngredients = findViewById(R.id.buttonAddIngredients);
        getSupportActionBar().hide();



        buttonAddIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postOperations.addIngredient(AddIngredientsActivity.this, String.valueOf(textboxIngredientName.getText()));

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.ltor, R.anim.fade_out);
    }
}