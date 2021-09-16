package com.example.pracgrader.fragments;

import static java.lang.Math.round;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pracgrader.R;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.example.pracgrader.classfiles.Admin;
import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.Instructor;
import com.example.pracgrader.classfiles.Student;
import com.example.pracgrader.classfiles.FlagData;

public class ViewStudentListFragment extends Fragment {

    String source;
    String purpose;
    int pos;

    Button filter;
    EditText searchName;
    Button back;
    int filterRes;
    Integer flagSelected;

    AppData appData = AppData.getInstance();


    public ViewStudentListFragment() {
        // Required empty public constructor
    }

    private void sortList(List<Student>students)
    {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student student, Student t1) {
                return student.getName().compareTo(t1.getName());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_student_list, container, false);
        back = view.findViewById(R.id.back);
        filter = view.findViewById(R.id.filterButton);
        searchName = view.findViewById(R.id.searchText);

        Bundle bundle = getArguments();
        source = bundle.getString("source");
        purpose = bundle.getString("purpose");
        pos = bundle.getInt("loc");

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.studentList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        StudentListAdapter studentListAdapter;
        if(source.equals("Instructor"))
        {
            List<Student> students = ((Instructor)appData.getCurrentUser()).getStudents();
            sortList(students);
            studentListAdapter = new StudentListAdapter(students);
        }
        else
        {
            List<Student> students = appData.getStudents();
            sortList(students);
            studentListAdapter = new StudentListAdapter(students);
        }
        rv.setAdapter(studentListAdapter);

        //Source Resources from XML
        Spinner filterSpinner = (Spinner) view.findViewById(R.id.choiceSpinner);
        EditText searchText = view.findViewById(R.id.searchText);
        Spinner flagSpinner = (Spinner) view.findViewById(R.id.flagSpinner);

        //Filter Spinner Setup
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.filters));
        filterAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        filterSpinner.setAdapter(filterAdapter);

        //Flag Spinner setup
        FlagData flagData = new FlagData();
        List<String> names = new LinkedList<>();
        List<Integer> flags = new LinkedList<>();
        List<Student> students;
        if(source.equals("Instructor"))
        {
            students = ((Instructor) appData.getCurrentUser()).getStudents();
        }
        else
        {
            students = appData.getStudents();
        }

        for(int i=0; i<students.size();i++)
        {
            Student student = students.get(i);
            if(!isInFlagList(student.getCountry(),flags))
            {
                int flagPos = flagData.getFlagInt(student.getCountry());
                names.add(flagData.getName(flagPos));
                flags.add(student.getCountry());
            }
        }

        flagListAdapter flagListAdapter = new flagListAdapter(getContext(),names,flags);

        flagSpinner.setAdapter(flagListAdapter);

        //Choice listen
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterRes = filterSpinner.getSelectedItemPosition();

                if(filterRes==0)
                {
                    searchText.setVisibility(View.VISIBLE);
                    flagSpinner.setVisibility(View.INVISIBLE);
                }
                else
                {
                    searchText.setVisibility(View.INVISIBLE);
                    flagSpinner.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        flagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                flagSelected = flags.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filterRes==0)
                {
                    if(searchName.length()>0)
                    {
                        List<Student> filteredList = new LinkedList<>();
                        for(int i=0; i<students.size();i++)
                        {
                            if(students.get(i).getName().contains(searchName.getText().toString()))
                            {
                                filteredList.add(students.get(i));
                            }
                        }
                        sortList(filteredList);
                        StudentListAdapter newStudentList = new StudentListAdapter(filteredList);
                        rv.swapAdapter(newStudentList,false);
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Search Field Empty",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    List<Student> filteredList = new LinkedList<>();
                    for (int i=0; i<students.size(); i++)
                    {
                        Integer flagid = students.get(i).getCountry();
                        if(flagid.equals(flagSelected))
                        {
                            filteredList.add(students.get(i));
                        }
                    }
                    sortList(filteredList);
                    StudentListAdapter newStudentList = new StudentListAdapter(filteredList);
                    rv.swapAdapter(newStudentList,false);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                if(source.equals("Admin"))
                {
                    ft.replace(R.id.main, new AdminHomeFragment()).commit();
                }
                else if(source.equals("Instructor"))
                {
                    ft.replace(R.id.main, new InstructorHomeFragment()).commit();
                }
            }
        });

        return view;
    }

    private boolean isInFlagList(Integer flagID,List<Integer> flags)
    {
        for(int i=0; i<flags.size();i++)
        {
            if(flags.get(i).equals(flagID))
            {
                return true;
            }
        }
        return false;
    }

    public class StudentListAdapter extends RecyclerView.Adapter<StudentListViewHolder>
    {
        List<Student> students = new LinkedList<Student>();

        public StudentListAdapter(List<Student> students)
        {
            this.students = students;
        }
        @NonNull
        @Override
        public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            StudentListViewHolder studentListViewHolder = new StudentListViewHolder(layoutInflater,parent);

            return studentListViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull StudentListViewHolder holder, int position) {
            holder.bind(students.get(position),position);
        }

        @Override
        public int getItemCount() {
            return students.size();
        }
    }

    public class StudentListViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView mark;
        ImageView flag;
        Button edit;
        LinearLayout linearLayout;

        public StudentListViewHolder(LayoutInflater li, ViewGroup parent) {
            super(li.inflate(R.layout.student_list_view, parent, false));
            name = itemView.findViewById(R.id.studentName);
            mark = itemView.findViewById(R.id.studentMark);
            flag = itemView.findViewById(R.id.studentFlag);
            edit = itemView.findViewById(R.id.studentEdit);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }

        public void bind(Student student,int position)
        {
            double avgMark = student.getAvgMark();
            name.setText(student.getName());
            double grade = (round(student.getAvgMark()*100))/100.0;
            mark.setText(Double.toString(grade));
            flag.setImageResource(student.getCountry());
            rowColor(avgMark);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    NewUserFragment newUserFragment = new NewUserFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("source",source);
                    bundle.putString("purpose","viewStudent");
                    bundle.putInt("loc",position);
                    newUserFragment.setArguments(bundle);
                    ft.replace(R.id.main,newUserFragment).commit();
                }
            });
        }

        public void rowColor(double mark)
        {
            if(mark>=0)
            {
                if (mark <= 50.0) {
                    linearLayout.setBackgroundColor(Color.RED);
                } else if (mark <= 80) {
                    linearLayout.setBackgroundColor(Color.YELLOW);
                } else {
                    linearLayout.setBackgroundColor(Color.GREEN);
                }
            }
        }
    }
}
