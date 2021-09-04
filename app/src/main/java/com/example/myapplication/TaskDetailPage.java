package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TaskDetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_page);

        Button backToMain = findViewById(R.id.backButton);
        backToMain.setOnClickListener(view -> {
            Intent backToMainIntent = new Intent(TaskDetailPage.this, MainActivity.class);
            startActivity(backToMainIntent);
        });

        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        String body = intent.getExtras().getString("body");
        String state = intent.getExtras().getString("state");
        TextView taskNameView = findViewById(R.id.taskNameView);
        taskNameView.setText(title);
        TextView taskContentView = findViewById(R.id.taskContentView);
        taskContentView.setText(body);
        TextView stateView = findViewById(R.id.stateView);
        stateView.setText(state);


    }
}