package com.example.taskmanagerhw13.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskmanagerhw13.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Button mButtonLogIn;
    private Button mButtonSignIn;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        findAllView(view);
        setListeners();

        return view;

    }
    private void findAllView(View view) {
        mEditTextUsername = view.findViewById(R.id.edit_text_username);
        mEditTextPassword = view.findViewById(R.id.edit_text_password);
        mButtonLogIn = view.findViewById(R.id.button_login);
        mButtonSignIn = view.findViewById(R.id.button_sign_in);
    }

    private void setListeners() {
        mButtonSignIn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                String userName = mEditTextUsername.getText().toString();
                String password = mEditTextPassword.getText().toString();


            }
        }));
        mButtonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), TaskPagerActivity.class);
                startActivity(intent);
            }
        });
    }
}