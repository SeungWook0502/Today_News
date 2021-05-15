package com.example.todaynews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Summary extends AppCompatActivity {
    int menu_select;
    private DBOpenHelper mDBOpenHelper;
    private String title;
    private String Article_Title;
    private String Article_Content;
    private String Article_URL;
    private TextView article_title;
    private TextView article_content;
    private Button url_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        menu_select = getIntent().getIntExtra("menu_select",0);

        article_title = findViewById(R.id.article_title);
        article_content = findViewById(R.id.article_content);
        url_start = findViewById(R.id.url_start);
        Toolbar toolbar = findViewById(R.id.summary_toolbar);

        title = getIntent().getStringExtra("title");
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        mDBOpenHelper = new DBOpenHelper(this);
        mDBOpenHelper.open();
        mDBOpenHelper.create();

        if(menu_select==1) {
            Article_Title = getIntent().getStringExtra("Article_Title");
            Article_Content = getIntent().getStringExtra("Article_Content");
            Article_URL = getIntent().getStringExtra("Article_URL");

            article_title.setText(Article_Title);
            article_content.setText(Article_Content);
        }
        else if(menu_select==2){
            String position = getIntent().getStringExtra("position");
            Cursor iCursor = mDBOpenHelper.setFirst();

            while(iCursor.moveToNext()){
                String tempID = iCursor.getString(iCursor.getColumnIndex("_id"));
                String tempTitle = iCursor.getString(iCursor.getColumnIndex("article_title"));
                String tempContent = iCursor.getString(iCursor.getColumnIndex("article_content"));
                Article_URL = iCursor.getString(iCursor.getColumnIndex("article_url"));
                if(tempID.equals(position)){
                    article_title.setText(tempTitle);
                    article_content.setText(tempContent);
                }
            }
        }

        url_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent url = new Intent(Intent.ACTION_VIEW, Uri.parse(Article_URL));
                startActivity(url);
            }
        });
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
                    mDBOpenHelper.insertColumn(title,Article_Title,Article_Content,Article_URL);
                    Toast.makeText(this,"스크랩 성공",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(this,"스크랩 실패",Toast.LENGTH_SHORT).show();
                }

            }
        }
        return true;
    }
}