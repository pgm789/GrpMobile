package com.example.grpmobile;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLoginName, edtLoginPassword;
    private Button btnLogin, btnSignup;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        edtLoginName = findViewById(R.id.edtLoginName);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Login button listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtLoginName.getText().toString().trim();
                String password = edtLoginPassword.getText().toString().trim();

                if (name.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both name and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if user is valid
                if (isUserValid(name, password)) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    // Get user role (User or Admin)
                    String role = getUserRole(name, password);
                    if ("User".equals(role)) {
                        Intent userIntent = new Intent(LoginActivity.this, UserActivity.class);
                        startActivity(userIntent);
                    } else if ("Admin".equals(role)) {
                        Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(adminIntent);
                    }
                    finish(); // Close LoginActivity
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Signup button listener: Redirect to SignupActivity
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(signupIntent);
            }
        });
    }

    // Validate the user credentials in the database
    private boolean isUserValid(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {"username", "password"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return isValid;
    }

    // Get the role of the user (User or Admin)
    private String getUserRole(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {"role"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

        String role = "";
        if (cursor.moveToFirst()) {
            role = cursor.getString(cursor.getColumnIndex("role"));
        }

        cursor.close();
        db.close();

        return role;
    }
}
