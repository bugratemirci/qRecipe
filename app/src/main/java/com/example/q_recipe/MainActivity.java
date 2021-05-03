package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.q_recipe.WebServices.PostOperations;
import com.google.firebase.iid.FirebaseInstanceId;


public class MainActivity extends AppCompatActivity {

    private Button buttonLogin, buttonContinueWithoutLogin, buttonRegisterMainPage;
    private PostOperations postOperations = new PostOperations();
    private EditText textboxEmailLogin, textboxPasswordLogin;
    private TextView textViewWarningLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getSupportActionBar().hide();

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonContinueWithoutLogin = findViewById(R.id.buttonContinueWithoutLogin);
        textboxEmailLogin = findViewById(R.id.textboxEmailLogin);
        textboxPasswordLogin = findViewById(R.id.textboxPasswordLogin);
        textViewWarningLogin = findViewById(R.id.textViewWarningLogin);
        buttonRegisterMainPage = findViewById(R.id.buttonRegisterMainPage);
        buttonRegisterMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
        String token = FirebaseInstanceId.getInstance().getToken();


       /* postOperations.pushNotification(MainActivity.this,
                "Merhaba",
                "Merhaba",
                "c3vHpMNrTRCwb94rIjeTi8:APA91bGxyGsn1WbCKi9nwQ2P6Wb6PIUMMSHGL3eh3IwpyoHt9Izi8wWMz41nbC3oguNXxKixTMwQj47s96UO7-0YIYXKA_ltfR1yNX5HcLCjwqoP8Dkw3PYlIQgKtk30eu3GTAFw531V",
                "AAAAWHco7YE:APA91bGt9bFWiRSsL1DeV8NAf7B5-PiqpmxeWIoBzXUDOPS5x46VhgWoCPk4VlvOEQa6DKr1tj5_LgrptukHbIo1OYpf9Ho5LtlkLsz7kTFte6u9ytpSRVvy9xGgve-MY6q3f3Q-V1mw");*/
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postOperations.loginOperations(MainActivity.this,String.valueOf(textboxEmailLogin.getText()), String.valueOf(textboxPasswordLogin.getText()), textViewWarningLogin);
            }
        });
        buttonContinueWithoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });
    }
}