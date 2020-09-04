package com.example.taskmanagerhw13.model;


import com.example.taskmanagerhw13.Utils.TaskState;

public class Task {
    private String mTaskName;
    private TaskState mTaskState;

    public String getTaskName() {
        return mTaskName;
    }

    public void setTaskName(String taskName) {
        mTaskName = taskName;
    }

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }
}
