package com.example.smarttaskmanager;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table ORDER BY deadline ASC")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM task_table WHERE category = :category")
    LiveData<List<Task>> getTasksByCategory(String category);
}
