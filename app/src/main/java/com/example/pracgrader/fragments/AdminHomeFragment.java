package com.example.pracgrader.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pracgrader.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminHomeFragment extends Fragment {
    Button logout;
    Button addInstructor;
    Button addStudent;
    Button addPracticals;
    Button viewInstructors;
    Button viewStudents;
    Button viewPracticals;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminHomeFragment newInstance(String param1, String param2) {
        AdminHomeFragment fragment = new AdminHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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