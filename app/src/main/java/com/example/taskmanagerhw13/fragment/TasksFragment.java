package com.example.taskmanagerhw13.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.taskmanagerhw13.R;
import com.example.taskmanagerhw13.Repository.TasksRepository;
import com.example.taskmanagerhw13.Utils.TaskState;
import com.example.taskmanagerhw13.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class TasksFragment<EndlessRecyclerViewScrollListener> extends Fragment {
    /**
     * get username and number of Tasks from Task activity
     */
    private static final String ARG_TASK_STATE = "ArgsTaskState";
    public static final String TASK_DETAIL_FRAGMENT_DIALOG_TAG = "TaskDetailFragmentDialogTag";
    public static final int TASK_DETAIL_REQUEST_CODE = 101;
    private TasksRepository mTasksRepository;
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private FloatingActionButton mFloatingActionButtonAdd;
    private TaskState mTaskState;
    private LinearLayout mLinearLayout1;
    private LinearLayout mLinearLayout2;
    private Callbacks mCallbacks;

    public TaskAdapter getAdapter() {
        return mAdapter;
    }

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance(TaskState taskState) {

        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_STATE, taskState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskState = (TaskState) getArguments().getSerializable(ARG_TASK_STATE);
        mTasksRepository = TasksRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        findViews(view);
        setClickListener();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        switch (getResources().getConfiguration().orientation) {
            case 1:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
            case 2:
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                break;
        }

        updateUI();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_tasks);
        mLinearLayout2 = view.findViewById(R.id.layout2);
        mLinearLayout1 = view.findViewById(R.id.layout1);
        mFloatingActionButtonAdd = view.findViewById(R.id.floating_action_button_add);
    }

    private void setClickListener() {
        mFloatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Task newTask = new Task();
//                mTasksRepository.addTask(newTask);
//                updateUI();
//                TaskDetailFragment taskDetailFragment =  TaskDetailFragment.newInstance(newTask.getId());
//                taskDetailFragment.setTargetFragment(TasksFragment.this,TASK_DETAIL_REQUEST_CODE);
//                taskDetailFragment.show(getFragmentManager(), TASK_DETAIL_FRAGMENT_DIALOG_TAG);
                mCallbacks.onAddTaskClicked();
            }
        });
    }

    public interface Callbacks {
        void onAddTaskClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Callbacks)
            mCallbacks = (Callbacks) context;
        else {
            throw new ClassCastException(context.toString() + "you must implement onAddTaskClicked");
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTaskTittle;
        private TextView mTextViewTaskState;
        private TextView mTextViewTaskDate;
        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTaskTittle = itemView.findViewById(R.id.list_row_task_title);
            mTextViewTaskState = itemView.findViewById(R.id.list_row_Task_state);
            mTextViewTaskDate = itemView.findViewById(R.id.text_view_task_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(mTask.getId());
                    taskDetailFragment.setTargetFragment(TasksFragment.this, TASK_DETAIL_REQUEST_CODE);
                    taskDetailFragment.show(getFragmentManager(), TASK_DETAIL_FRAGMENT_DIALOG_TAG);


                }
            });
        }

        public void bindTask(Task task) {
            mTask = task;
            if (getAdapterPosition() % 2 == 1)
                itemView.setBackgroundColor(Color.GRAY);
            else
                itemView.setBackgroundColor(Color.WHITE);

            mTextViewTaskTittle.setText(task.getTaskTitle());
            mTextViewTaskState.setText(task.getTaskState().toString());
            mTextViewTaskDate.setText(task.getTaskDate().toString());
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_row_task, parent, false);

            TaskHolder taskHolder = new TaskHolder(view);

            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

    public void updateUI() {
        List<Task> tasks = mTasksRepository.getList(mTaskState);
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTasks(tasks);
            mAdapter.notifyDataSetChanged();

        }
        if (tasks.size() == 0) {
            mLinearLayout1.setVisibility(View.GONE);
            mLinearLayout2.setVisibility(View.VISIBLE);
        } else if (mTasksRepository.getList().size() != 0) {
            mLinearLayout1.setVisibility(View.VISIBLE);
            mLinearLayout2.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateUI();
    }
}