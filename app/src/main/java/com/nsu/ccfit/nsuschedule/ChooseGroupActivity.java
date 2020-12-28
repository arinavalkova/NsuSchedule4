package com.nsu.ccfit.nsuschedule;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.nsu.ccfit.nsuschedule.data.DataController;

import java.io.IOException;

public class ChooseGroupActivity extends AppCompatActivity {

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_group);

        editText = findViewById(R.id.group);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void okClicked(View view) throws IOException {
        DataController dataController = new DataController(getFilesDir());
        dataController.setScheduleUrl("https://table.nsu.ru/ics/group/" + editText.getText());
        loadMainActivity();
    }

    private void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}