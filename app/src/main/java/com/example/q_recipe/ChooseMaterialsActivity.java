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

        selectedIngredients = new ArrayList<>();
        fillListView();

        listviewRecipeMaterials.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIngredients.add(String.valueOf(ingredients.toArray()[position]));
                ingredients.remove(String.valueOf(ingredients.toArray()[position]));
                selectedIngredients.toArray();
                fillListView();
            }
        });
        listviewSelectedRecipeMaterials.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ingredients.add(String.valueOf(selectedIngredients.toArray()[position]));
                selectedIngredients.remove(selectedIngredients.toArray()[position]);
                fillListView();
            }
        });

        buttonUploadAPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostOperations postOperations = new PostOperations();
                postOperations.addRecipe(ChooseMaterialsActivity.this,
                        recipeDetails.getRecipeName(),
                        recipeDetails.getRecipeDescription(),
                        selectedIngredients,
                        labelChooseMaterialWarningLabel,
                        loggedInUser.getAccess_token(),
                        loggedInUser.getId(),
                        loggedInUser
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