package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.q_recipe.Models.User;
import com.example.q_recipe.WebServices.PutOperations;

import org.w3c.dom.Text;

public class ProfileEditActivity extends AppCompatActivity {
    private EditText textboxProfileEditName, textboxProfileEditEmail, textboxProfileEditPassword;
    private Button buttonProfileEditSave;
    private TextView labelEditProfileWarning;

    private PutOperations putOperations = new PutOperations();
    private User user;
    private String access_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        getSupportActionBar().hide();

        textboxProfileEditEmail = findViewById(R.id.textboxProfileEditEmail);
        textboxProfileEditName = findViewById(R.id.textboxProfileEditName);
        textboxProfileEditPassword = findViewById(R.id.textboxProfileEditPassword);
        buttonProfileEditSave = findViewById(R.id.buttonProfileEditSave);
        labelEditProfileWarning = findViewById(R.id.labelEditProfileWarning);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        access_token = intent.getStringExtra("user_access_token");

        buttonProfileEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Id", user.getId());
                putOperations.editProfile(ProfileEditActivity.this,
                        user.getId(),
                        String.valueOf(textboxProfileEditName.getText()),
                        String.valueOf(textboxProfileEditEmail.getText()),
                        String.valueOf(textboxProfileEditPassword.getText()),
                        labelEditProfileWarning,
                        access_token);
            }
        });
    }
}