package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.example.q_recipe.WebServices.PostOperations;

import java.io.IOException;

public class UploadPictureActivity extends AppCompatActivity {
    private Button buttonUploadImage;
    private static int RESULT_LOAD_IMAGE = 1;
    private PostOperations postOperations = new PostOperations();
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);
        buttonUploadImage = findViewById(R.id.buttonUploadImage);

        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
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
                postOperations.updateRecipePicture(UploadPictureActivity.this,
                        Bitmap.createScaledBitmap(bitmap, 500, 500, false),
                        "Bearer: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwOTNjMTRmZDE1MDM3MGFjOGQzYjdkYyIsIm5hbWUiOiJSZWNlcCBQb2xhdCIsImlhdCI6MTYyMDM4NzcwMywiZXhwIjoxNjIwMzkxMzAzfQ.TvSTGu3PUvjHVDSp2VN1Om4JL5Qc7IVLRWJXNKvPTUE",
                        "6093c4e9d150370ac8d3b7dd",
                        "6093c14fd150370ac8d3b7dc");



            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}