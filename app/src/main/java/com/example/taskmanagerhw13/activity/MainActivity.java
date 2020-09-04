package com.example.taskmanagerhw13.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.taskmanagerhw13.R;
import com.example.taskmanagerhw13.fragment.LoginFragment;
import com.example.taskmanagerhw13.fragment.SinInFragment;
import com.example.taskmanagerhw13.fragment.SinInFragment.Callbacks;

public class MainActivity extends AppCompatActivity
        implements SinInFragment.Callbacks, LoginFragment.Callbacks{

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment.newInstance())
                    .commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_setting:
                Toast.makeText(this,"this Feature will be add soon!",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_users:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLoginClicked() {
        Intent intent = TaskPagerActivity.newIntent(this);
        startActivity(intent);

    }

    @Override
    public void onSinInClicked() {
        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SinInFragment())
                    .commit();

    }
    @Override
    public void onBackClicked() {
        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();

    }

}