package com.example.taskmanagerhw13.model;


import com.example.taskmanagerhw13.Utils.TaskState;
import com.example.taskmanagerhw13.Utils.UserType;

import java.util.Date;
import java.util.UUID;

public class Task {

    private UUID mId;
    private String mTaskTitle;
    private TaskState mTaskState;
    private String mTaskDescription;
    private Date mTaskDate;

    public UUID getId() {
        return mId;
    }

    public void setUser(UserType mUser) {
        this.mUser = mUser;
    }

    private UserType mUser;

    public String getTaskDescription() {
        return mTaskDescription;
    }

    public void setTaskDescription(String mTaskDescription) {
        this.mTaskDescription = mTaskDescription;
    }

    public Date getTaskDate() {
        return mTaskDate;
    }

    public void setTaskDate(Date mTaskDate) {
        this.mTaskDate = mTaskDate;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskName) {
        mTaskTitle = taskName;
    }

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }

    public Task( String mTaskTitle, TaskState mTaskState, String mTaskDescription, Date mTaskDate, UserType mUser) {
        this.mId = UUID.randomUUID();
        this.mTaskTitle = mTaskTitle;
        this.mTaskState = mTaskState;
        this.mTaskDescription = mTaskDescription;
        this.mTaskDate = mTaskDate;
        this.mUser = mUser;
    }
    public Task(UUID id){
        mId = id;
        mTaskDate = new Date();



    }
    public Task(){
        this(UUID.randomUUID());

    }
}
