package com.example.pracgrader.fragments;

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

import java.util.LinkedList;
import java.util.List;

import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.Instructor;
import com.example.pracgrader.classfiles.Student;
import com.example.pracgrader.classfiles.FlagData;

public class ViewStudentListFragment extends Fragment {

    String source;
    String purpose;
    int pos;

    AppData appData = AppData.getInstance();


    public ViewStudentListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_student_list, container, false);

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
            studentListAdapter = new StudentListAdapter(((Instructor)appData.getCurrentUser()).getStudents());
        }
        else
        {
            studentListAdapter = new StudentListAdapter(appData.getStudents());
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
        List<String> names = flagData.getNames();
        List<Integer> flags = flagData.getFlags();

        flagListAdapter flagListAdapter = new flagListAdapter(getContext(),names,flags);

        flagSpinner.setAdapter(flagListAdapter);

        //Choice listen
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int result = filterSpinner.getSelectedItemPosition();

                if(result==0)
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

        Button back = view.findViewById(R.id.back);

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
            name.setText(student.getName());
            mark.setText(student.getEmail());
            flag.setImageResource(student.getCountry());
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    linearLayout.setBackgroundColor(Color.BLACK);
                    Toast.makeText(getActivity(), "Button Pressed", Toast.LENGTH_SHORT).show();
                    */
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
    }
}
