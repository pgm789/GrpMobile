package com.example.grpmobile;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtPassword;
    private RadioGroup radioGroup;
    private Button btnLogin;
    private TextView tvGoToSignup;
    private DBHelper dbHelper;
    private boolean isLoginMode = true;  // Default is login mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        radioGroup = findViewById(R.id.radioGroup);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToSignup = findViewById(R.id.tvGoToSignup);

        dbHelper = new DBHelper(this);

        // Set up the login button click listener
        btnLogin.setOnClickListener(v -> {
            if (isLoginMode) {
                login();
            } else {
                signup();
            }
        });

        // Set up "Go to Sign Up" TextView click listener
        tvGoToSignup.setOnClickListener(v -> {
            if (isLoginMode) {
                // Switch to sign up mode
                isLoginMode = false;
                btnLogin.setText("Sign Up");
                tvGoToSignup.setText("Back to Login");
                edtName.setText(""); // Clear name and password
                edtPassword.setText("");
                radioGroup.setVisibility(View.VISIBLE); // Show role selection (for sign up)
            } else {
                // Switch back to login mode
                isLoginMode = true;
                btnLogin.setText("Login");
                tvGoToSignup.setText("Go to Sign Up");
                edtName.setText(""); // Clear name and password
                edtPassword.setText("");
                radioGroup.setVisibility(View.GONE); // Hide role selection (for login)
            }
        });
    }

    private void login() {
        String name = edtName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter both name and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isUserValid(name, password)) {
            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

            // Get user role (User or Admin)
            String role = getUserRole(name, password);
            if ("User".equals(role)) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            } else if ("Admin".equals(role)) {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
            }
            finish(); // Close current activity
        } else {
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void signup() {
        String name = edtName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter both name and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected role (User or Admin)
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRoleButton = findViewById(selectedId);
        String role = selectedRoleButton.getText().toString();

        if (role.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please select a role", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the user to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", name);
        values.put("password", password);
        values.put("role", role);

        long rowId = db.insert("users", null, values);

        if (rowId != -1) {
            Toast.makeText(MainActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
            isLoginMode = true;
            btnLogin.setText("Login");
            tvGoToSignup.setText("Go to Sign Up");
            radioGroup.setVisibility(View.GONE); // Hide role selection after signup
        } else {
            Toast.makeText(MainActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private boolean isUserValid(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("users", null, "username=? AND password=?", new String[]{username, password}, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    private String getUserRole(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("users", new String[]{"role"}, "username=? AND password=?", new String[]{username, password}, null, null, null);
        String role = "";
        if (cursor.moveToFirst()) {
            role = cursor.getString(cursor.getColumnIndex("role"));
        }
        cursor.close();
        db.close();
        return role;
    }
}
