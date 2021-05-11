package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.q_recipe.Models.User;
import com.example.q_recipe.WebServices.PutOperations;

public class EditUserActivity extends AppCompatActivity {
    private EditText textboxNameEdit, textboxEmailEdit, textboxPasswordEdit, textboxPhoneEdit, textboxAboutMeEdit;
    private Button buttonSaveEdit;
    private PutOperations putOperations = new PutOperations();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        getSupportActionBar().hide();


        textboxNameEdit = findViewById(R.id.textboxNameEdit);
        textboxEmailEdit = findViewById(R.id.textboxEmailEdit);
        textboxPasswordEdit = findViewById(R.id.textboxPasswordEdit);
        textboxPhoneEdit = findViewById(R.id.textboxPhoneEdit);
        textboxAboutMeEdit = findViewById(R.id.textboxAboutMeEdit);

        buttonSaveEdit = findViewById(R.id.buttonSaveEdit);


        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("selectedUser");

        textboxNameEdit.setText(user.getName());
        textboxAboutMeEdit.setText(user.getAbout());
        textboxEmailEdit.setText(user.getEmail());
        textboxPhoneEdit.setText(user.getPhone());

        buttonSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putOperations.editProfileAdmin(
                        EditUserActivity.this,
                        user.getId(),
                        String.valueOf(textboxNameEdit.getText()),
                        String.valueOf(textboxEmailEdit.getText()),
                        String.valueOf(textboxPasswordEdit.getText()),
                        String.valueOf(textboxAboutMeEdit.getText()),
                        String.valueOf(textboxPhoneEdit.getText()));
                Intent intent = new Intent(EditUserActivity.this, AdminDashboardUserOperationsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
            }


        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.ltor, R.anim.fade_out);
    }
}