package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<com.amplifyframework.datastore.generated.model.Task> TaskItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                tasksRecyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }


        Button login = findViewById(R.id.loginButton);
        // do your thang
        login.setOnClickListener(view -> {
            Amplify.Auth.signInWithWebUI(
                    this,
                    result -> Log.i("AuthQuickStart", result.toString()),
                    error -> Log.e("AuthQuickStart", error.toString())
            );
        });


        Button signout = findViewById(R.id.signoutButton);
        signout.setOnClickListener(view -> {
            Amplify.Auth.signOut(
                    () -> Log.i("AuthQuickstart", "Signed out successfully"),
                    error -> Log.e("AuthQuickstart", error.toString())
            );
        });

        TextView usernameView = findViewById(R.id.usernameView);
//        if(Amplify.Auth.equals(null)){
////            usernameView.setText("Login Please...");
//                        usernameView.setText("Welcome, " + Amplify.Auth.getCurrentUser().getUsername());
//
//        } else {
//            usernameView.setText("Welcome, " + Amplify.Auth.getCurrentUser().getUsername());
//        }

//        AuthSignUpOptions options = AuthSignUpOptions.builder()
//                .userAttribute(AuthUserAttributeKey.email(), "shero2ram@gmail.com")
//                .build();
//        Amplify.Auth.signUp("anas", "Password123", options,
//                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
//                error -> Log.e("AuthQuickStart", "Sign up failed", error)
//        );

//        Amplify.Auth.confirmSignUp(
//                "anas",
//                "937466",
//                result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
//                error -> Log.e("AuthQuickstart", error.toString())
//        );


        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Task item : response.getData()) {
                        Log.i("MyAmplifyApp", item.getTitle());

                        TaskItems.add(item);
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(toSettings);
            }
        });

        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(view -> {
            Intent toAddTask = new Intent(MainActivity.this, AddTasks.class);
            startActivity(toAddTask);
        });

//        Button leftButton = findViewById(R.id.leftButton);
//        leftButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent toLeft = new Intent(MainActivity.this, TaskDetailPage.class);
//                Button leftButton = findViewById(R.id.leftButton); // we can define a button and get the text from it or we can get the text directly from the button id in the layout
//                String taskName = leftButton.getText().toString();
//                toLeft.putExtra("taskName", taskName);
//                startActivity(toLeft);
//            }
//        });
//
//        Button middleButton = findViewById(R.id.middleButton);
//        middleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent toMiddle = new Intent(MainActivity.this, TaskDetailPage.class);
//                Button middleButton = findViewById(R.id.middleButton);
//                String taskName = middleButton.getText().toString();
//                toMiddle.putExtra("taskName", taskName);
//                startActivity(toMiddle);
//            }
//        });
//
//        Button rightButton = findViewById(R.id.rightButton);
//        rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent toRight = new Intent(MainActivity.this, TaskDetailPage.class);
//                String taskName = rightButton.getText().toString();
//                toRight.putExtra("taskName", taskName);
//                startActivity(toRight);
//            }
//        });

//        TaskDatabase db = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "TaskDB").allowMainThreadQueries().build();
//        TaskDao taskDao = db.taskDao();

//        List<Task> TaskItems = taskDao.getAll();

//        RecyclerView tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
//        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        tasksRecyclerView.setAdapter(new TaskAdapter(TaskItems));

    }

    @Override
    protected void onStart() {
        super.onStart();
        RecyclerView tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(new TaskAdapter(TaskItems));
    }

    @Override
    protected void onResume() {
        super.onResume();

//        String usernameMessage = "\'s Tasks";
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        String username = sharedPreferences.getString("username", "Username");
//        TextView usernameField = findViewById(R.id.usernameView);
//        usernameField.setText(username + usernameMessage);
    }
}