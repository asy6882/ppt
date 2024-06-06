package com.example.checklist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TaskDbHelper dbHelper;
    private TaskAdapter adapter;
    private List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(taskList, this::deleteTask);
        recyclerView.setAdapter(adapter);

        loadTasks();

        Button buttonAdd = findViewById(R.id.buttonAdd);
        EditText editTextTask = findViewById(R.id.editTextTask);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString();
                if (!task.isEmpty()) {
                    addTask(task);
                    editTextTask.setText("");
                }
            }
        });
    }

    private void loadTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("tasks", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String task = cursor.getString(cursor.getColumnIndexOrThrow("task"));
            taskList.add(new Task(id, task));
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void addTask(String task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("task", task);
        long newRowId = db.insert("tasks", null, values);
        taskList.add(new Task((int) newRowId, task));
        adapter.notifyItemInserted(taskList.size() - 1);
    }

    private void deleteTask(Task task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tasks", "_id=?", new String[]{String.valueOf(task.getId())});
        taskList.remove(task);
        adapter.notifyDataSetChanged();
    }
}
