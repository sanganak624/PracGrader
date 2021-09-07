package com.example.pracgrader;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPracListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPracListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewPracListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment viewPracListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPracListFragment newInstance(String param1, String param2) {
        ViewPracListFragment fragment = new ViewPracListFragment();
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
        View view = inflater.inflate(R.layout.fragment_view_prac_list, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.pracList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        PracListAdapter pracListAdapter = new PracListAdapter();
        rv.setAdapter(pracListAdapter);

        return view;
    }

    public class PracListAdapter extends RecyclerView.Adapter<PracListViewHolder>{

        List<Prac> pracs = new LinkedList<>();
        @NonNull
        @Override
        public PracListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            PracListViewHolder pracListViewHolder = new PracListViewHolder(layoutInflater,parent);
            pracs.add(new Prac("Prac1",20,10,"brh"));
            pracs.add(new Prac("Prac1",21,-1,"brh"));
            pracs.add(new Prac("Prac1",265,-1,"brh"));
            return pracListViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PracListViewHolder holder, int position) {
            holder.bind(pracs.get(position));
        }

        @Override
        public int getItemCount() {
            return 3;
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
            if(prac.getMark()!=-1)
            {
                pracMark.setText(Integer.toString(prac.getMark()));
            }
            else
            {
                pracMark.setHint("/"+prac.getMaxMarks());
            }
        }
    }
}