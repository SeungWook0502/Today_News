package com.example.todaynews;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class KeywordSetting extends AppCompatActivity {
    private CheckBox sid100;
    private CheckBox sid101;
    private CheckBox sid102;
    private CheckBox sid103;
    private CheckBox sid104;
    private CheckBox sid105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_setting);

        sid100 = findViewById(R.id.sid100);
        sid101 = findViewById(R.id.sid101);
        sid102 = findViewById(R.id.sid102);
        sid103 = findViewById(R.id.sid103);
        sid104 = findViewById(R.id.sid104);
        sid105 = findViewById(R.id.sid105);

        Toolbar toolbar = findViewById(R.id.keyword_toolbar);
        setSupportActionBar(toolbar);

        try {
            BufferedReader br = new BufferedReader(new FileReader("/data/data/com.example.todaynews/files/keywordfile.txt"));
            String readStr = br.readLine();
            sid100.setChecked(readStr.equals("1") ? true : false);
            readStr = br.readLine();
            sid101.setChecked(readStr.equals("1") ? true : false);
            readStr = br.readLine();
            sid102.setChecked(readStr.equals("1") ? true : false);
            readStr = br.readLine();
            sid103.setChecked(readStr.equals("1") ? true : false);
            readStr = br.readLine();
            sid104.setChecked(readStr.equals("1") ? true : false);
            readStr = br.readLine();
            sid105.setChecked(readStr.equals("1") ? true : false);
            br.close();
        } catch (Exception e) {
            sid100.setChecked(true);
            sid101.setChecked(true);
            sid102.setChecked(true);
            sid103.setChecked(true);
            sid104.setChecked(true);
            sid105.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_toolbar, menu);
        menu.getItem(0).setTitle("저장");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            try {
                FileOutputStream fos = openFileOutput
                        ("keywordfile.txt", Context.MODE_PRIVATE);
                PrintWriter out = new PrintWriter(fos);
                if (sid100.isChecked() == false && sid101.isChecked() == false && sid102.isChecked() == false && sid103.isChecked() == false && sid104.isChecked() == false && sid105.isChecked() == false) {
                    for (int i = 0; i < 6; i++)
                        out.println(1);
                } else {
                    out.println(sid100.isChecked() == true ? 1 : 0);
                    out.println(sid101.isChecked() == true ? 1 : 0);
                    out.println(sid102.isChecked() == true ? 1 : 0);
                    out.println(sid103.isChecked() == true ? 1 : 0);
                    out.println(sid104.isChecked() == true ? 1 : 0);
                    out.println(sid105.isChecked() == true ? 1 : 0);
                }
                out.close();
                super.onBackPressed();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}