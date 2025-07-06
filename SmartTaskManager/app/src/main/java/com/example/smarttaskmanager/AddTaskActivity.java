// Original AddTaskActivity.java (Before Edit/Reminder Features)
package com.example.smarttaskmanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText titleInput, descInput;
    private Spinner categorySpinner;
    private TextView deadlineText;
    private Button pickDateBtn, saveBtn;

    private String selectedDate = "";
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        titleInput = findViewById(R.id.input_title);
        descInput = findViewById(R.id.input_description);
        categorySpinner = findViewById(R.id.spinner_category);
        deadlineText = findViewById(R.id.text_deadline);
        pickDateBtn = findViewById(R.id.btn_pick_date);
        saveBtn = findViewById(R.id.btn_save);

        taskViewModel = new TaskViewModel(getApplication());

        // Setup Spinner
        String[] categories = {"Work", "Personal", "Learning"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(adapter);

        pickDateBtn.setOnClickListener(v -> showDatePicker());
        saveBtn.setOnClickListener(v -> saveTask());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,
                (view, year, month, day) -> {
                    selectedDate = day + "/" + (month + 1) + "/" + year;
                    deadlineText.setText("Deadline: " + selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void saveTask() {
        String title = titleInput.getText().toString().trim();
        String desc = descInput.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Task task = new Task(title, desc, category, selectedDate, false);
        taskViewModel.insert(task);
        Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        finish();
    }
}
