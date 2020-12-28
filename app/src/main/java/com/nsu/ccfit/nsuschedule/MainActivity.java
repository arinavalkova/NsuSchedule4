package com.nsu.ccfit.nsuschedule;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nsu.ccfit.nsuschedule.data.DataController;
import com.nsu.ccfit.nsuschedule.ui.main.SectionsPagerAdapter;

import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataController dataController = new DataController(getFilesDir());
        try {
            if (!dataController.loadNSUServerData()) {
                loadChooseGroupActivity();
            }
            System.out.println(dataController.getData());
        } catch (IOException | ParserException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadChooseGroupActivity() {
        Intent intent = new Intent(this, ChooseGroupActivity.class);
        System.out.println("AKDJFLFJFKLAJL");
        startActivity(intent);
    }
}