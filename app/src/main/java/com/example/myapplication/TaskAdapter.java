package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    List<com.amplifyframework.datastore.generated.model.Task> taskItems = new ArrayList<>();

    public TaskAdapter(List<com.amplifyframework.datastore.generated.model.Task> taskItems) {
        this.taskItems = taskItems;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public com.amplifyframework.datastore.generated.model.Task task;
        View itemView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), TaskDetailPage.class);
                intent.putExtra("title",task.getTitle());
                intent.putExtra("body",task.getDescription());
                intent.putExtra("state",task.getStatus());
                view.getContext().startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        holder.task = taskItems.get(position);
        TextView taskTitle = holder.itemView.findViewById(R.id.taskTitleViewFragment);
        taskTitle.setText(holder.task.getTitle());
        TextView taskState = holder.itemView.findViewById(R.id.taskStateViewFragment);
        taskState.setText(holder.task.getStatus());
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

}
