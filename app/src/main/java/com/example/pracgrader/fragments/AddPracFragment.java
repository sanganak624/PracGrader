package com.example.pracgrader.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pracgrader.R;
import com.example.pracgrader.classfiles.AppData;

import java.util.ArrayList;
import java.util.List;

public class AddPracFragment extends Fragment {

    public AddPracFragment() {
        // Required empty public constructor
    }
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

    String version;

    List<String> flagNames = AppData.getInstance().getFlagName();
    List<Integer> flagIds = AppData.getInstance().getFlags();
    flagListAdapter flagListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_prac, container, false);
    }
}