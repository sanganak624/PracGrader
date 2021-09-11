package com.example.pracgrader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.fragments.AdminHomeFragment;
import com.example.pracgrader.fragments.LoginFragment;
import com.example.pracgrader.fragments.NewUserFragment;

public class MainActivity  extends AppCompatActivity {
    FragmentManager fm = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppData.getInstance().fillTestData();


        LoginFragment loginFrag = (LoginFragment) fm.findFragmentById(R.id.main);

        if(loginFrag==null)
        {
            loginFrag = new LoginFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            ft.add(R.id.main,loginFrag).commit();
        }
    }

    public void startLogin()
    {
        LoginFragment loginFrag = (LoginFragment) fm.findFragmentById(R.id.main);

        if(loginFrag==null)
        {
            loginFrag = new LoginFragment();
            fm.beginTransaction().add(R.id.main,loginFrag).commit();
        }
        fm.beginTransaction().add(R.id.main,loginFrag).commit();
    }

    public void startAdminHome()
    {
        AdminHomeFragment adminHomeFragment = (AdminHomeFragment) fm.findFragmentById(R.id.main);

        if(adminHomeFragment==null)
        {
            adminHomeFragment = new AdminHomeFragment();
        }
        fm.beginTransaction().add(R.id.main,adminHomeFragment).commit();
    }
}