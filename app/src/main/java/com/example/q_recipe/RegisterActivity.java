package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.q_recipe.WebServices.PostOperations;
import com.google.firebase.iid.FirebaseInstanceId;

public class RegisterActivity extends AppCompatActivity {
    private Button buttonSaveRegister;
    private EditText textboxNameRegister, textboxEmailRegister, textboxPasswordRegister, textboxPhoneRegister, textboxAboutMeRegister;
    private PostOperations postOperations;
    private TextView labelRegisterWarning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        buttonSaveRegister = findViewById(R.id.buttonSaveRegister);
        textboxNameRegister = findViewById(R.id.textboxNameRegister);
        textboxEmailRegister = findViewById(R.id.textboxEmailRegister);
        textboxPasswordRegister = findViewById(R.id.textboxPasswordRegister);
        labelRegisterWarning = findViewById(R.id.labelRegisterWarning);
        textboxAboutMeRegister = findViewById(R.id.textboxAboutMeRegister);
        textboxPhoneRegister = findViewById(R.id.textboxPhoneRegister);

        String token = FirebaseInstanceId.getInstance().getToken();
        getSupportActionBar().hide();

        buttonSaveRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postOperations = new PostOperations();
                postOperations.registerOperations(
                        RegisterActivity.this,
                        String.valueOf(textboxNameRegister.getText()),
                        String.valueOf(textboxEmailRegister.getText()),
                        String.valueOf(textboxPasswordRegister.getText()),
                        labelRegisterWarning,
                        token,
                        String.valueOf(textboxPhoneRegister.getText()),
                        String.valueOf(textboxAboutMeRegister.getText()));
            }
        });

    }
}