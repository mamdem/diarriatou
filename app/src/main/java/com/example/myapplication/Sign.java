package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.SQLite.DatabaseHelper;

public class Sign extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextUsername, editTextPassword;
    private DatabaseHelper dbHelper;

    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        dbHelper = new DatabaseHelper(this);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(R.layout.loading_dialog); // layout personnalisé avec ProgressBar
            builder.setCancelable(false); // rend le dialogue non annulable
            loadingDialog = builder.create();
        }
        loadingDialog.show();
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    private void registerUser() {
        showLoadingDialog();

        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_FIRST_NAME, firstName);
        values.put(DatabaseHelper.COLUMN_LAST_NAME, lastName);
        values.put(DatabaseHelper.COLUMN_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);

        long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
        // Vous pouvez ajouter ici du code pour gérer l'utilisateur nouvellement inscrit
        Toast.makeText(getApplicationContext(), "Inscription reussi", Toast.LENGTH_LONG).show();
        hideLoadingDialog();
    }

}