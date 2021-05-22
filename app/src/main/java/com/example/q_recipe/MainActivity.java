package com.example.q_recipe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.q_recipe.WebServices.GetOperations;
import com.example.q_recipe.WebServices.PostOperations;



public class MainActivity extends AppCompatActivity {

    private CardView buttonLogin;
    private PostOperations postOperations = new PostOperations();
    private EditText textboxEmailLogin, textboxPasswordLogin;
    private TextView  textViewRegister, textboxLoginWarning;


    private GetOperations getOperations = new GetOperations();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getSupportActionBar().hide();


        buttonLogin = findViewById(R.id.buttonLogin);

        textboxEmailLogin = findViewById(R.id.textboxEmailLogin);
        textboxPasswordLogin = findViewById(R.id.textboxPasswordLogin);
        textboxLoginWarning = findViewById(R.id.textboxLoginWarning);
        textViewRegister = findViewById(R.id.textViewRegister);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);

            }
        });
        

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postOperations.loginOperations(MainActivity.this,String.valueOf(textboxEmailLogin.getText()), String.valueOf(textboxPasswordLogin.getText()), textboxLoginWarning);

            }
        });

    }
    @Override
    public void onBackPressed() {

    }
}