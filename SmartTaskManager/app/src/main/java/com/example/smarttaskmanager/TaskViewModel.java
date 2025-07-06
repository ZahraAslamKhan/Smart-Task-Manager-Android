package com.example.smarttaskmanager;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        TaskDatabase db = TaskDatabase.getInstance(application);
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<List<Task>> getTasksByCategory(String category) {
        return taskDao.getTasksByCategory(category);
    }

    public void insert(Task task) {
        new Thread(() -> taskDao.insert(task)).start();
    }

    public void update(Task task) {
        new Thread(() -> taskDao.update(task)).start();
    }

    public void delete(Task task) {
        new Thread(() -> taskDao.delete(task)).start();
    }
}
