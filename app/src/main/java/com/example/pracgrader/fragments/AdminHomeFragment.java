package com.example.pracgrader.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pracgrader.R;

public class AdminHomeFragment extends Fragment {
    Button logout;
    Button addInstructor;
    Button addStudent;
    Button addPracticals;
    Button viewInstructors;
    Button viewStudents;
    Button viewPracticals;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        setXML(view);
        onClicks();
        return view;

    }

    private void setXML(View view)
    {
        logout = view.findViewById(R.id.logout);

        addInstructor = view.findViewById(R.id.addInstructor);
        addStudent = view.findViewById(R.id.addStudent);
        addPracticals = view.findViewById(R.id.addPracticals);
        viewInstructors = view.findViewById(R.id.viewInstructors);
        viewStudents = view.findViewById(R.id.viewStudents);
        viewPracticals = view.findViewById(R.id.viewPracs);
    }

    private void onClicks()
    {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft.replace(R.id.main,new LoginFragment()).commit();
            }
        });

        addInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewUserFragment newUserFragment = new NewUserFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source","addInstructorAdmin");
                newUserFragment.setArguments(bundle);
                ft.replace(R.id.main,newUserFragment).commit();
            }
        });

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewUserFragment newUserFragment = new NewUserFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source","addStudentAdmin");
                newUserFragment.setArguments(bundle);
                ft.replace(R.id.main,newUserFragment).commit();
            }
        });

        addPracticals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPracFragment addPracFragment = new AddPracFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source","addPracticalsAdmin");
                addPracFragment.setArguments(bundle);
                ft.replace(R.id.main,addPracFragment).commit();
            }
        });

        viewInstructors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft.replace(R.id.main,new ViewInstructorsFragment()).commit();
            }
        });

        viewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewStudentListFragment viewStudentListFragment = new ViewStudentListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source","viewStudentsAdmin");
                viewStudentListFragment.setArguments(bundle);
                ft.replace(R.id.main,viewStudentListFragment).commit();
            }
        });

        viewPracticals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPracListFragment pracListFragment = new ViewPracListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source","viewPracticalsAdmin");
                pracListFragment.setArguments(bundle);
                ft.replace(R.id.main,pracListFragment).commit();
            }
        });
    }

}