package com.example.pracgrader.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.Instructor;
import com.example.pracgrader.R;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewInstructorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewInstructorsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewInstructorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewInstructorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewInstructorsFragment newInstance(String param1, String param2) {
        ViewInstructorsFragment fragment = new ViewInstructorsFragment();
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
        View view = inflater.inflate(R.layout.fragment_view_instructors, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.instructorList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        InstructorListAdapter instructorListAdapter = new InstructorListAdapter();
        rv.setAdapter(instructorListAdapter);

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

    public class InstructorListAdapter extends RecyclerView.Adapter<InstructorListViewHolder>
    {
        List<Instructor> instructorList = new LinkedList<Instructor>();
        @NonNull
        @Override
        public InstructorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            InstructorListViewHolder instructorListViewHolder = new InstructorListViewHolder(layoutInflater,parent);
            return instructorListViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull InstructorListViewHolder holder, int position) {

            holder.bind(AppData.getInstance().getInstructor(position));
        }

        @Override
        public int getItemCount() {
            return AppData.getInstance().getInstructors().size();
        }
    }

    public class InstructorListViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView email;
        ImageView flag;
        Button edit;

        public InstructorListViewHolder(LayoutInflater li, ViewGroup parent) {
            super(li.inflate(R.layout.instructor_list_view, parent, false));

            name = itemView.findViewById(R.id.instructorName);
            email = itemView.findViewById(R.id.instructorEmail);
            flag = itemView.findViewById(R.id.instructorFlag);
            edit = itemView.findViewById(R.id.editInstructor);
        }

        public void bind(Instructor instructor)
        {
            name.setText(instructor.getName());
            email.setText(instructor.getEmail());
            flag.setImageResource(instructor.getCountry());
        }
    }
}