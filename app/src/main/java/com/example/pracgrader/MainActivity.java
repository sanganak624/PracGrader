package com.example.pracgrader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

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

        //appData.fillTestData();

        appData.loadData(getApplicationContext());

        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);

        if(findViewById(R.id.mainActivity).getTag().equals("tabletView"))
        {
            FrameLayout sub = findViewById(R.id.sub);
            appData.setSub(sub);
            appData.setTablet(true);
            RegisterAdminFragment registerAdminFragment = (RegisterAdminFragment) fm.findFragmentById(R.id.sub);
            if(registerAdminFragment==null)
            {
                registerAdminFragment = new RegisterAdminFragment();
                ft.add(R.id.sub, registerAdminFragment);
            }
            sub.setVisibility(View.GONE);
        }
        else
        {
            appData.setTablet(false);
        }
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
            if(registerAdminFragment==null)
            {
                registerAdminFragment = new RegisterAdminFragment();
                ft.add(R.id.main, registerAdminFragment).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {

    }
}