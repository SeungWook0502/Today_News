package com.example.todaynews;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class DisplaySetting extends AppCompatActivity {
    private int select = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_setting);

        Toolbar toolbar = findViewById(R.id.display_toolbar);
        setSupportActionBar(toolbar);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (i == R.id.display_rbt1) {
                select = 1;
            } else if (i == R.id.display_rbt2) {
                select = 2;
            }else if (i == R.id.display_rbt3) {
                select = 3;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            try {
                FileOutputStream fos = openFileOutput
                        ("myfile.txt", Context.MODE_PRIVATE);
                PrintWriter out = new PrintWriter(fos);
                out.println(select);
                out.close();
                super.onBackPressed();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}