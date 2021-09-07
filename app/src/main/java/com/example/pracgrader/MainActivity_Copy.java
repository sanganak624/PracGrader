package com.example.pracgrader;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.FragmentManager;

        import android.os.Bundle;

        import com.example.pracgrader.fragments.NewUserFragment;
        import com.example.pracgrader.fragments.ViewPracListFragment;

public class MainActivity_Copy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
//        login loginFrag = (login) fm.findFragmentById(R.id.main);
//
//        if(loginFrag==null)
//        {
//            loginFrag = new login();
//            fm.beginTransaction().add(R.id.main,loginFrag).commit();
//        }

        NewUserFragment newUserFragment = (NewUserFragment) fm.findFragmentById(R.id.main);

        if(newUserFragment==null)
        {
            newUserFragment = new NewUserFragment();
            fm.beginTransaction().add(R.id.main,newUserFragment).commit();
        }

//        ViewInstructorsFragment viewInstructorsFragment = (ViewInstructorsFragment) fm.findFragmentById(R.id.main);
//
//        if(viewInstructorsFragment==null)
//        {
//            viewInstructorsFragment = new ViewInstructorsFragment();
//            fm.beginTransaction().add(R.id.main,viewInstructorsFragment).commit();
//        }

//        ViewStudentListFragment viewStudentListFragment = (ViewStudentListFragment) fm.findFragmentById(R.id.main);
//
//        if(viewStudentListFragment==null)
//        {
//            viewStudentListFragment = new ViewStudentListFragment();
//            fm.beginTransaction().add(R.id.main,viewStudentListFragment).commit();
//        }

//        ViewPracListFragment pracListFragment = (ViewPracListFragment) fm.findFragmentById(R.id.main);
//
//        if(pracListFragment==null)
//        {
//            pracListFragment = new ViewPracListFragment();
//            fm.beginTransaction().add(R.id.main,pracListFragment).commit();
//        }
    }
}