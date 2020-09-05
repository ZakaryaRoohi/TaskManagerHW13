package com.example.taskmanagerhw13.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.taskmanagerhw13.R;
import com.example.taskmanagerhw13.Repository.TasksRepository;
import com.example.taskmanagerhw13.Utils.TaskState;
import com.example.taskmanagerhw13.model.Task;

import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends DialogFragment {

    public static final String ARG_TASK_ID = "ARGTaskId";
    public static final String DIALOG_FRAGMENT_TAG = "Dialog";
    public static final int REQUEST_CODE_DATE_PICKER = 0;

    private TasksRepository mTasksRepository;
    private Task mTask;

    private EditText mEditTextTaskTitle;
    private EditText mEditTextDescription;
    private RadioButton mRadioButtonDone;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonTodo;
    private Button mButtonDate;
    private Button mButtonSave;
    private Button mButtonDiscard;

    public TaskDetailFragment() {
        // Required empty public constructor
    }


    public static TaskDetailFragment newInstance(UUID taskId) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTasksRepository = TasksRepository.getInstance();
        UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        mTask = (Task) mTasksRepository.get(taskId);
//        Toast.makeText(getActivity(),"uuid"+mTask.getId(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        findViews(view);
        setListeners();
        initViews();
        return view;
    }

    private void findViews(View view) {
        mEditTextTaskTitle = view.findViewById(R.id.task_title);
        mEditTextDescription = view.findViewById(R.id.text_view_description);
        mButtonDate = view.findViewById(R.id.task_date);
        mButtonSave = view.findViewById(R.id.button_save);
        mButtonDiscard = view.findViewById(R.id.button_discard);
        mRadioButtonDone = view.findViewById(R.id.radio_button_done);
        mRadioButtonDoing = view.findViewById(R.id.radio_button_doing);
        mRadioButtonTodo = view.findViewById(R.id.radio_button_todo);
    }

    private void initViews() {
        mEditTextTaskTitle.setText(mTask.getTaskTitle());
        mEditTextDescription.setText(mTask.getTaskDescription());
        mButtonDate.setText(mTask.getTaskDate().toString());
        setTaskState(mTask.getTaskState());


    }

    private void setTaskState(TaskState taskState) {
        switch (taskState) {
            case DONE:
                mRadioButtonDone.setChecked(true);
                break;
            case DOING:
                mRadioButtonDoing.setChecked(true);
                break;
            case TODO:
                mRadioButtonTodo.setChecked(true);
                break;
        }
    }

    private void setListeners() {
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getTaskDate());
                datePickerFragment.setTargetFragment(TaskDetailFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            }
        });
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
getActivity().finish();
            }
        });
        mButtonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRadioButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setTaskState(TaskState.DONE);
            }
        });
        mRadioButtonDoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setTaskState(TaskState.DOING);

            }
        });
        mRadioButtonTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setTaskState(TaskState.TODO);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            //get response from intent extra, which is user selected date
            Date userSelectedDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE);

            mTask.setTaskDate(userSelectedDate);
            mButtonDate.setText(mTask.getTaskDate().toString());

            updateTask();
        }
    }

    private void updateTask() {
        mTasksRepository.update(mTask);
//        mCallbacks.onCrimeUpdated(mCrime);
    }

}