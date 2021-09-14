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
    Button grade;

    String source;
    String purpose;
    int pos;
    FlagData flags = new FlagData();
    List<String> flagNames = flags.getNames();
    List<Integer> flagIds = flags.getFlags();
    flagListAdapter flagListAdapter;


    public NewUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);

        Bundle bundle = getArguments();
        source = bundle.getString("source");
        purpose = bundle.getString("purpose");
        pos = bundle.getInt("loc");

        setXML(view);
        adapterListner();
        onClicks();
        onFocusListeners();
        if(source.equals("Admin"))
        {
            switch (purpose)
            {
                case "addInstructor":
                    title.setText("New Instructor");
                    register.setText("Register");
                    delete.setVisibility(View.GONE);
                    grade.setVisibility(View.GONE);
                    break;
                case "addStudent":
                    title.setText("New Student");
                    register.setText("Register");
                    delete.setVisibility(View.GONE);
                    grade.setVisibility(View.GONE);
                    break;
                case "viewInstructor":
                    title.setText("Edit Instructor");
                    register.setText("Save");
                    delete.setVisibility(View.VISIBLE);
                    grade.setVisibility(View.GONE);

                    Instructor instructor = appData.getInstructor(pos);

                    name.setText(instructor.getName());
                    email.setText(instructor.getEmail());
                    userName.setText(instructor.getUsername());
                    country.setSelection(flagListAdapter.getFlagPosition(instructor.getCountry()));
                    String strPin = Integer.toString(instructor.getPin());
                    pin.get(0).setText(Character.toString(strPin.charAt(0)));
                    pin.get(1).setText(Character.toString(strPin.charAt(1)));
                    pin.get(2).setText(Character.toString(strPin.charAt(2)));
                    pin.get(3).setText(Character.toString(strPin.charAt(3)));

                    break;
                case "viewStudent":
                    title.setText("Student Name");
                    register.setText("Save");
                    delete.setVisibility(View.VISIBLE);
                    grade.setVisibility(View.VISIBLE);

                    Student student = appData.getStudent(pos);

                    name.setText(student.getName());
                    email.setText(student.getEmail());
                    userName.setText(student.getUsername());
                    country.setSelection(flagListAdapter.getFlagPosition(student.getCountry()));
                    strPin = Integer.toString(student.getPin());
                    pin.get(0).setText(Character.toString(strPin.charAt(0)));
                    pin.get(1).setText(Character.toString(strPin.charAt(1)));
                    pin.get(2).setText(Character.toString(strPin.charAt(2)));
                    pin.get(3).setText(Character.toString(strPin.charAt(3)));

                    break;
                default:
            }
        }
        else if (source.equals("Instructor"))
        {
            Instructor instructor = (Instructor) appData.getCurrentUser();
            switch (purpose)
            {
                case "editProfile":
                    title.setText("Instructor Name");
                    register.setText("Save");
                    delete.setVisibility(View.GONE);
                    grade.setVisibility(View.GONE);

                    name.setText(instructor.getName());
                    email.setText(instructor.getEmail());
                    userName.setText(instructor.getUsername());
                    country.setSelection(flagListAdapter.getFlagPosition(instructor.getCountry()));
                    String strPin = Integer.toString(instructor.getPin());
                    pin.get(0).setText(Character.toString(strPin.charAt(0)));
                    pin.get(1).setText(Character.toString(strPin.charAt(1)));
                    pin.get(2).setText(Character.toString(strPin.charAt(2)));
                    pin.get(3).setText(Character.toString(strPin.charAt(3)));

                    break;
                case "addStudent":
                    title.setText("New Student");
                    register.setText("Register");
                    delete.setVisibility(View.GONE);
                    grade.setVisibility(View.GONE);

                    break;
                case "viewStudent":
                    title.setText("Student Name");
                    register.setText("Save");
                    delete.setVisibility(View.VISIBLE);
                    grade.setVisibility(View.VISIBLE);
                    Student student = instructor.getStudents().get(pos);
                    name.setText(student.getName());
                    email.setText(student.getEmail());
                    userName.setText(student.getUsername());
                    country.setSelection(flagListAdapter.getFlagPosition(student.getCountry()));
                    strPin = Integer.toString(student.getPin());
                    pin.get(0).setText(Character.toString(strPin.charAt(0)));
                    pin.get(1).setText(Character.toString(strPin.charAt(1)));
                    pin.get(2).setText(Character.toString(strPin.charAt(2)));
                    pin.get(3).setText(Character.toString(strPin.charAt(3)));

                    break;

            }

        }
        else if(source.equals("Student"))
        {
            switch (purpose)
            {
                case "editProfile":
                    title.setText("Instructor Name");
                    register.setText("Save");
                    delete.setVisibility(View.GONE);
                    grade.setVisibility(View.GONE);
                    Student student = (Student) appData.getCurrentUser();
                    name.setText(student.getName());
                    email.setText(student.getEmail());
                    userName.setText(student.getUsername());
                    country.setSelection(flagListAdapter.getFlagPosition(student.getCountry()));
                    String strPin = Integer.toString(student.getPin());
                    pin.get(0).setText(Character.toString(strPin.charAt(0)));
                    pin.get(1).setText(Character.toString(strPin.charAt(1)));
                    pin.get(2).setText(Character.toString(strPin.charAt(2)));
                    pin.get(3).setText(Character.toString(strPin.charAt(3)));
                    break;
                default:
            }
        }

        return view;
    }

    private void setXML(View view)
    {
        title = view.findViewById(R.id.title);
        country = (Spinner) view.findViewById(R.id.countryDropdown);
        grade = view.findViewById(R.id.grade);
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
                changeFragment();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userNameCheck()&&emailCheck()&&pinCheck()&&nameCheck())
                {
                    if(purpose.equals("addInstructor"))
                    {
                        Instructor newInstructor = new Instructor();
                        newInstructor.setUsername(userName.getText().toString());
                        newInstructor.setPin(AppData.stringToPin(pin));
                        newInstructor.setEmail(email.getText().toString());
                        newInstructor.setName(name.getText().toString());
                        newInstructor.setCountry(flagSelected);

                        appData.getInstructorDb().addInstructor(newInstructor);

                        Toast.makeText(getContext(),"Instructor added",Toast.LENGTH_SHORT).show();
                    }
                    else if(purpose.equals("addStudent"))
                    {
                        Student newStudent = new Student();
                        newStudent.setUsername(userName.getText().toString());
                        newStudent.setPin(AppData.stringToPin(pin));
                        newStudent.setEmail(email.getText().toString());
                        newStudent.setName(name.getText().toString());
                        newStudent.setCountry(flagSelected);
                        appData.getStudentDb().addStudent(newStudent,appData.getCurrentUser());

                        /*if(source.equals("Instructor"))
                        {
                            Instructor instructor = (Instructor) appData.getCurrentUser();
                            appData.getStudentDb().addStudent(newStudent,instructor);
                        }*/
                        Toast.makeText(getContext(), "Student added", Toast.LENGTH_SHORT).show();
                    }
                    else if(purpose.equals("viewInstructor"))
                    {
                        Instructor instructor = appData.getInstructor(pos);
                        instructor.setName(name.getText().toString());
                        instructor.setEmail(email.getText().toString());
                        instructor.setUsername(userName.getText().toString());
                        instructor.setCountry(flagSelected);
                        instructor.setPin(AppData.stringToPin(pin));

                        appData.getInstructorDb().editInstructor(instructor);

                        Toast.makeText(getContext(),"Instructor Details Updated",Toast.LENGTH_SHORT).show();
                    }
                    else if(purpose.equals("viewStudent"))
                    {
                        if(source.equals("Admin"))
                        {
                            Student student = appData.getStudent(pos);
                            student.setName(name.getText().toString());
                            student.setEmail(email.getText().toString());
                            student.setUsername(userName.getText().toString());
                            student.setCountry(flagSelected);
                            student.setPin(AppData.stringToPin(pin));
                            appData.getStudentDb().editStudent(student);
                        }
                        else if(source.equals("Instructor"))
                        {
                            Instructor instructor = (Instructor) appData.getCurrentUser();
                            Student student = instructor.getStudents().get(pos);
                            student.setName(name.getText().toString());
                            student.setEmail(email.getText().toString());
                            student.setUsername(userName.getText().toString());
                            student.setCountry(flagSelected);
                            student.setPin(AppData.stringToPin(pin));
                            appData.getStudentDb().editStudent(student);
                        }
                        Toast.makeText(getContext(),"Student Details Updated",Toast.LENGTH_SHORT).show();
                    }
                    else if(purpose.equals("editProfile"))
                    {
                        if(source.equals("Instructor"))
                        {
                            Instructor instructor = (Instructor) appData.getCurrentUser();
                            instructor.setName(name.getText().toString());
                            instructor.setEmail(email.getText().toString());
                            instructor.setUsername(userName.getText().toString());
                            instructor.setCountry(flagSelected);
                            instructor.setPin(AppData.stringToPin(pin));
                            appData.getInstructorDb().editInstructor(instructor);
                            Toast.makeText(getContext(), "Details Updated", Toast.LENGTH_SHORT).show();
                        }
                        else if(source.equals("Student"))
                        {
                            Student student = (Student) appData.getCurrentUser();
                            student.setName(name.getText().toString());
                            student.setEmail(email.getText().toString());
                            student.setUsername(userName.getText().toString());
                            student.setCountry(flagSelected);
                            student.setPin(AppData.stringToPin(pin));
                            appData.getStudentDb().editStudent(student);
                            Toast.makeText(getContext(), "Details Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                    changeFragment();
                }
                else
                {
                    Toast.makeText(getContext(),"User Not Added",Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(purpose.equals("viewInstructor"))
                {
                    appData.getInstructorDb().removeInstructor(appData.getInstructors().get(pos));
                    Toast.makeText(getContext(),"Instructor Removed",Toast.LENGTH_SHORT).show();
                }
                if(purpose.equals("viewStudent"))
                {
                    if(source.equals("Admin"))
                    {
                        Student student = appData.getStudent(pos);
                        appData.getStudents().remove(student);
                        List<Instructor> instructors = appData.getInstructors();
                        for(int i=0;i<instructors.size();i++)
                        {
                            if(instructors.get(i).getStudents().remove(student))
                            {
                                i=instructors.size();
                            }
                        }
                        appData.getStudentDb().removeStudent(student);
                    }
                    else if(source.equals("Instructor"))
                    {
                        Instructor instructor = (Instructor) appData.getCurrentUser();
                        Student student = instructor.getStudents().get(pos);
                        instructor.getStudents().remove(student);
                        appData.getStudents().remove(student);
                        appData.getStudentDb().removeStudent(student);
                    }
                    Toast.makeText(getContext(),"Student Removed",Toast.LENGTH_SHORT).show();
                }
                changeFragment();
            }
        });

        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPracListFragment viewPracListFragment = new ViewPracListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source",source);
                bundle.putString("purpose","gradePrac");
                bundle.putInt("loc",pos);
                viewPracListFragment.setArguments(bundle);
                ft.replace(R.id.main,viewPracListFragment).commit();
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
        if(source.equals("Student")||(source.equals("Instructor")&&purpose.equals("editProfile")))
        {
            if(appData.getCurrentUser().getUsername().equals(userName.getText().toString()))
            {
                validUserName = true;
            }
        }
        else if(purpose.equals("viewInstructor"))
        {
            if(appData.getInstructor(pos).getUsername().equals(userName.getText().toString()))
            {
                validUserName = true;
            }
        }
        else if(purpose.equals("viewStudent"))
        {
            if(source.equals("Admin")) {
                if (appData.getStudent(pos).getUsername().equals(userName.getText().toString())) {
                    validUserName = true;
                }
            }
            else if(source.equals("Instructor"))
            {
                if (((Instructor)appData.getCurrentUser()).getStudents().get(pos).getUsername().equals(userName.getText().toString())) {
                    validUserName = true;
                }
            }
        }
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

    private void changeFragment()
    {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        if(source.equals("Admin"))
        {
            if(purpose.equals("viewInstructor"))
            {
                ViewInstructorsFragment viewInstructorsFragment = new ViewInstructorsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source",source);
                bundle.putString("purpose","viewInstructors");
                viewInstructorsFragment.setArguments(bundle);
                ft.replace(R.id.main,viewInstructorsFragment).commit();
            }
            else if(purpose.equals("viewStudent"))
            {
                ViewStudentListFragment viewStudentListFragment = new ViewStudentListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("source",source);
                bundle.putString("purpose","viewStudents");
                viewStudentListFragment.setArguments(bundle);
                ft.replace(R.id.main,viewStudentListFragment).commit();
            }
            else {
                ft.replace(R.id.main, new AdminHomeFragment()).commit();
            }
        }
        else if(source.equals("Instructor"))
        {
            ft.replace(R.id.main, new InstructorHomeFragment()).commit();
        }
        else if(source.equals("Student"))
        {
            ft.replace(R.id.main, new StudentHomeFragment()).commit();
        }
    }


}