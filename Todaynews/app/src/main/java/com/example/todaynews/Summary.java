package com.example.todaynews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Summary extends AppCompatActivity {
    int menu_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        menu_select = getIntent().getIntExtra("menu_select",0);
        String title = getIntent().getStringExtra("title");
        Toolbar toolbar = findViewById(R.id.summary_toolbar);
        //타이틀 받아오기
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu_select==1) {
            getMenuInflater().inflate(R.menu.custom_toolbar, menu);
            menu.getItem(0).setTitle("스크랩");
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (menu_select == 1) {
            if (item.getItemId() == R.id.save) {
                Toast.makeText(this,"menu_select = 1",Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}