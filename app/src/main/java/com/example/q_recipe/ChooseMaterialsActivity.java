package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.Business.RecipeDetails;
import com.example.q_recipe.Models.Ingredient;
import com.example.q_recipe.Models.Recipe;
import com.example.q_recipe.Models.User;
import com.example.q_recipe.WebServices.GetOperations;
import com.example.q_recipe.WebServices.PostOperations;

import java.util.ArrayList;
import java.util.List;

public class ChooseMaterialsActivity extends AppCompatActivity {
    private ListView listviewRecipeMaterials, listviewSelectedRecipeMaterials;
    private Button buttonUploadAPicture;
    private ArrayList<String> ingredients = new ArrayList<>();
    private ArrayList<String> selectedIngredients;
    private LoggedInUser loggedInUser;
    private RecipeDetails recipeDetails;
    private TextView labelChooseMaterialWarningLabel;
    private GetOperations getOperations = new GetOperations();
    private List<Ingredient> ingredientList;
    private EditText textboxSearchIngredientAddRecipe, textboxNewRecipeDescription;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_materials);
        getSupportActionBar().hide();
        ingredientList = getOperations.getIngredients();

        for(Ingredient ingredient: ingredientList) {
            ingredients.add(ingredient.getName());
        }

        Intent intent = getIntent();
        loggedInUser = (LoggedInUser) intent.getSerializableExtra("user");
        recipeDetails = (RecipeDetails) intent.getSerializableExtra("recipe");
        buttonUploadAPicture = findViewById(R.id.buttonUploadAPicture);
        listviewRecipeMaterials = findViewById(R.id.listviewRecipeMaterials);
        listviewSelectedRecipeMaterials = findViewById(R.id.listviewSelectedRecipeMaterials);
        labelChooseMaterialWarningLabel = findViewById(R.id.labelChooseMaterialWarningLabel);
        textboxSearchIngredientAddRecipe = findViewById(R.id.textboxSearchIngredientAddRecipe);
        textboxNewRecipeDescription = findViewById(R.id.textboxNewRecipeDescription);

        textboxSearchIngredientAddRecipe.addTextChangedListener(new TextWatcher() {
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
        selectedIngredients = new ArrayList<>();
        fillListView(null);
        listviewRecipeMaterials.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIngredients.add(String.valueOf(listviewRecipeMaterials.getItemAtPosition(position)));
                ingredients.remove(String.valueOf(listviewRecipeMaterials.getItemAtPosition(position)));
                selectedIngredients.toArray();

                fillListView(null);
            }
        });
        listviewSelectedRecipeMaterials.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ingredients.add(String.valueOf(selectedIngredients.toArray()[position]));
                selectedIngredients.remove(String.valueOf(selectedIngredients.toArray()[position]));
                fillListView(null);
            }
        });
        buttonUploadAPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostOperations postOperations = new PostOperations();
                postOperations.addRecipe(ChooseMaterialsActivity.this,
                        recipeDetails.getRecipeName(),
                        String.valueOf(textboxNewRecipeDescription.getText()),
                        selectedIngredients,
                        labelChooseMaterialWarningLabel,
                        loggedInUser.getAccess_token(),
                        loggedInUser.getId(),
                        loggedInUser,
                        recipeDetails.getRecipeImage()
                );
                GetOperations getOperations = new GetOperations();
                List<User> userList = getOperations.getUsers();

                for(int i=0; i<userList.size();i++){
                    postOperations.pushNotification(ChooseMaterialsActivity.this,
                            recipeDetails.getRecipeName(),
                            loggedInUser.getName() + " tarafından yeni tarif eklendi",
                            userList.get(i).getNotificationToken(),
                            "AAAAWHco7YE:APA91bGt9bFWiRSsL1DeV8NAf7B5-PiqpmxeWIoBzXUDOPS5x46VhgWoCPk4VlvOEQa6DKr1tj5_LgrptukHbIo1OYpf9Ho5LtlkLsz7kTFte6u9ytpSRVvy9xGgve-MY6q3f3Q-V1mw");
                }
            }
        });
    }

    public void fillListView(CharSequence charSequence){
        if(charSequence == null){

            ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, ingredients);
            listviewRecipeMaterials.setAdapter(recipeNamesAdapter);
        }
        else{
            ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, ingredients);
            recipeNamesAdapter.getFilter().filter(charSequence);
            listviewRecipeMaterials.setAdapter(recipeNamesAdapter);
        }
        if(selectedIngredients.size() != 0){
            ArrayAdapter<String> selectedRecipeNamesAdapter = new  ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, selectedIngredients);
            listviewSelectedRecipeMaterials.setAdapter(selectedRecipeNamesAdapter);
        }

    }

}