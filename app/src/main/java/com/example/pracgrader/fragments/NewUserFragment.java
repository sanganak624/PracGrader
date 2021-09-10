package com.example.pracgrader.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.pracgrader.R;
import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.FlagData;
import com.example.pracgrader.classfiles.Instructor;
import com.example.pracgrader.classfiles.Student;


public class NewUserFragment extends Fragment {

    AppData appData = AppData.getInstance();
    TextView title;
    Spinner country;
    EditText name;
    EditText email;
    EditText userName;
    List<EditText> pin = new ArrayList<>();

    Integer flagSelected;

    Button back;
    Button register;
    Button delete;

    String version;

    List<String> flagNames = AppData.getInstance().getFlagName();
    List<Integer> flagIds = AppData.getInstance().getFlags();
    flagListAdapter flagListAdapter;


    public NewUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);

        String source = "Admin";
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            source = bundle.getString("source","Admin");
            version = source;
        }
        setXML(view);
        adapterListner();
        onClicks();
        onFocusListeners();

        switch (source)
        {
            case "addInstructorAdmin":
                title.setText("New Instructor");
                register.setText("Register");
                delete.setVisibility(View.GONE);
                break;
            case "addStudentAdmin":
                title.setText("New Student");
                register.setText("Register");
                delete.setVisibility(View.GONE);
                break;
            case "viewInstructorsAdmin":
                title.setText("Instructor Name");
                register.setText("Save");
                delete.setVisibility(View.VISIBLE);
                break;
            case "viewStudentsAdmin":
                title.setText("Student Name");
                register.setText("Save");
                delete.setVisibility(View.VISIBLE);
                break;
            case "editProfileInstructor":
                title.setText("Instructor Name");
                register.setText("Save");
                delete.setVisibility(View.GONE);
                break;
            case "addStudentInstructor":
                title.setText("New Student");
                register.setText("Register");
                delete.setVisibility(View.GONE);
                break;
            case "viewStudentsInstructor":
                title.setText("Student Name");
                register.setText("Save");
                delete.setVisibility(View.VISIBLE);
                break;
            default:
        }

        return view;
    }

    private void setXML(View view)
    {
        title = view.findViewById(R.id.title);
        country = (Spinner) view.findViewById(R.id.countryDropdown);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        userName = view.findViewById(R.id.username);
        pin.add(view.findViewById(R.id.pinNum1));
        pin.add(view.findViewById(R.id.pinNum2));
        pin.add(view.findViewById(R.id.pinNum3));
        pin.add(view.findViewById(R.id.pinNum4));

        back = view.findViewById(R.id.back);
        register = view.findViewById(R.id.register);
        delete = view.findViewById(R.id.delete);
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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userNameCheck()&&emailCheck()&&pinCheck()&&nameCheck())
                {
                    if(version.equals("addInstructorAdmin"))
                    {
                        Instructor newInstructor = new Instructor();
                        newInstructor.setUsername(userName.getText().toString());
                        newInstructor.setPin(AppData.stringToPin(pin));
                        newInstructor.setEmail(email.getText().toString());
                        newInstructor.setName(name.getText().toString());
                        newInstructor.setCountry(flagSelected);

                        appData.addInstructor(newInstructor);
                        Toast.makeText(getContext(),"Instructor added",Toast.LENGTH_SHORT).show();
                    }
                    else if(version.equals("addStudentAdmin"))
                    {
                        Student newStudent = new Student();
                        newStudent.setUsername(userName.getText().toString());
                        newStudent.setPin(AppData.stringToPin(pin));
                        newStudent.setEmail(email.getText().toString());
                        newStudent.setName(name.getText().toString());
                        newStudent.setCountry(flagSelected);

                        appData.addStudent(newStudent);
                        Toast.makeText(getContext(),"Student added",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"User Not Added",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void onFocusListeners()
    {

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!email.hasFocus())
                {
                    emailCheck();
                }
            }
        });
        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!userName.hasFocus())
                {
                    userNameCheck();
                }
            }
        });
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!name.hasFocus())
                {
                    nameCheck();
                }
            }
        });
    }

    private void adapterListner()
    {
        flagListAdapter = new flagListAdapter(getContext(),flagNames,flagIds);

        country.setAdapter(flagListAdapter);

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                flagSelected = flagIds.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private boolean userNameCheck()
    {
        // Unique Username
        boolean validUserName = appData.uniqueUsername(userName.getText().toString());
        if(userName.length()==0)
        {
            validUserName = false;
            Toast.makeText(getContext(),"Please enter username",Toast.LENGTH_SHORT).show();
        }
        else if(!validUserName)
        {
            Toast.makeText(getContext(),"Username taken",Toast.LENGTH_SHORT).show();
        }
        return validUserName;
    }

    private boolean emailCheck()
    {
        //Email Verification
        boolean validEmail = AppData.isValidEmail(email.getText());
        if(email.length()==0)
        {
            validEmail = false;
            Toast.makeText(getContext(),"Please enter email",Toast.LENGTH_SHORT).show();
        }
        else if(!validEmail)
        {
            Toast.makeText(getContext(),"Invalid email format",Toast.LENGTH_SHORT).show();
        }
        return validEmail;
    }

    private boolean pinCheck()
    {
        // Has Pin
        boolean validPin = true;
        if(pin.get(0).length()==0 || pin.get(1).length()==0
                || pin.get(2).length() ==0 || pin.get(3).length() ==0)
        {
            Toast.makeText(getContext(),"Enter 4 digit pin",Toast.LENGTH_SHORT).show();
            validPin = false;
        }
        return  validPin;
    }

    private boolean nameCheck()
    {
        boolean validName = true;
        if(name.length()==0)
        {
            Toast.makeText(getContext(),"Please Enter Name",Toast.LENGTH_SHORT).show();
            validName = false;
        }
        if(name.length()>22)
        {
            Toast.makeText(getContext(),"Name to long",Toast.LENGTH_SHORT).show();
            validName = false;
        }
        return  validName;
    }




}