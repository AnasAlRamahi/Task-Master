package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        Button backToMain = findViewById(R.id.backToMainButton);
        backToMain.setOnClickListener(view -> {
            Intent toAddTask = new Intent(AddTasks.this, MainActivity.class);
            startActivity(toAddTask);
        });

        Button addTaskButton = findViewById(R.id.submitTask);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDatabase db = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "TaskDB").allowMainThreadQueries().build();
                TaskDao taskDao = db.taskDao();

                TextView taskTitle = findViewById(R.id.taskTitleEntry);
                String title = taskTitle.getText().toString();
                TextView taskBody = findViewById(R.id.taskBodyEntry);
                String body = taskBody.getText().toString();
                TextView taskState = findViewById(R.id.taskStateEntry);
                String state = taskState.getText().toString();
                taskDao.insert(new Task(title, body, state));

                Intent goToMain = new Intent(AddTasks.this, MainActivity.class);
                startActivity(goToMain);

                Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        TaskDatabase db = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "TaskDB").allowMainThreadQueries().build();
        TaskDao taskDao = db.taskDao();

        TextView count = findViewById(R.id.totalTasks);
        count.setText("Total Tasks: " + taskDao.getAll().size());

    }
}
