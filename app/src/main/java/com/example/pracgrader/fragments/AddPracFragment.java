package com.example.pracgrader.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pracgrader.R;
import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.Instructor;
import com.example.pracgrader.classfiles.Prac;
import com.example.pracgrader.classfiles.Student;

import java.util.ArrayList;
import java.util.List;

public class AddPracFragment extends Fragment {

    public AddPracFragment() {
        // Required empty public constructor
    }
    AppData appData = AppData.getInstance();
    TextView title;
    TextView name;
    EditText description;
    EditText maxMark;

    Button back;
    Button save;
    Button delete;

    String version;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_prac, container, false);
        String source = "Admin";
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            source = bundle.getString("source","Admin");
            version = source;
        }

        setXML(view);
        onClicks();
        onFocusListeners();

        switch (source)
        {
            case "addPracticalsAdmin":
                title.setText("Add Practical");
                delete.setVisibility(View.GONE);
                break;
            case "viewPracticalsAdmin":
                title.setText("Edit Practical");
                delete.setVisibility(View.VISIBLE);
                break;
            default:
        }
        return view;
    }

    private void setXML(View view)
    {
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        maxMark = view.findViewById(R.id.maxMark);
        name = view.findViewById(R.id.name);
        back = view.findViewById(R.id.back);
        save = view.findViewById(R.id.savePrac);
        delete = view.findViewById(R.id.deletePrac);
    }

    private void onClicks()
    {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft.replace(R.id.main,new AdminHomeFragment()).commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(version.equals("addPracticalsAdmin"))
                {
                    if(desCheck()&&nameCheck()&&maxMarkCheck())
                    {
                        Prac newPrac = new Prac(name.getText().toString(),Double.parseDouble(maxMark.getText().toString())
                        ,description.getText().toString());

                        appData.addPrac(newPrac);
                        Toast.makeText(getContext(),"Practical Added",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Practical Failed To Added",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void onFocusListeners()
    {
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!title.hasFocus())
                {
                    nameCheck();
                }
            }
        });
        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!description.hasFocus())
                {
                    desCheck();
                }
            }
        });
        maxMark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!maxMark.hasFocus())
                {
                    maxMarkCheck();
                }
            }
        });
    }

    private boolean nameCheck()
    {
        boolean validName = true;
        if(name.length()==0)
        {
            Toast.makeText(getContext(),"Please Enter Name",Toast.LENGTH_SHORT).show();
            validName = false;
        }
        return  validName;
    }

    private boolean desCheck()
    {
        boolean validDes = true;
        if(description.length()==0)
        {
            Toast.makeText(getContext(),"Please Enter Description",Toast.LENGTH_SHORT).show();
            validDes = false;
        }
        return  validDes;
    }

    private boolean maxMarkCheck()
    {
        boolean validMax = true;
        if(maxMark.length()==0)
        {
            Toast.makeText(getContext(),"Please Enter Max Marks",Toast.LENGTH_SHORT).show();
            validMax = false;
        }
        return  validMax;
    }


}