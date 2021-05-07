package com.example.q_recipe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.ENV.GlobalVariables;
import com.example.q_recipe.Models.User;
import com.example.q_recipe.WebServices.GetOperations;
import com.example.q_recipe.WebServices.PostOperations;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class UserProfileActivity extends AppCompatActivity {
    private TextView textViewProfileName, textViewProfileEmail, textViewProfileAbout, textViewProfilePhone;
    private LoggedInUser loggedInUser;
    private Button buttonProfileEdit;
    private CircularImageView imageViewProfileImage;
    private static int RESULT_LOAD_IMAGE = 1;
    private PostOperations postOperations = new PostOperations();
    private Bitmap bitmap;
    private User user = null;
    private GetOperations getOperations = new GetOperations();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        loggedInUser = (LoggedInUser) intent.getSerializableExtra("user");

        user = getOperations.getUser(loggedInUser.getId());

        textViewProfileName = findViewById(R.id.textViewProfileName);
        textViewProfileEmail = findViewById(R.id.textViewProfileEmail);
        textViewProfileAbout = findViewById(R.id.textViewProfileAbout);
        textViewProfilePhone = findViewById(R.id.textViewProfilePhone);
        buttonProfileEdit = findViewById(R.id.buttonProfileEdit);
        imageViewProfileImage = findViewById(R.id.imageViewProfileImage);

        String originalInput = loggedInUser.getProfile_image();
        originalInput = originalInput.replace("\n","");
        byte[] result = Base64.getDecoder().decode(originalInput);
        Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);

        imageViewProfileImage.setImageBitmap(bitmap);



        textViewProfileName.setText(user.getName());
        textViewProfileEmail.setText(user.getEmail());
        textViewProfileAbout.setText(user.getAbout());
        textViewProfilePhone.setText(user.getPhone());
        imageViewProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });
        buttonProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(UserProfileActivity.this, ProfileEditActivity.class);
                intentEditProfile.putExtra("user", user);
                intentEditProfile.putExtra("user_access_token", loggedInUser.getAccess_token());
                startActivity(intentEditProfile);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
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
                postOperations.updateProfilePicture(UserProfileActivity.this,
                        Bitmap.createScaledBitmap(bitmap, 500, 500, false),
                        loggedInUser.getAccess_token(),
                        loggedInUser.getId(),
                        loggedInUser);
                imageViewProfileImage.setImageDrawable(new BitmapDrawable(getApplicationContext().getResources(), bitmap));



            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomepageActivity.class);
        intent.putExtra("user", loggedInUser);
        startActivity(intent);
    }

}