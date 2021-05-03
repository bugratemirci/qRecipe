package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.Models.User;
import com.example.q_recipe.WebServices.GetOperations;

public class UserProfileActivity extends AppCompatActivity {
    private TextView textViewProfileName, textViewProfileEmail, textViewProfileRole;
    private LoggedInUser loggedInUser;
    private Button buttonProfileEdit;

    private User user = null;
    private GetOperations getOperations = new GetOperations();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        loggedInUser = (LoggedInUser) intent.getSerializableExtra("user");
        String id = loggedInUser.getId();
        user = getOperations.getUser(loggedInUser.getId());

        textViewProfileName = findViewById(R.id.textViewProfileName);
        textViewProfileEmail = findViewById(R.id.textViewProfileEmail);
        textViewProfileRole = findViewById(R.id.textViewProfileRole);
        buttonProfileEdit = findViewById(R.id.buttonProfileEdit);

        textViewProfileName.setText(user.getName());
        textViewProfileEmail.setText(user.getEmail());
        textViewProfileRole.setText(user.getRole());

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
}