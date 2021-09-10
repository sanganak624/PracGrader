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
import android.widget.EditText;
import android.widget.TextView;

import com.example.pracgrader.R;

import java.util.LinkedList;
import java.util.List;

import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.Prac;


public class ViewPracListFragment extends Fragment {

    public ViewPracListFragment() {
    }

    String version = "Admin";
    AppData appData = AppData.getInstance();
    Button back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_prac_list, container, false);

        String source = "Admin";
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            source = bundle.getString("source","Admin");
            version = source;
        }


        RecyclerView rv = (RecyclerView) view.findViewById(R.id.pracList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        PracListAdapter pracListAdapter = new PracListAdapter();
        rv.setAdapter(pracListAdapter);

        back = view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.main,new AdminHomeFragment()).commit();
            }
        });
        return view;
    }

    public class PracListAdapter extends RecyclerView.Adapter<PracListViewHolder>{

        @NonNull
        @Override
        public PracListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            PracListViewHolder pracListViewHolder = new PracListViewHolder(layoutInflater,parent);
            return pracListViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PracListViewHolder holder, int position) {
            holder.bind(appData.getPrac(position));
        }

        @Override
        public int getItemCount() {
            return appData.getPracs().size();
        }
    }


    public class PracListViewHolder extends RecyclerView.ViewHolder{
        TextView pracTitle;
        EditText pracMark;
        Button pracGrade;


        public PracListViewHolder(LayoutInflater li, ViewGroup parent) {
            super(li.inflate(R.layout.prac_list_view, parent, false));
            pracTitle = itemView.findViewById(R.id.pracTitle);
            pracMark = itemView.findViewById(R.id.pracMark);
            pracGrade = itemView.findViewById(R.id.pracGrade);
        }

        public void bind(Prac prac)
        {
            pracTitle.setText(prac.getTitle());
            if(version.equals("viewPracticalsAdmin"))
            {
                pracMark.setHint("/"+prac.getMaxMarks());
            }
            else
            {
                if (prac.getMark() != -1) {
                    pracMark.setText(Double.toString(prac.getMark()));
                } else {
                    pracMark.setHint("/" + prac.getMaxMarks());
                }
            }

        }
    }
}