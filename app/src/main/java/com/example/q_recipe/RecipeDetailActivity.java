package com.example.q_recipe;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.q_recipe.Business.RecipeDetails;
import com.example.q_recipe.Helpers.SliderAdapter;
import com.example.q_recipe.Models.Recipe;
import com.example.q_recipe.WebServices.GetOperations;
import com.example.q_recipe.WebServices.PostOperations;

import java.io.IOException;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView viewPagerDetailRecipe;
    private String recipeId;
    private GetOperations getOperations = new GetOperations();
    private PostOperations postOperations = new PostOperations();
    private TextView recipeDescriptionDetailPage, recipeNameDetailPage;
    private ListView recipeIngredientsDetailPage;
    private Bitmap bitmap;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        getSupportActionBar().hide();

        recipeDescriptionDetailPage = findViewById(R.id.recipeDescriptionDetailPage);
        recipeNameDetailPage = findViewById(R.id.recipeNameDetailPage);
        recipeIngredientsDetailPage = findViewById(R.id.recipeIngredientsDetailPage);
        viewPagerDetailRecipe = findViewById(R.id.viewPagerDetailRecipe);


        Intent intent = getIntent();
        recipeId = intent.getStringExtra("selectedRecipe");


        postOperations.getRecipeWithId(RecipeDetailActivity.this, recipeId, recipeNameDetailPage, recipeDescriptionDetailPage, recipeIngredientsDetailPage, viewPagerDetailRecipe);

        /*viewPagerDetailRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });*/

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            try {

                PostOperations postOperations = new PostOperations();

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse("file:///"+picturePath));
                postOperations.updateRecipeImages(RecipeDetailActivity.this,
                        Bitmap.createScaledBitmap(bitmap, 500, 500, false),
                        recipeId);
                viewPagerDetailRecipe.setImageDrawable(new BitmapDrawable(getApplicationContext().getResources(), bitmap));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}