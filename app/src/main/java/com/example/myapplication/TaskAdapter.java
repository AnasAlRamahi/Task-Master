package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    List<Task> taskItems = new ArrayList<Task>();

    public TaskAdapter(List<Task> taskItems) {
        this.taskItems = taskItems;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public Task task;
        View itemView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
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
        taskTitle.setText(holder.task.title);
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }




}
