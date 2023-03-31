package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class registerActivity extends AppCompatActivity {

    EditText registerUsername, registerEmail, registerPassword, registerPlace;

    public void userRegisteration(View view){
        if(registerUsername.getText().toString().equals("") || registerEmail.getText().toString().equals("")
        || registerPassword.getText().toString().equals("") || registerPlace.getText().toString().equals("")){
            Toast.makeText(this, "All the fields are mandatory", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(registerActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        registerUsername = findViewById(R.id.registerUsername);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerPlace = findViewById(R.id.registerPlace);

        TextView goRegister = findViewById(R.id.goToLogin);
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this, loginActivity.class));
            }
        });
    }
}