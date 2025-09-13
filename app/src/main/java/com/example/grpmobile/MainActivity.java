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
    private boolean isLoginMode = true;  // 默认是登录模式

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

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Set up the login button click listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginMode) {
                    login(); // 执行登录
                } else {
                    signup(); // 执行注册
                }
            }
        });

        // Set up "Go to Sign Up" TextView click listener
        tvGoToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginMode) {
                    // Switch to Sign Up mode
                    isLoginMode = false;
                    btnLogin.setText("Sign Up");
                    tvGoToSignup.setText("Back to Login");
                    edtName.setText(""); // Clear the name and password
                    edtPassword.setText("");
                    radioGroup.setVisibility(View.VISIBLE); // Show role selection (for Sign Up)
                } else {
                    // Switch back to Login mode
                    isLoginMode = true;
                    btnLogin.setText("Login");
                    tvGoToSignup.setText("Go to Sign Up");
                    edtName.setText(""); // Clear the name and password
                    edtPassword.setText("");
                    radioGroup.setVisibility(View.GONE); // Hide role selection (for Login)
                }
            }
        });
    }

    // Handle Login logic
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
                // Navigate to UserActivity
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            } else if ("Admin".equals(role)) {
                // Navigate to AdminActivity
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
            }
            finish(); // Close current activity
        } else {
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle Signup logic
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

        // Save the user to the database
        saveUserToDatabase(name, password, role);
        Toast.makeText(MainActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();

        // After signup, switch back to Login mode
        isLoginMode = true;
        btnLogin.setText("Login");
        tvGoToSignup.setText("Go to Sign Up");
        edtName.setText(""); // Clear the name and password
        edtPassword.setText("");
        radioGroup.setVisibility(View.GONE); // Hide role selection after signup
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

    // Save user information to the database
    private void saveUserToDatabase(String username, String password, String role) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("role", role);

        db.insert("users", null, values);
        db.close();
    }
}
