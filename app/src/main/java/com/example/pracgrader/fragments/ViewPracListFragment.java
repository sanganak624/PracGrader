package com.example.pracgrader.fragments;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pracgrader.R;

import java.util.LinkedList;
import java.util.List;

import com.example.pracgrader.classfiles.Admin;
import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.Instructor;
import com.example.pracgrader.classfiles.Prac;
import com.example.pracgrader.classfiles.Student;


public class ViewPracListFragment extends Fragment {

    public ViewPracListFragment() {
    }

    String source;
    String purpose;
    int pos;
    AppData appData = AppData.getInstance();
    Button back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_prac_list, container, false);

        Bundle bundle = getArguments();
        source = bundle.getString("source");
        purpose = bundle.getString("purpose");
        pos = bundle.getInt("loc");


        RecyclerView rv = (RecyclerView) view.findViewById(R.id.pracList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Prac> pracs;
        if(source.equals("Admin"))
        {
            if(purpose.equals("gradePrac"))
            {
                pracs = ((Admin) appData.getCurrentUser()).getStudents().get(pos).getPracs();
            }
            else
            {
                pracs = appData.getPracs();
            }
        }
        else if(source.equals("Instructor"))
        {
            pracs = ((Instructor) appData.getCurrentUser()).getStudents().get(pos).getPracs();
        }
        else
        {
            pracs = ((Student) appData.getCurrentUser()).getPracs();
        }

        PracListAdapter pracListAdapter = new PracListAdapter(pracs);
        rv.setAdapter(pracListAdapter);

        back = view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                if(purpose.equals("gradePrac"))
                {
                    ViewStudentListFragment viewStudentListFragment = new ViewStudentListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("source",source);
                    bundle.putString("purpose","viewStudents");
                    viewStudentListFragment.setArguments(bundle);
                    ft.replace(R.id.main,viewStudentListFragment).commit();
                }
                else if (purpose.equals("viewPracticals"))
                {
                    if(source.equals("Admin"))
                    {
                        ft.replace(R.id.main, new AdminHomeFragment()).commit();
                    }
                    else if(source.equals("Student"))
                    {
                        ft.replace(R.id.main, new StudentHomeFragment()).commit();
                    }
                }
            }
        });
        return view;
    }

    public class PracListAdapter extends RecyclerView.Adapter<PracListViewHolder>{
        List<Prac> pracs = new LinkedList<Prac>();

        public PracListAdapter(List<Prac> pracs)
        {
            this.pracs = pracs;
        }

        @NonNull
        @Override
        public PracListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            PracListViewHolder pracListViewHolder = new PracListViewHolder(layoutInflater,parent);
            return pracListViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PracListViewHolder holder, int position) {
            holder.bind(pracs.get(position),position);
        }

        @Override
        public int getItemCount() {
            return pracs.size();
        }
    }


    public class PracListViewHolder extends RecyclerView.ViewHolder{
        TextView pracTitle;
        EditText pracMark;
        Button pracGrade;
        Button update;

        public PracListViewHolder(LayoutInflater li, ViewGroup parent) {
            super(li.inflate(R.layout.prac_list_view, parent, false));
            pracTitle = itemView.findViewById(R.id.pracTitle);
            pracMark = itemView.findViewById(R.id.pracMark);
            pracGrade = itemView.findViewById(R.id.pracGrade);
            update = itemView.findViewById(R.id.pracGrade);

            if(source.equals("Student"))
            {
                update.setVisibility(View.GONE);
                pracMark.setEnabled(false);
                pracMark.setInputType(InputType.TYPE_NULL);
            }


        }

        public void bind(Prac prac,int pos)
        {
            pracTitle.setText(prac.getTitle());
            if(source.equals("Admin")&&purpose.equals("viewPracticals"))
            {
                pracMark.setHint("/"+prac.getMaxMarks());
                update.setText("Edit");
            }
            else
            {
                if (prac.getMark() != -1) {
                    if(source.equals("Student"))
                    {
                        pracMark.setText(Double.toString(prac.getMark())+"/"+Double.toString(prac.getMaxMarks()));
                    }
                    else
                    {
                        pracMark.setText(Double.toString(prac.getMark()));
                    }
                } else {
                    pracMark.setHint("/" + prac.getMaxMarks());
                }
            }
            pracMark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(!pracMark.hasFocus())
                    {
                        checkMark(prac);
                    }
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    if(source.equals("Admin")&&purpose.equals("viewPracticals"))
                    {
                        AddPracFragment viewPracListFragment = new AddPracFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("source",source);
                        bundle.putString("purpose","viewPractical");
                        bundle.putInt("loc",pos);
                        viewPracListFragment.setArguments(bundle);
                        ft.replace(R.id.main,viewPracListFragment).commit();
                    }
                    else
                    {
                        if(checkMark(prac))
                        {
                            prac.setMark(Double.parseDouble(pracMark.getText().toString()));
                            Toast.makeText(getContext(),"Mark Updated",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Mark Update Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }


        private boolean checkMark(Prac prac)
        {
            boolean validMark = true;
            if(pracMark.length()==0)
            {
                validMark = false;
                Toast.makeText(getContext(),"Please Enter Mark",Toast.LENGTH_SHORT).show();
            }
            else if(prac.getMaxMarks()<Double.parseDouble(pracMark.getText().toString()))
            {
                validMark = false;
                Toast.makeText(getContext(),"Invalid Mark",Toast.LENGTH_SHORT).show();
            }
            return validMark;
        }
    }
}