package com.example.campuslink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.campuslink.databinding.ActivityMainBinding;
import com.example.campuslink.databinding.ActivityTeacherBinding;
import com.example.campuslink.ui.teacher.TeacherHomeFragment;
import com.example.campuslink.ui.teacher.TeacherMessageFragment;
import com.example.campuslink.ui.teacher.TeacherPersonFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TeacherActivity extends AppCompatActivity {

    private ActivityTeacherBinding binding;

    private TeacherHomeFragment teacherHomeFragment;
    private TeacherMessageFragment teacherMessageFragment;
    private TeacherPersonFragment teacherPersonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_teacher);

        ActionBar actionBar= getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        binding = ActivityTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navigationView = findViewById(R.id.nav_view_t);
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_teacher_home,R.id.navigation_teacher_message,R.id.navigation_teacher_person
        ).build();
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment_activity_teacher);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navViewT,navController);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                teacherHomeFragment = new TeacherHomeFragment();
                teacherMessageFragment = new TeacherMessageFragment();
                teacherPersonFragment = new TeacherPersonFragment();

                int id = item.getItemId();
                switch (id){
                    case R.id.navigation_teacher_home:
                        fragment = teacherHomeFragment;
                        break;
                    case R.id.navigation_teacher_message:
                        fragment = teacherMessageFragment;
                        break;
                    case R.id.navigation_teacher_person:
                        fragment = teacherPersonFragment;
                        break;
                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_teacher,fragment).commit();
                return true;
            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}