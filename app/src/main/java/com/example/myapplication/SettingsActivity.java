package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button backToMain = findViewById(R.id.backButton);
        backToMain.setOnClickListener(view -> {
            Intent backToMainIntent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(backToMainIntent);
        });

        Button saveButton = findViewById(R.id.saveButtonFromSettings);
        saveButton.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            EditText usernameField = findViewById(R.id.usernameEntery);
            String username = usernameField.getText().toString();
            sharedPreferencesEditor.putString("username", username);
            sharedPreferencesEditor.apply();

            Intent toMain = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(toMain);
        });
    }
}