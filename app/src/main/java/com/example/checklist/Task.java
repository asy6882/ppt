package com.example.checklist;

public class Task {
    private int id;
    private String task;

    public Task(int id, String task) {
        this.id = id;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }
}
