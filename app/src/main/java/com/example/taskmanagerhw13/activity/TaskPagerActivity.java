package com.example.taskmanagerhw13.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmanagerhw13.R;
import com.example.taskmanagerhw13.Repository.TasksRepository;
import com.example.taskmanagerhw13.Utils.TaskState;
import com.example.taskmanagerhw13.fragment.TasksFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;



public class TaskPagerActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,TaskPagerActivity.class);
        return  intent;
    }


    public static final String EXTRA_USERNAME = "extraUsername";
    private ViewPager2 viewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFloatingActionButtonAdd;
    String[] titles = {"Done", "Doing", "Todo"};
    private FragmentStateAdapter pagerAdapter;

    public static Intent newIntent(Context context, String username) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_pager);
        Intent intent = getIntent();
        this.setTitle(intent.getStringExtra(EXTRA_USERNAME));

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
            TasksRepository.getInstance().cleanTaskRepository();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

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
                    return TasksFragment.newInstance(TaskState.DONE);
                case 1:
                    return TasksFragment.newInstance(TaskState.DOING);

                case 2:
                    return TasksFragment.newInstance(TaskState.TODO);

            }
            return null;
        }
    }


}