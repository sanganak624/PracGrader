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
import com.example.pracgrader.classfiles.Student;
import com.example.pracgrader.classfiles.FlagData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewStudentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewStudentListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewStudentListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewStudentListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewStudentListFragment newInstance(String param1, String param2) {
        ViewStudentListFragment fragment = new ViewStudentListFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_student_list, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.studentList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        StudentListAdapter studentListAdapter = new StudentListAdapter();
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
                ft.replace(R.id.main,new AdminHomeFragment()).commit();
            }
        });

        return view;
    }

    public class StudentListAdapter extends RecyclerView.Adapter<StudentListViewHolder>
    {
        List<Student> students = new LinkedList<Student>();
        @NonNull
        @Override
        public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            StudentListViewHolder studentListViewHolder = new StudentListViewHolder(layoutInflater,parent);

            return studentListViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull StudentListViewHolder holder, int position) {
            holder.bind(AppData.getInstance().getStudent(position));
        }

        @Override
        public int getItemCount() {
            return AppData.getInstance().getStudents().size();
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

        public void bind(Student student)
        {
            name.setText(student.getName());
            mark.setText(student.getEmail());
            flag.setImageResource(student.getCountry());
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setBackgroundColor(Color.BLACK);
                    Toast.makeText(getActivity(), "Button Pressed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
