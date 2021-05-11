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
        buttonSaveRegister = findViewById(R.id.buttonSaveEdit);
        textboxNameRegister = findViewById(R.id.textboxNameEdit);
        textboxEmailRegister = findViewById(R.id.textboxEmailEdit);
        textboxPasswordRegister = findViewById(R.id.textboxPasswordEdit);
        labelRegisterWarning = findViewById(R.id.labelRegisterWarning);
        textboxAboutMeRegister = findViewById(R.id.textboxAboutMeEdit);
        textboxPhoneRegister = findViewById(R.id.textboxPhoneEdit);

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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.ltor, R.anim.fade_out);
    }
}