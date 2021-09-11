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
import com.example.pracgrader.classfiles.Student;


public class StudentHomeFragment extends Fragment {

    Button logout;
    Button editProfile;
    Button viewPracticals;
    TextView title;
    AppData appData = AppData.getInstance();


    public StudentHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_home, container, false);

        setXML(view);
        onClicks();

        title.setText(((Student) appData.getCurrentUser()).getName());

        return view;
    }

    private void setXML(View view)
    {
        logout = view.findViewById(R.id.logout);
        editProfile = view.findViewById(R.id.editProfile);
        viewPracticals = view.findViewById(R.id.viewPracs);
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
                bundle.putString("source","Student");
                bundle.putString("purpose","editProfile");
                newUserFragment.setArguments(bundle);
                ft.replace(R.id.main,newUserFragment).commit();
            }
        });

        viewPracticals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPracListFragment pracListFragment = new ViewPracListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source","Student");
                bundle.putString("purpose","viewPracticals");
                pracListFragment.setArguments(bundle);
                ft.replace(R.id.main,pracListFragment).commit();
            }
        });

    }
}