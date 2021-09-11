package com.example.pracgrader.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pracgrader.R;
import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.Instructor;

public class InstructorHomeFragment extends Fragment {

    Button logout;
    Button editProfile;
    Button addStudent;
    Button viewStudents;
    TextView title;
    AppData appData = AppData.getInstance();

    public InstructorHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructor_home, container, false);
        setXML(view);
        onClicks();
        title.setText(((Instructor) appData.getCurrentUser()).getName());

        return view;
    }

    private void setXML(View view)
    {
        logout = view.findViewById(R.id.logout);
        editProfile = view.findViewById(R.id.editProfile);
        addStudent = view.findViewById(R.id.addStudent);
        viewStudents = view.findViewById(R.id.viewStudents);
        title = view.findViewById(R.id.title);

    }

    private void onClicks()
    {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appData.setCurrentUser(null);
                ft.replace(R.id.main,new LoginFragment()).commit();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewUserFragment newUserFragment = new NewUserFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source","Instructor");
                bundle.putString("purpose","editProfile");
                newUserFragment.setArguments(bundle);
                ft.replace(R.id.main,newUserFragment).commit();
            }
        });

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewUserFragment newUserFragment = new NewUserFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source","Instructor");
                bundle.putString("purpose","addStudent");
                newUserFragment.setArguments(bundle);
                ft.replace(R.id.main,newUserFragment).commit();
            }
        });


        viewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewStudentListFragment viewStudentListFragment = new ViewStudentListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source","Instructor");
                bundle.putString("purpose","viewStudents");
                viewStudentListFragment.setArguments(bundle);
                ft.replace(R.id.main,viewStudentListFragment).commit();
            }
        });
    }
}