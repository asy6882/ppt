package com.example.checklist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Task task);
    }

    public TaskAdapter(List<Task> taskList, OnDeleteClickListener onDeleteClickListener) {
        this.taskList = taskList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskTextView;
        public Button deleteButton;

        public TaskViewHolder(View view) {
            super(view);
            taskTextView = view.findViewById(R.id.taskTextView);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskTextView.setText(task.getTask());
        holder.deleteButton.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(task));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
