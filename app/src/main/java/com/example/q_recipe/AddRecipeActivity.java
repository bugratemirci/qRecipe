package com.example.q_recipe;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.Business.RecipeDetails;
import com.example.q_recipe.WebServices.PostOperations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddRecipeActivity extends AppCompatActivity {
    private EditText textboxNewRecipeName;
    private Button buttonNewRecipeChooseMaterials;
    private ImageView uploadImageButtonAddRecipe;
    private Bitmap bitmap;
    private RecipeDetails recipeDetails = new RecipeDetails();
    private String imageString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        getSupportActionBar().hide();

        textboxNewRecipeName = findViewById(R.id.textboxNewRecipeName);

        buttonNewRecipeChooseMaterials = findViewById(R.id.buttonNewRecipeChooseMaterials);
        uploadImageButtonAddRecipe = findViewById(R.id.uploadImageButtonAddRecipe);

        Intent intent = getIntent();
        LoggedInUser loggedInUser = (LoggedInUser) intent.getSerializableExtra("user");
        buttonNewRecipeChooseMaterials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChooseMaterials = new Intent(AddRecipeActivity.this, ChooseMaterialsActivity.class);
                recipeDetails.setRecipeName(String.valueOf(textboxNewRecipeName.getText()));
                recipeDetails.setRecipeImage(imageString);
                intentChooseMaterials.putExtra("user", loggedInUser);
                intentChooseMaterials.putExtra("recipe", recipeDetails);

                startActivity(intentChooseMaterials);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
            }
        });
        uploadImageButtonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
            }
        });
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

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse("file:///"+picturePath));
                uploadImageButtonAddRecipe.setImageDrawable(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
                Bitmap.createScaledBitmap(bitmap, 250, 250, false);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();
                imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.ltor, R.anim.fade_out);
    }
}