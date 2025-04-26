package com.example.hydro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText nameInput, emailInput, passwordInput, confirmPasswordInput;
    Button signupButton;

    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "loginPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Link UI
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        signupButton = findViewById(R.id.signupButton);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        signupButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmPasswordInput.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                nameInput.setError("Name required");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                emailInput.setError("Email required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                passwordInput.setError("Password required");
                return;
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                confirmPasswordInput.setError("Confirm your password");
                return;
            }
            if (!password.equals(confirmPassword)) {
                confirmPasswordInput.setError("Passwords do not match");
                return;
            }

            // Save user credentials securely
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PASSWORD, password);
            editor.apply();

            Toast.makeText(SignupActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();

            // Navigate back to login
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
