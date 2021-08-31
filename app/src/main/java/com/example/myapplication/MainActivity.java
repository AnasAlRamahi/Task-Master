package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(toSettings);
            }
        });

        Button leftButton = findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLeft = new Intent(MainActivity.this, TaskDetailPage.class);
                Button leftButton = findViewById(R.id.leftButton); // we can define a button and get the text from it or we can get the text directly from the button namzzz
                String taskName = leftButton.getText().toString();
                toLeft.putExtra("taskName", taskName);
                startActivity(toLeft);
            }
        });

        Button middleButton = findViewById(R.id.middleButton);
        middleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMiddle = new Intent(MainActivity.this, TaskDetailPage.class);
                Button middleButton = findViewById(R.id.middleButton);
                String taskName = middleButton.getText().toString();
                toMiddle.putExtra("taskName", taskName);
                startActivity(toMiddle);
            }
        });

        Button rightButton = findViewById(R.id.rightButton);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRight = new Intent(MainActivity.this, TaskDetailPage.class);
                String taskName = leftButton.getText().toString();
                toRight.putExtra("taskName", taskName);
                startActivity(toRight);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        String usernameMessage = "\'s Tasks";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String username = sharedPreferences.getString("username", "Username");
        TextView usernameField = findViewById(R.id.usernameView);
        usernameField.setText(username + usernameMessage);
    }
}