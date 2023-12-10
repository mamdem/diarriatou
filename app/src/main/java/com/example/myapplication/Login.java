package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.SQLite.DatabaseHelper;

public class Login extends AppCompatActivity {

    EditText usernameET, passwordET;
    Button btn;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.password);

        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.checkUser(usernameET.getText().toString(), passwordET.getText().toString())){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Login ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });

    }




}