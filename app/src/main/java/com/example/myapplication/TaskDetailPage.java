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
        String taskName = intent.getExtras().getString("taskName");
        TextView taskDetailsTitle = findViewById(R.id.taskNameView);
        taskDetailsTitle.setText(taskName);

    }
}