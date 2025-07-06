package com.example.smarttaskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList = new ArrayList<>();
    private final Context context;
    private final TaskViewModel taskViewModel;

    public TaskAdapter(Context context) {
        this.context = context;
        this.taskViewModel = new TaskViewModel(((MainActivity) context).getApplication());
    }

    public void setTasks(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task current = taskList.get(position);
        holder.title.setText(current.getTitle());
        holder.deadline.setText("Due: " + current.getDeadline());
        holder.category.setText("Category: " + current.getCategory());
        holder.checkBox.setChecked(current.isCompleted());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            current.setCompleted(isChecked);
            taskViewModel.update(current);
        });

        holder.deleteBtn.setOnClickListener(v -> {
            taskViewModel.delete(current);
            Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, deadline, category;
        CheckBox checkBox;
        ImageButton deleteBtn;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title);
            deadline = itemView.findViewById(R.id.task_deadline);
            category = itemView.findViewById(R.id.task_category);
            checkBox = itemView.findViewById(R.id.checkbox_done);
            deleteBtn = itemView.findViewById(R.id.btn_delete);
        }
    }
}
