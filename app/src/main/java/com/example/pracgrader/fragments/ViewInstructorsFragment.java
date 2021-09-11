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

public class ViewInstructorsFragment extends Fragment {
    String source;
    String purpose;
    int pos;
    public ViewInstructorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_instructors, container, false);

        Bundle bundle = getArguments();
        source = bundle.getString("source");
        purpose = bundle.getString("purpose");
        pos = bundle.getInt("loc");

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
                ft.replace(R.id.main, new AdminHomeFragment()).commit();
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

            holder.bind(AppData.getInstance().getInstructor(position),position);
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

        public void bind(Instructor instructor, int position)
        {
            name.setText(instructor.getName());
            email.setText(instructor.getEmail());
            flag.setImageResource(instructor.getCountry());
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    NewUserFragment newUserFragment = new NewUserFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("source",source);
                    bundle.putString("purpose","viewInstructor");
                    bundle.putInt("loc",position);
                    newUserFragment.setArguments(bundle);
                    ft.replace(R.id.main,newUserFragment).commit();
                }
            });
        }
    }
}