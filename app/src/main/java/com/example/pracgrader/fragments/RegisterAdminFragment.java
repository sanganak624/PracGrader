package com.example.pracgrader.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pracgrader.R;
import com.example.pracgrader.classfiles.Admin;
import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.Instructor;

import java.util.LinkedList;
import java.util.List;


public class RegisterAdminFragment extends Fragment {
    EditText userName;
    TextView pinLabel;
    Button register;
    List<EditText> pin = new LinkedList<>();
    AppData appData = AppData.getInstance();

    int oldPin = 0;
    int attempt = 0;

    public RegisterAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_admin, container, false);
        setXML(view);
        onClicks();

        return view;
    }

    private void setXML(View view)
    {
        userName = view.findViewById(R.id.username);
        pinLabel = view.findViewById(R.id.pinLabel);
        register = view.findViewById(R.id.signIn);
        pin.add(view.findViewById(R.id.pinNum1));
        pin.add(view.findViewById(R.id.pinNum2));
        pin.add(view.findViewById(R.id.pinNum3));
        pin.add(view.findViewById(R.id.pinNum4));
    }

    private void onClicks()
    {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(attempt == 0)
                {
                    if (pinCheck() && userNameCheck()) {
                        oldPin = AppData.stringToPin(pin);
                        pinLabel.setText("Confirm Pin");
                        register.setText("Confirm Pin");
                        userName.setEnabled(false);
                        userName.setInputType(InputType.TYPE_NULL);
                        clearPin();
                        Toast.makeText(getContext(),"Confirm Pin",Toast.LENGTH_SHORT).show();
                        attempt++;
                    }
                }
                else if(attempt == 1)
                {
                    int newPin = AppData.stringToPin(pin);
                    if(oldPin==newPin)
                    {
                        Admin admin = new Admin();
                        admin.setPin(newPin);
                        admin.setUsername(userName.getText().toString());
                        appData.getAdminDb().addAdmin(admin);
                        Toast.makeText(getContext(),"Admin Created",Toast.LENGTH_SHORT).show();
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.main, new LoginFragment()).commit();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Pin Mismatch",Toast.LENGTH_SHORT).show();
                        pinLabel.setText("Enter Pin");
                        register.setText("Register");
                        userName.setEnabled(true);
                        userName.setInputType(InputType.TYPE_CLASS_TEXT);
                        attempt=0;
                    }
                }
            }
        });
    }

    private void clearPin()
    {
        pin.get(0).setText("");
        pin.get(1).setText("");
        pin.get(2).setText("");
        pin.get(3).setText("");
    }

    private boolean pinCheck()
    {
        // Has Pin
        boolean validPin = true;
        if(pin.get(0).length()==0 || pin.get(1).length()==0
                || pin.get(2).length() ==0 || pin.get(3).length() ==0)
        {
            Toast.makeText(getContext(),"Enter 4 digit pin",Toast.LENGTH_SHORT).show();
            validPin = false;
        }
        return  validPin;
    }

    private boolean userNameCheck()
    {
        // Unique Username
        boolean validUserName = appData.uniqueUsername(userName.getText().toString());
        if(userName.length()==0)
        {
            validUserName = false;
            Toast.makeText(getContext(),"Please enter username",Toast.LENGTH_SHORT).show();
        }
        else if(!validUserName)
        {
            Toast.makeText(getContext(),"Username taken",Toast.LENGTH_SHORT).show();
        }
        return validUserName;
    }
}