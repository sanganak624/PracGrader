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
import com.example.pracgrader.fragments.RegisterAdminFragment;

public class MainActivity  extends AppCompatActivity {
    FragmentManager fm = getSupportFragmentManager();
    AppData appData = AppData.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appData.fillTestData();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        if(appData.getAdmins().size()!=0)
        {
            LoginFragment loginFrag = (LoginFragment) fm.findFragmentById(R.id.main);
            if(loginFrag==null)
            {
                loginFrag = new LoginFragment();
                ft.add(R.id.main, loginFrag).commit();
            }
        }
        else
        {
            RegisterAdminFragment registerAdminFragment = (RegisterAdminFragment) fm.findFragmentById(R.id.main);
            ft.add(R.id.main, registerAdminFragment).commit();
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