package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddTasks extends AppCompatActivity {
    HashMap<String, Team> teams = new HashMap<>();

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            TextView description = findViewById(R.id.taskBodyEntry);
            description.setText(sharedText);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }


            Button backToMain = findViewById(R.id.backToMainButton);
            backToMain.setOnClickListener(view -> {
                Intent toAddTask = new Intent(AddTasks.this, MainActivity.class);
                startActivity(toAddTask);
            });

            Amplify.API.query(
                    ModelQuery.list(Team.class),
                    response -> {
                        for (Team team : response.getData()) {
                            Log.i("MyAmplifyApp", team.getName());
                            teams.put(team.getName(), team);
                        }
                    },
                    error -> Log.e("MyAmplifyApp", "Query failure", error)
            );

            Button addTaskButton = findViewById(R.id.submitTask);
            addTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                TaskDatabase db = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "TaskDB").allowMainThreadQueries().build();
//                TaskDao taskDao = db.taskDao();

                    TextView taskTitle = findViewById(R.id.taskTitleEntry);
                    String title = taskTitle.getText().toString();
                    TextView taskBody = findViewById(R.id.taskBodyEntry);
                    String body = taskBody.getText().toString();
                    RadioGroup rGroup = findViewById(R.id.teamRadioGroup);
                    int id = rGroup.getCheckedRadioButtonId();
                    RadioButton rButton = findViewById(id);
                    String rButtonContent = rButton.getText().toString();

//                TextView taskState = findViewById(R.id.taskStateEntry);
//                String state = taskState.getText().toString();
//                taskDao.insert(new Task(title, body, state));

//                Team team = new Team.Builder()
//                        .name("Team 3")
//                        .build();


//                Amplify.API.mutate(ModelMutation.create(team),
//                        response -> Log.i("MyAmplifyApp", "Team with id: " + response.getData().getId()),
//                        error -> Log.e("MyAmplifyApp", "Create failed", error)
//                );

                    com.amplifyframework.datastore.generated.model.Task todo = com.amplifyframework.datastore.generated.model.Task.builder()
                            .title(title)
                            .description(body)
                            .status("new")
                            .team(teams.get(rButtonContent))
                            .build();

                    Amplify.API.mutate(ModelMutation.create(todo),
                            response -> Log.i("MyAmplifyApp", "Todo with id: " + response.getData().getId()),
                            error -> Log.e("MyAmplifyApp", "Create failed", error)
                    );

                    Intent goToMain = new Intent(AddTasks.this, MainActivity.class);
                    startActivity(goToMain);

                    Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_LONG).show();
                }
            });
        }
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

