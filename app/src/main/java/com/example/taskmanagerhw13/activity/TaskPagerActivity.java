package com.example.taskmanagerhw13.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagerhw13.R;
import com.example.taskmanagerhw13.Repository.TasksRepository;
import com.example.taskmanagerhw13.Repository.UserRepository;
import com.example.taskmanagerhw13.Utils.TaskState;
import com.example.taskmanagerhw13.fragment.TaskDetailFragment;
import com.example.taskmanagerhw13.fragment.TasksFragment;
import com.example.taskmanagerhw13.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class TaskPagerActivity extends AppCompatActivity implements  TaskDetailFragment.Callbacks {

    public static final String EXTRA_BUNDLE_USERNAME = "extraBundleUsername";
    public static final String TASK_DETAIL_FRAGMENT_DIALOG_TAG = "TaskDetailFragmentDialogTag";
    public static final int TASK_DETAIL_REQUEST_CODE = 101;
    private TasksRepository mTasksRepository;
    private ViewPager2 viewPager;
    private String mUsername;
    private UserRepository mUserRepository;

    String[] titles = {"Done", "Doing", "Todo"};
    private FragmentStateAdapter pagerAdapter;

    public static Intent newIntent(Context context, String username) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_BUNDLE_USERNAME, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);

        Intent intent = getIntent();
        mUsername = intent.getStringExtra(EXTRA_BUNDLE_USERNAME);
        this.setTitle(mUsername);


        mUserRepository = UserRepository.getInstance();
        mTasksRepository=TasksRepository.getInstance();
        findViews();
        setListeners();

        pagerAdapter = new TaskPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout_task_state);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position])
        ).attach();

    }

    private void findViews() {
        viewPager = findViewById(R.id.view_pager_fragments);

    }

    private void setListeners() {

    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
//            TasksRepository.getInstance().cleanTaskRepository();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

//    @Override
//    public void onAddTaskClicked() {
//        Task newTask = new Task(mUsername);
//        mTasksRepository = TasksRepository.getInstance();
//        mTasksRepository.addTask(newTask);
//
//        TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance(newTask.getId());
//        taskDetailFragment.show(getSupportFragmentManager(), TASK_DETAIL_FRAGMENT_DIALOG_TAG);
//    }

    @Override
    public void updateTasksFragment(TaskState taskState, String username) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.fragment_container,TasksFragment.newInstance(taskState,username)).commit();

//        TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        if (tasksFragment != null) {
//            tasksFragment.updateUI();
//        }
//        else{
//            TasksFragment tasksFragment1 = TasksFragment.newInstance(taskState,username);
//            tasksFragment1.updateUI();
//        }
        viewPager.setAdapter(pagerAdapter);

//        TasksFragment tasksFragment = (TasksFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        tasksFragment.update();
    }

//    @Override
//    public void onTaskUpdated() {
//        TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.fragment_container);
//        tasksFragment.updateUI();
//
//    }

//    @Override
//    public void onTaskUpdated() {
//
//            TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.fragment_container);
//
//            tasksFragment.updateUI();
//
//    }

    private class TaskPagerAdapter extends FragmentStateAdapter {
        public TaskPagerAdapter(FragmentActivity fragmentManager) {
            super(fragmentManager);
        }


        @Override
        public int getItemCount() {
            return 3;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return TasksFragment.newInstance(TaskState.DONE, mUsername);
                case 1:
                    return TasksFragment.newInstance(TaskState.DOING, mUsername);

                case 2:
                    return TasksFragment.newInstance(TaskState.TODO, mUsername);

            }
            return null;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mUserRepository.getUserType(mUsername) != null) {
            switch (mUserRepository.getUserType(mUsername)) {
                case USER:
                    return super.onCreateOptionsMenu(menu);
                case ADMIN:
                    MenuInflater inflater = getMenuInflater();
                    inflater.inflate(R.menu.menu_task_pager, menu);
                    return true;
            }

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_setting:
                Toast.makeText(this, "this Feature will be add soon!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_clear_tasks:
                Toast.makeText(this, "repository cleared!", Toast.LENGTH_SHORT).show();

                mTasksRepository.cleanTaskRepository();
                viewPager.setAdapter(pagerAdapter);
//                TasksFragment tasksFragment = (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//                tasksFragment.updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}