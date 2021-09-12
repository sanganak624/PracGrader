package com.example.pracgrader.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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

    String source;
    String purpose;
    int pos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_prac, container, false);

        Bundle bundle = getArguments();
        source = bundle.getString("source");
        purpose = bundle.getString("purpose");
        pos = bundle.getInt("loc");
        setXML(view);
        onClicks();
        onFocusListeners();

        switch (purpose)
        {
            case "addPracticals":
                title.setText("Add Practical");
                delete.setVisibility(View.GONE);
                break;
            case "viewPractical":
                title.setText("Edit Practical");
                delete.setVisibility(View.VISIBLE);

                Prac prac = appData.getPrac(pos);

                name.setText(prac.getTitle());
                description.setText(prac.getDescription());
                maxMark.setText(Double.toString(prac.getMaxMarks()));
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
                if(purpose.equals("addPracticals"))
                {
                    ft.replace(R.id.main, new AdminHomeFragment()).commit();
                }
                else if(purpose.equals("viewPractical"))
                {
                    ViewPracListFragment pracListFragment = new ViewPracListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("source","Admin");
                    bundle.putString("purpose","viewPracticals");
                    pracListFragment.setArguments(bundle);
                    if(appData.isTablet())
                    {
                        FrameLayout frame = appData.getSub();
                        frame.setVisibility(View.GONE);
                        ft.replace(R.id.sub,pracListFragment).commit();
                    }
                    else
                    {
                        ft.replace(R.id.main, pracListFragment).commit();
                    }
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(desCheck()&&nameCheck()&&maxMarkCheck())
                {
                    if(purpose.equals("addPracticals"))
                    {

                        Prac newPrac = new Prac(name.getText().toString(),Double.parseDouble(maxMark.getText().toString())
                        ,description.getText().toString());

                        appData.getPracDb().addPrac(newPrac);
                        for(int i=0; i<appData.getStudents().size(); i++)
                        {
                            Student student = appData.getStudent(i);
                            student.addPrac(newPrac);
                        }
                        Toast.makeText(getContext(),"Practical Added",Toast.LENGTH_SHORT).show();

                        ft.replace(R.id.main,new AdminHomeFragment()).commit();
                    }
                    else if(purpose.equals("viewPractical"))
                    {
                        Prac prac = appData.getPrac(pos);

                        for(int i=0; i<appData.getStudents().size(); i++)
                        {
                            Student student = appData.getStudent(i);
                            for(int j=0; j<student.getPracs().size(); j++)
                            {
                                Prac prac1 = student.getPracs().get(j);

                                if(prac1.getTitle().equals(prac.getTitle())
                                        &&prac1.getDescription().equals(prac.getDescription()))
                                {
                                    Prac prac2 = student.getPracs().get(j);
                                    prac2.setMaxMarks(Double.parseDouble(maxMark.getText().toString()));
                                    prac2.setDescription(description.getText().toString());
                                    prac2.setTitle(name.getText().toString());
                                    j = student.getPracs().size();
                                }
                            }
                        }
                        prac.setMaxMarks(Double.parseDouble(maxMark.getText().toString()));
                        prac.setDescription(description.getText().toString());
                        prac.setTitle(name.getText().toString());

                        appData.getPracDb().editPrac(prac);

                        Toast.makeText(getContext(),"Practical Edited",Toast.LENGTH_SHORT).show();

                        ViewPracListFragment pracListFragment = new ViewPracListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("source","Admin");
                        bundle.putString("purpose","viewPracticals");
                        pracListFragment.setArguments(bundle);
                        if(appData.isTablet())
                        {
                            FrameLayout frame = appData.getSub();
                            frame.setVisibility(View.GONE);
                            //ft.replace(R.id.sub,pracListFragment);
                            ft.replace(R.id.main, pracListFragment).commit();
                        }
                        else
                        {
                            ft.replace(R.id.main, pracListFragment).commit();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Practical Failed To Save",Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prac prac = appData.getPrac(pos);
                appData.getPracDb().removePrac(prac);
                for(int i=0; i<appData.getStudents().size(); i++)
                {
                    Student student = appData.getStudent(i);
                    for(int j=0; j<student.getPracs().size(); j++)
                    {
                        Prac prac1 = student.getPracs().get(j);

                        if(prac1.getTitle().equals(prac.getTitle())
                                &&prac1.getDescription().equals(prac.getDescription()))
                        {
                            student.getPracs().remove(j);
                            j = student.getPracs().size();
                        }
                    }
                }

                Toast.makeText(getContext(),"Practical Removed",Toast.LENGTH_SHORT).show();
                ViewPracListFragment pracListFragment = new ViewPracListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source","Admin");
                bundle.putString("purpose","viewPracticals");
                pracListFragment.setArguments(bundle);
                if(appData.isTablet())
                {
                    FrameLayout frame = appData.getSub();
                    frame.setVisibility(View.GONE);
                    ft.replace(R.id.sub,pracListFragment).commit();
                }
                else
                {
                    ft.replace(R.id.main, pracListFragment).commit();
                }
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
        boolean validName = appData.uniquePracName(name.getText().toString());
        if(name.length()==0)
        {
            Toast.makeText(getContext(),"Please Enter Name",Toast.LENGTH_SHORT).show();
            validName = false;
        }
        else if(purpose.equals("viewPractical"))
        {
            Prac prac = appData.getPrac(pos);
            if (prac.getTitle().equals(name.getText().toString())) {
                validName = true;
            }
        }
        else if(!validName)
        {
            Toast.makeText(getContext(),"Prac Name Used",Toast.LENGTH_SHORT).show();
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
        if(Double.parseDouble(maxMark.getText().toString())<=0)
        {
            Toast.makeText(getContext(),"Please Enter Mark Greater Than 0",Toast.LENGTH_SHORT).show();
            validMax = false;
        }
        return  validMax;
    }


}