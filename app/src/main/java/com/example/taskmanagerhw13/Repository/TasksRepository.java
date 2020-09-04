package com.example.taskmanagerhw13.Repository;



import com.example.taskmanagerhw13.Utils.TaskState;
import com.example.taskmanagerhw13.model.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TasksRepository implements Serializable {
    private static TasksRepository sTasksRepository;
    private List<Task> mTasks;
    public static int mNumberOfTasks;

    public static TasksRepository getInstance() {
        if (sTasksRepository == null)
            sTasksRepository = new TasksRepository();
        return sTasksRepository;
    }

    private TasksRepository() {

        mTasks = new ArrayList<>();
        for (int i = 0; i < mNumberOfTasks; i++) {
            Task task = new Task();
            task.setTaskName("Task : " + (i + 1));
            task.setTaskState(randomTaskState());
            mTasks.add(task);
        }
    }

    public List<Task> getList() {
        return mTasks;
    }

    private TaskState randomTaskState() {
        Random random = new Random();
        int rand = random.nextInt(3);
        switch (rand) {
            case 0:
                return TaskState.DONE;
            case 1:
                return TaskState.DOING;
            case 2:
                return TaskState.TODO;
        }
        return null;
    }

    public List<Task> getList(TaskState taskState) {
        List<Task> taskList = new ArrayList<>();
        for (Task task : mTasks) {
            if (task.getTaskState() == taskState)
                taskList.add(task);
        }
        return taskList;
    }

    public void addTask() {
        Task task = new Task();
        task.setTaskName("Task : " + (sTasksRepository.getList().size()));
        task.setTaskState(randomTaskState());
        mTasks.add(task);
    }

    public int getIndexOfTask(Task task) {
        return mTasks.indexOf(task);
    }

    public void cleanTaskRepository() {
        sTasksRepository = null;
    }
}
