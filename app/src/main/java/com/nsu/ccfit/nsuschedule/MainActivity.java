package com.nsu.ccfit.nsuschedule;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.nsu.ccfit.nsuschedule.ui.main.SectionsPagerAdapter;

import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DataViewModel model = ViewModelProviders.of(this).get(DataViewModel.class);
        try {
            if (!model.loadData()) {
                    loadChooseGroupActivity();
                    return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }


//        DataController dataController = new DataController(getFilesDir());
//        try {
//            if (!dataController.loadNSUServerData()) {
//            }
//            System.out.println(dataController.getData());
//        } catch (IOException | ParserException e) {
//            e.printStackTrace();
//        }

        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    private void loadChooseGroupActivity() {
        Intent intent = new Intent(this, ChooseGroupActivity.class);
        System.out.println("AKDJFLFJFKLAJL");
        startActivity(intent);
    }
}