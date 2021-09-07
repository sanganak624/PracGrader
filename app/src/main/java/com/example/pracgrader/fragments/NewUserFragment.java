package com.example.pracgrader.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.pracgrader.R;
import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.FlagData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewUserFragment extends Fragment {

    Spinner country;
    EditText name;
    EditText email;
    EditText userName;
    List<EditText> pin = new ArrayList<>();

    Integer flagSelected;

    Button back;
    Button register;

    List<String> flagNames = AppData.getInstance().getFlagName();
    List<Integer> flagIds = AppData.getInstance().getFlags();
    com.example.pracgrader.fragments.flagListAdapter flagListAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewUserFragment newInstance(String param1, String param2) {
        NewUserFragment fragment = new NewUserFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);

        String source = "Admin";
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            source = bundle.getString("source","Admin");
        }
        setXML(view);
        adapterListner();
        onClicks();

        switch (source)
        {
            case "editProfileAdmin":
                name.setText(source);
                break;
            case "addInstructorAdmin":
                name.setText(source);
                break;
            case "addStudentAdmin":
                name.setText(source);
                break;
            case "viewInstructorsAdmin":
                name.setText(source);
                break;
            case "viewStudentsAdmin":
                name.setText(source);
                break;
            case "editProfileInstructor":
                break;
            case "addStudentInstructor":
                break;
            case "viewStudentsInstructor":
                break;
            case "editProfileStudent":
                break;
            default:

        }



        return view;
    }

    private void setXML(View view)
    {
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
    }

    private void adapterListner()
    {
        flagListAdapter = new flagListAdapter(getContext(),flagNames,flagIds);

        country.setAdapter(flagListAdapter);

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),flagNames.get(i),Toast.LENGTH_SHORT).show();
                flagSelected = flagIds.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}