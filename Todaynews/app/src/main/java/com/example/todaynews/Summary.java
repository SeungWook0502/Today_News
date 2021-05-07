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
    private DBOpenHelper mDBOpenHelper;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        menu_select = getIntent().getIntExtra("menu_select",0);
        title = getIntent().getStringExtra("title");
        Toolbar toolbar = findViewById(R.id.summary_toolbar);
        //타이틀 받아오기
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        mDBOpenHelper = new DBOpenHelper(this);
        mDBOpenHelper.open();
        mDBOpenHelper.create();
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
                try {
                    mDBOpenHelper.insertColumn(title,"기사타이틀123","3줄요약123","http://123.123");
                    Toast.makeText(this,"스크랩 성공",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(this,"스크랩 실패",Toast.LENGTH_SHORT).show();
                }

            }
        }
        return true;
    }
}