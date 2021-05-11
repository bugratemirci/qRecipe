package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.q_recipe.Models.Recipe;
import com.example.q_recipe.WebServices.DeleteOperations;
import com.example.q_recipe.WebServices.GetOperations;

import org.w3c.dom.Text;

import java.util.List;

public class EditRecipeByAdminActivity extends AppCompatActivity {
    private ListView listviewRecipesAdmin;
    private Button buttonDeleteRecipe;
    private TextView labelSelectedRecipe;
    private GetOperations getOperations = new GetOperations();
    private String[] recipeNames;
    private String[] ids;
    private DeleteOperations deleteOperations = new DeleteOperations();
    private String selectedId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe_by_admin);
        listviewRecipesAdmin = findViewById(R.id.listviewRecipesAdmin);
        buttonDeleteRecipe = findViewById(R.id.buttonDeleteRecipe);
        labelSelectedRecipe = findViewById(R.id.labelSelectedRecipe);
        getSupportActionBar().hide();

        fillListView();
        listviewRecipesAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                labelSelectedRecipe.setText("Seçili tarif:" + recipeNames[position]);
                selectedId = ids[position];
            }
        });

        buttonDeleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!String.valueOf(labelSelectedRecipe).isEmpty()){
                    Vibrator vibrator = (Vibrator) getSystemService(EditRecipeByAdminActivity.this.VIBRATOR_SERVICE);
                    vibrator.vibrate(200);

                    deleteOperations.deleteRecipe(EditRecipeByAdminActivity.this, selectedId, listviewRecipesAdmin);

                    labelSelectedRecipe.setText("");
                }
                else{
                    labelSelectedRecipe.setText("Lütfen silinecek tarifi seçiniz.");
                }
            }
        });
    }
    public void fillListView(){
        List<Recipe> recipeList = getOperations.getRecipes();
        recipeNames = new String[recipeList.size()];
        ids = new String[recipeList.size()];

        for(int i = 0; i<recipeList.size(); i++){
            recipeNames[i] = recipeList.get(i).getName();
            ids[i] = recipeList.get(i).getId();
        }
        ArrayAdapter<String> recipeNamesAdapter = new  ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, recipeNames);
        listviewRecipesAdmin.setAdapter(recipeNamesAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.ltor, R.anim.fade_out);
    }
}