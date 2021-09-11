package com.example.pracgrader.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pracgrader.R;

import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.User;

import java.util.LinkedList;
import java.util.List;


public class LoginFragment extends Fragment {
    private Button signIn;
    private List<EditText> pin = new LinkedList<>();
    private EditText userName;
    private AppData appData = AppData.getInstance();

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setXML(view);
        onFocus();
        onClicks();

       return view;
    }

    private void onClicks()
    {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userNameCheck()&&pinCheck())
                {
                    User curUser = appData.getCurrentUser();
                    FragmentTransaction fm = getParentFragmentManager().beginTransaction();
                    if(curUser.getRole()==1)
                    {
                        fm.replace(R.id.main,new AdminHomeFragment()).commit();
                    }
                    else if(curUser.getRole()==2)
                    {
                        fm.replace(R.id.main,new InstructorHomeFragment()).commit();
                    }
                    else if(curUser.getRole()==3)
                    {
                        fm.replace(R.id.main,new StudentHomeFragment()).commit();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Invalid User Details",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onFocus()
    {
        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!userName.hasFocus()) {
                    userNameCheck();
                }
            }
        });
    }

    private boolean userNameCheck()
    {
        // Unique Username
        boolean validUsername = true;
        if(userName.length()!=0)
        {
            User curUser = appData.findUsername(userName.getText().toString());

            if(curUser!=null) {
                appData.setCurrentUser(curUser);
                validUsername = true;
            }
            else {
                validUsername = false;
                Toast.makeText(getContext(),"Invalid Username",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            validUsername = false;
            Toast.makeText(getContext(),"Enter Username",Toast.LENGTH_SHORT).show();
        }
        return validUsername;
    }

    private boolean pinCheck()
    {
        boolean correctPin = false;
        if(this.pin.get(0).length()==0 || this.pin.get(1).length()==0
                || this.pin.get(2).length() ==0 || this.pin.get(3).length() ==0)
        {
            Toast.makeText(getContext(),"Enter 4 digit pin",Toast.LENGTH_SHORT).show();
            correctPin = false;
        }
        else
        {
            int pin = AppData.stringToPin(this.pin);
            if (appData.getCurrentUser().getPin() == pin) {
                correctPin = true;
            }
        }
        return correctPin;
    }

    private void setXML(View view)
    {
        signIn = view.findViewById(R.id.signIn);
        userName = view.findViewById(R.id.username);
        pin.add(view.findViewById(R.id.pinNum1));
        pin.add(view.findViewById(R.id.pinNum2));
        pin.add(view.findViewById(R.id.pinNum3));
        pin.add(view.findViewById(R.id.pinNum4));
    }
}